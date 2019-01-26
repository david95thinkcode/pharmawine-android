package com.jmaplus.pharmawine.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.firebase.database.*
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.DiscussionMessageAdapter
import com.jmaplus.pharmawine.models.Messaging.DiscussionMessage
import com.jmaplus.pharmawine.models.Messaging.FireMessage
import com.jmaplus.pharmawine.models.Messaging.FireRoom
import com.jmaplus.pharmawine.models.Messaging.FireUser
import com.jmaplus.pharmawine.utils.FirebaseConstants.*
import com.jmaplus.pharmawine.utils.Utils
import com.jmaplus.pharmawine.utils.Utils.presentToast
import java.util.*

class DiscussionActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private var TAG: String = "DiscussionActivity"
        var ROOM_EXTRA: String = "room"
        var FROMUSER_EXTRA: String = "from"
        var TOUSERID_EXTRA: String = "toId"
        var TOUSERROLE_EXTRA: String = "toRole"
        var TOUSERFULLNAME_EXTRA: String = "toName"
        var TOUSERAVATARURL_EXTRA: String = "toAvatar"
        var LASTMESSAGETIME_EXTRA: String = "lastMessage"
    }

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var mMessagesReference: DatabaseReference
    private var messagesList: MutableList<DiscussionMessage> = ArrayList()
    private lateinit var adapter: DiscussionMessageAdapter
    private lateinit var mContext: Context
    private lateinit var FromUserId: String
    private lateinit var ToUserId: String
    private lateinit var ToUserFullName: String
    private lateinit var ToUserAvatarUrl: String
    private lateinit var ToUserRoleLabel: String
    private lateinit var mRoom: String
    private var mLastMessageTime = ""


    /**
     * A true lorsque c'est la premiere fois que les
     * deux users s'ecrivent
     */
    private var mNoRoomFound = false

    private lateinit var mInput: EditText
    private lateinit var mSendBtn: Button
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get data from previous activity
        mRoom = intent.extras.getString(ROOM_EXTRA, "")
        FromUserId = intent.extras.getString(FROMUSER_EXTRA, "")
        ToUserId = intent.extras.getString(TOUSERID_EXTRA, "")
        ToUserFullName = intent.extras.getString(TOUSERFULLNAME_EXTRA, "")
        ToUserAvatarUrl = intent.extras.getString(TOUSERAVATARURL_EXTRA, "")
        ToUserRoleLabel = Utils.getRoleLabel(intent.extras.getInt(TOUSERROLE_EXTRA, -1))
        mLastMessageTime = intent.extras.getString(LASTMESSAGETIME_EXTRA, "")

        mContext = this

        InitializeUI()
        initializeFirebase()
    }

    private fun InitializeUI() {
        title = ToUserFullName
        supportActionBar?.subtitle = "Votre $ToUserRoleLabel"

        mRecyclerView = findViewById(R.id.msg_recycle_view)
        mInput = findViewById(R.id.message_input_edit_text)
        mSendBtn = findViewById(R.id.send_message_btn)
        mSendBtn.setOnClickListener(this)

        // mProgressBar = findViewById(R.id.discussion_activity_progress_bar)
        // mProgressBar.visibility = View.VISIBLE

        initialiseRecycleView()
    }

    private fun initialiseRecycleView() {
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        // Get users picture Url and put it on adapter
        database.child(USERS_COLLECTION).child(ToUserId).addListenerForSingleValueEvent(
                (object : ValueEventListener {

                    override fun onDataChange(userSnapshot: DataSnapshot) {
                        try {
                            if (userSnapshot.exists()) {
                                val user = userSnapshot.getValue(FireUser::class.java)
                                if (!user?.avatarUrl.isNullOrEmpty()) {
                                    adapter = DiscussionMessageAdapter(mContext, messagesList, ToUserAvatarUrl)
                                    mRecyclerView.adapter = adapter
                                } else {
                                    adapter = DiscussionMessageAdapter(mContext, messagesList, ToUserAvatarUrl)
                                    mRecyclerView.adapter = adapter
                                }
                            } else {
                                adapter = DiscussionMessageAdapter(mContext, messagesList, ToUserAvatarUrl)
                                mRecyclerView.adapter = adapter
                            }
                        } catch (e: Exception) {
                            adapter = DiscussionMessageAdapter(mContext, messagesList, ToUserAvatarUrl)
                            mRecyclerView.adapter = adapter
                        }

                    }

                    override fun onCancelled(p0: DatabaseError) {
                        adapter = DiscussionMessageAdapter(mContext, messagesList, ToUserAvatarUrl)
                        mRecyclerView.adapter = adapter
                    }
                })
        )
    }

    private fun initializeFirebase() {

        var fromUserRoomCollection = database.child(USERS_CHANNEL_COLLECTION).child(FromUserId).child(ToUserId)
        var toUserRoomCollection = database.child(USERS_CHANNEL_COLLECTION).child(ToUserId).child(FromUserId)

        /**
         * @description
         * Recuperer l'ID de la room et l'attribuer a mRoom
         * (ce qui est deja fait dans le OnCreate)
         *
         *  Si mRoom est vide, on recherche une roomId correspondante
         *  a la conversation des deux users (fromUserId et toUserId)
         *
         *  A => On recherche dans la collection de rooms du TOUSER
         *      - Lorsqu'un ROOM est retrouvé liant TOUSER et FROM USER :
         *        l'ID de ce room est attribue a mRoom
         *      - Lorsqu'il n'est retrouvé aucun ROOM liant ces deux users : on passe au point B
         *
         *  B => On recherche dans la collection de rooms du FROMUSER
         *      - Lorsqu'un ROOM est trouve : l'ID de ce ROOM est attribue a mRoom
         *      - Lorsque la recherche est infructueuse : on cree une nouvelle ROOM
         *        uniquement lorsque le premier message est envoyé, on assigne sa valeur a mRoom,
         *        puis on ajoute l'ID du room a la collection de room de chaque user
         *
         *        NB :
         *        pour le TOUSER, ce cera => /users_rooms/toUserId/fromUserId/roomId
         *        pour le FROMUSER, ce sera => /users_rooms/fromUserId/toUserId/roomId
         *
         */

        /**
         * LISTENER DU SNAPSHOT DE FROMUSER
         *
         * Ceci correspond a la verification decrite au point B
         */
        val fromUserListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (mRoom.isNullOrEmpty()) {
                    if (dataSnapshot.exists()) {

                        initializeMessageReferences(dataSnapshot.value.toString())

                        toUserRoomCollection.setValue(mRoom)

                        Log.i(TAG, "mRoom exists on FROMUSER : $mRoom")
                    } else {
                        /**
                         *  Si le code est execute jusqu'ici,
                         *  cela signifie que la room n'existe pas des deux cotes utilisateur
                         */
                        Log.w(TAG, "mRoom don't exists on FROMUSER")
                        mNoRoomFound = true
                        mProgressBar.visibility = View.GONE

                        presentToast(mContext, "Starting a new conversation", true)
                    }
                } else {
                    Log.i(TAG, "Room found ==> $mRoom")
                    initializeMessageReferences(mRoom)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        /**
         * LISTENER DU SNAPSHOT DE TOUSER
         *
         * A l'interieur, on fait la verification A indiquee plus haut
         */
        val toUserListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (mRoom.isNullOrEmpty()) {
                    println("mRoom empty so this activity is called from UsersList")

                    if (dataSnapshot.exists()) {

                        initializeMessageReferences(dataSnapshot.value.toString())

                        fromUserRoomCollection.setValue(mRoom)

                        Log.i(TAG, "mRoom exist on TOUSER")
                    } else {
                        mNoRoomFound = true

                        Log.i(TAG, "mRoom don't exist on TOUSER")

                        fromUserRoomCollection.addListenerForSingleValueEvent(fromUserListener)
                    }
                } else {
                    mNoRoomFound = false
                    println("mRoom ==> $mRoom ==> activity called from Messaging Fragment")
                    fromUserRoomCollection.addListenerForSingleValueEvent(fromUserListener)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        // Listener for second user
        toUserRoomCollection.addListenerForSingleValueEvent(toUserListener)
    }

    private fun initializeMessageReferences(roomKey: String) {
        mRoom = roomKey
        mMessagesReference = database.child(MESSAGES_COLLECTION).child(mRoom)
        mNoRoomFound = false

        Log.i(TAG, "messageReferences Initialized ! roomKey ==> : $roomKey \n")

        var messagesListener = object : ChildEventListener {
            override fun onChildAdded(fireMsg: DataSnapshot, p1: String?) {
                var msg = fireMsg.getValue(FireMessage::class.java)
                val discusMsg = DiscussionMessage(msg!!, msg.from == FromUserId)

                pushToMessageList(discusMsg)

                // Mark message as read
                // if its sent to the current user
                // and the timestamps is <= to the last message timestamps
                // and the message was unread

                if (msg.to == FromUserId
                        && !msg.isRead
                        && msg.createdAt.toLong() <= mLastMessageTime.toLong() && !fireMsg.key.isNullOrEmpty()
                ) {
                    mMessagesReference
                            .child(fireMsg?.key.toString())
                            .child("read")
                            .setValue(true)

                    // Update on Room Instance too
                    database.child(CHANNEL_COLLECTION)
                            .child(mRoom)
                            .child("lastMessage")
                            .child("read")
                            .setValue(true)

                } else {
                    // "That message don't need to be update to read state
                }

            }

            override fun onCancelled(fireMsg: DatabaseError) {
                Log.w(TAG, "onCancelled")
            }

            override fun onChildMoved(fireMsg: DataSnapshot, p1: String?) {
                Log.w(TAG, "onChildMoved")
            }

            override fun onChildChanged(fireMsg: DataSnapshot, p1: String?) {
                Log.w(TAG, "onChildChanged from mMessagesReference Listener")
            }

            override fun onChildRemoved(fireMsg: DataSnapshot) {
                Log.w(TAG, "onChildRemoved")
            }
        }

        mMessagesReference.addChildEventListener(messagesListener)
    }

    private fun pushToMessageList(message: DiscussionMessage) {
        try {
            if (message != null) {
                // Here we add a message to the list and update the recycler view

                val index = if (messagesList.size == 0) 0 else messagesList.size
                if (index == 0) Log.i(TAG, "Premier texto")

                messagesList.add(index, message)
                adapter.notifyItemInserted(index)
                mProgressBar.visibility = View.GONE

                // Scroll to last message position
                mRecyclerView.scrollToPosition(adapter.itemCount - 1)

                Log.i(TAG, "Added to index $index ==> ${message.fireMessage.content}")
            }
        } catch (e: kotlin.UninitializedPropertyAccessException) {
            Log.w(TAG, "Initialisation manquante quelque part")
            Log.w(TAG, e.message)
            e.printStackTrace()
        } catch (e: Exception) {
            Log.e(TAG, "Error on pushToMessageList()")
            Log.w(TAG, e.message)
            e.printStackTrace()
        }

    }

    /**
     * This function is used to push
     * a new message to database in FireBase
     */
    private fun sendMessage() {

        Log.w(TAG, "Room value ==> $mRoom")

        if (!mInput.text.isNullOrEmpty()) {

            if (mNoRoomFound) {
                // Create a room if there is no room found
                // before saving message

                Log.e(TAG, "No room found")

                val roomObject = FireRoom(FromUserId, ToUserId)
                val createdRoomKey = database.child(CHANNEL_COLLECTION).push().key

                initializeMessageReferences(createdRoomKey.toString())

                // Create & save a new room object
                database.child(CHANNEL_COLLECTION).child(mRoom).setValue(roomObject)

                // Adding the room to room collection for each user
                database.child(USERS_CHANNEL_COLLECTION).child(ToUserId).child(FromUserId).setValue(createdRoomKey)
                database.child(USERS_CHANNEL_COLLECTION).child(FromUserId).child(ToUserId).setValue(createdRoomKey)

                pushMessage()
            } else {
                pushMessage()
            }
        }
    }

    private fun pushMessage() {


        try {
            val newFireMessageKey: String? = mMessagesReference.push().key

            val msg = FireMessage(FromUserId, ToUserId, mInput.text.toString(), status = "pending",
                    id = newFireMessageKey!!
            )

            // Pushing message object to message collection
            mMessagesReference.child(newFireMessageKey!!).setValue(msg)
                    .addOnSuccessListener {
                        // Write was successful!

                        // Update Message status
                        mMessagesReference.child(newFireMessageKey).child("status").setValue("sent")

                        // Update lastseen property of message sender
                        database.child(USERS_COLLECTION).child(FromUserId).child("lastSeenAt").setValue(Date().time.toString())

                        // Update Room lastMessage property
                        database.child(CHANNEL_COLLECTION).child(mRoom).child("lastMessage").setValue(msg)

                        // Update status even it's useless
                        database.child(CHANNEL_COLLECTION).child(mRoom)
                                .child("lastMessage")
                                .child("status").setValue("sent")

                        // Scroll to last message position
                        mRecyclerView.scrollToPosition(adapter.itemCount - 1)

                    }
                    .addOnFailureListener {
                        // Write failed
                        Log.e(TAG, "Sending message failed")
                    }

            mInput.setText("")
        } catch (e: kotlin.UninitializedPropertyAccessException) {
            // Une variable n'a pas eté initialise
            // Donc on n'envoie pas le message
            Log.e(TAG, "Une variable n'a pas eté initialise")
            Log.e(TAG, e.message)
            e.printStackTrace()
            cannotSendMessageToast()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            cannotSendMessageToast()
        }
    }

    private fun cannotSendMessageToast() {
        presentToast(this, "This message can't be send", true)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mSendBtn.id -> sendMessage()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}
