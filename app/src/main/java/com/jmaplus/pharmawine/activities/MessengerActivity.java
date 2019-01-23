package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Messaging.FireRoom;
import com.jmaplus.pharmawine.models.Messaging.FireUser;
import com.jmaplus.pharmawine.models.Messaging.MessagingRoom;

import java.util.ArrayList;
import java.util.List;

import static com.jmaplus.pharmawine.utils.FirebaseConstants.USERS_CHANNEL_COLLECTION;
import static com.jmaplus.pharmawine.utils.FirebaseConstants.USERS_COLLECTION;

public class MessengerActivity extends AppCompatActivity {

    public static final String TAG = "MessengerActivity";

    private DatabaseReference database;
    private DatabaseReference userReference;
    private DatabaseReference userRoomsReference;

    private List<MessagingRoom> roomsList;
    private AuthUser authenticatedUser;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        mContext = this;

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authenticatedUser = AuthUser.getAuthenticatedUser(this);

        roomsList = new ArrayList<MessagingRoom>();

        MessagingInitialisation();
    }


    public void MessagingInitialisation() {

        // 1- Initialisation de la db et des references
        database = FirebaseDatabase.getInstance().getReference();
        userReference = database.child(USERS_COLLECTION).child(authenticatedUser.getId().toString());
        userRoomsReference = database.child(USERS_CHANNEL_COLLECTION).child(authenticatedUser.getId().toString());

        try {
            // 2- Add user to firebase if it doesn't exists
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
//                        FireUser u = new FireUser(authenticatedUser.getId().toString()
//                                , authenticatedUser.getFirstname(),
//                                authenticatedUser.getLastname(), authenticatedUser.getAvatar());

//                        database.child(USERS_COLLECTION).child(authenticatedUser.getId().toString()).setValue(u)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(MessengerActivity.this,
//                                                "User was added to firebase", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    } else {
                        Toast.makeText(mContext, "onDataChange: User exists on Firebase", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException());

                }
            };

            // 3 - On ajoute a roomList la liste des room presentes dans firebase
            ChildEventListener roomChildListenner = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FireRoom fr = dataSnapshot.getValue(FireRoom.class);

                    addToRoomList(fr, dataSnapshot.getKey().toString());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            // Ajout des listenners
            userReference.addValueEventListener(userListener);
            userRoomsReference.addChildEventListener(roomChildListenner);

        } catch (Exception e) {
            Log.e(TAG, "MessagingInitialisation: " + e.getMessage());
        }


        /**
         * Chercher les channel du user, s'il n'y en a pas, creer un
         */
//        database.getReference(FirebaseConstants.CHANNEL_COLLECTION)
    }

    public void addToRoomList(final FireRoom fireRoom, final String roomKey) {

        String secondUserInTheRoom =
                authenticatedUser.getId().toString() == fireRoom.getFirst() ? fireRoom.getSecond() : fireRoom.getFirst();

        DatabaseReference secondFireUserReference = database.child(USERS_COLLECTION).child(secondUserInTheRoom);

        // Details du second user
        secondFireUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FireUser secondUserDetails = dataSnapshot.getValue(FireUser.class);

                if (secondUserDetails != null) {
//                    MessagingRoom messagingRoom = new MessagingRoom(
//                            secondUserDetails.getLastname(),
//                            fireRoom.getLastMessage(),
//                            secondUserDetails.getId(),
//                            roomKey, secondUserDetails.getAvatar()
//                    );

                    if (!roomsList.contains(roomKey)) {
//                        roomsList.add(messagingRoom);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
