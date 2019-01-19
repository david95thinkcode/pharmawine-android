package com.jmaplus.pharmawine.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.MessagingRoomAdapter
import com.jmaplus.pharmawine.models.AuthUser
import com.jmaplus.pharmawine.models.Messaging.FireRoom
import com.jmaplus.pharmawine.models.Messaging.FireUser
import com.jmaplus.pharmawine.models.Messaging.MessagingRoom
import com.jmaplus.pharmawine.utils.FirebaseConstants.*
import com.jmaplus.pharmawine.utils.ItemClickSupport

import java.util.ArrayList

class MessagingActivity : AppCompatActivity() {
    val TAG = "MessagingActivity"

    private lateinit var database: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var userRoomsReference: DatabaseReference

    private var roomsList: MutableList<MessagingRoom> = ArrayList()
    private lateinit var authenticatedUser: AuthUser
    private lateinit var mContext: Context
    private lateinit var adapter: MessagingRoomAdapter

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messging)

        mContext = this

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        authenticatedUser = AuthUser.getAuthenticatedUser(this)

        InitialiseUI()
        MessagingInitialisation()
    }

    private fun InitialiseUI() {
        mRecyclerView = findViewById(R.id.rv_messages)

        configureOnClickRecyclerView()

        // Adapter configuration
        mRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)
        adapter = MessagingRoomAdapter(roomsList, mContext, authenticatedUser.id.toString())
        mRecyclerView.adapter = adapter
    }


    private fun MessagingInitialisation() {

        // 1- Initialisation de la db et des references
        database = FirebaseDatabase.getInstance().reference
        userReference = database.child(USERS_COLLECTION).child(authenticatedUser.getId()!!.toString())
        userRoomsReference = database.child(USERS_CHANNEL_COLLECTION).child(authenticatedUser.getId()!!.toString())

        try {
            // 2- Add user to firebase if it doesn't exists
            val userListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        val u = FireUser(authenticatedUser.getId()!!.toString(), authenticatedUser.getFirstname(),
                                authenticatedUser.getLastname(), authenticatedUser.getAvatar())

                        userReference.setValue(u).addOnSuccessListener {
                                    Toast.makeText(mContext,
                                            "User was added to firebase", Toast.LENGTH_SHORT).show()
                                }
                    } else {
                        Toast.makeText(mContext, "onDataChange: User exists on Firebase", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException())

                }
            }

            // 3 - On ajoute a roomList la liste des room presentes dans firebase
            val roomChildListenner = object : ChildEventListener {
                override fun onChildAdded(roomKeydataSnapshot: DataSnapshot, s: String?) {

                    val roomKey = roomKeydataSnapshot.value.toString()

                    Log.i(TAG, "Room key is $roomKey")

                    database.child(CHANNEL_COLLECTION).child(roomKey).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            try {
                                val fr = dataSnapshot.getValue(FireRoom::class.java)
                                Log.i(TAG, fr.toString())
                                addToRoomList(fr, dataSnapshot.key!!.toString())
                            } catch (e: Exception) {
                                Log.w(TAG, e.message)
                            }

                        }
                    })
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }

            // Ajout des listenners
            userReference.addValueEventListener(userListener)
            userRoomsReference.addChildEventListener(roomChildListenner)

        } catch (e: Exception) {
            Log.e(TAG, "MessagingInitialisation: " + e.message)
        }
    }

    fun addToRoomList(fireRoom: FireRoom?, roomKey: String) {

        val secondUserInTheRoom = if (authenticatedUser.id!!.toString() == fireRoom?.first) fireRoom!!.second else fireRoom!!.first
        val secondFireUserReference = database.child(USERS_COLLECTION).child(secondUserInTheRoom)

        // Details du second user
        secondFireUserReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val secondUserDetails = dataSnapshot.getValue(FireUser::class.java)

                if (secondUserDetails != null) {
                    val messagingRoom = MessagingRoom(
                            "${secondUserDetails.firstname} ${secondUserDetails.lastName}",
                            fireRoom.lastMessage,
                            secondUserDetails.id,
                            roomKey, secondUserDetails.avatarUrl, secondUserDetails.role
                    )

                    // Adding room to roomList
                    if (!roomsList.any { it.roomId == messagingRoom.roomId }) {
                        var index = if (roomsList.size == 0) 0 else roomsList.size

                        roomsList.add(index, messagingRoom)
                        adapter.notifyItemInserted(index);

                        Toast.makeText(mContext, "New room added ==>  ${messagingRoom.roomId}t", Toast.LENGTH_SHORT).show()

                    } else {
                        Log.i(TAG, "Room ${messagingRoom.roomId} already exists on RoomList")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_messaging_room_item)
                .setOnItemClickListener { theRecyclerView, position, v ->
                    //Get concerned room from adapter
                    var r = adapter.getRoom(position)
                    // Go to discussion activity
                    openDiscussion(r)
                }
    }

    /**
     *  Open Discussion activity
     */
    private fun openDiscussion(room: MessagingRoom) {

        var intent = Intent(mContext, DiscussionActivity::class.java)
        intent.putExtra(DiscussionActivity.FROMUSER, authenticatedUser.id)
        intent.putExtra(DiscussionActivity.TOUSERID, room.userId)
        intent.putExtra(DiscussionActivity.TOUSERFULLNAME, room.username)
        intent.putExtra(DiscussionActivity.ROOM, room.roomId)
        intent.putExtra(DiscussionActivity.TOUSERROLE, room.role)

        // TODO: SEND USER ROLE (superviseur / groupe non superviseé)

        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}