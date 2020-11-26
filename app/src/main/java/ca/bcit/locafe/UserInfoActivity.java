package ca.bcit.locafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.bcit.locafe.data.model.User;
import ca.bcit.locafe.ui.favourites.FavouriteItem;

public class UserInfoActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final EditText firstName = findViewById(R.id.updateFirstName);
        final EditText lastName = findViewById(R.id.updateLastName);
        final EditText email = findViewById(R.id.updateEmail);

        Button updateBtn = findViewById(R.id.btnUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userId = currentUser.getUid();

        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String userFirst = snapshot.child("firstName").getValue(String.class);
                String userLast = snapshot.child("lastName").getValue(String.class);
                String userEmail = snapshot.child("email").getValue(String.class);
                firstName.setText(userFirst);
                lastName.setText(userLast);
                email.setText(userEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newFirst = firstName.getText().toString();
                final String newLast = lastName.getText().toString();
                final String newEmail = email.getText().toString();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("firstName").setValue(newFirst);
                        dataSnapshot.getRef().child("lastName").setValue(newLast);
                        //dataSnapshot.getRef().child("email").setValue(newEmail);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                Toast.makeText(UserInfoActivity.this, "Information updated.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}