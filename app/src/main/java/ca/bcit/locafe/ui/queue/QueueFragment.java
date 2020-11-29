package ca.bcit.locafe.ui.queue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.bcit.locafe.FavouritesListArrayAdapter;
import ca.bcit.locafe.LocationDetailsActivity;
import ca.bcit.locafe.R;
import ca.bcit.locafe.ui.favourites.FavouriteItem;

public class QueueFragment extends Fragment {

    TextView queueText;
    FirebaseAuth firebaseAuth;

    private QueueViewModel queueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queueViewModel =
                ViewModelProviders.of(this).get(QueueViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_queue, container, false);
        final TextView textView = root.findViewById(R.id.text_queue);
        queueViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final Button leave = root.findViewById(R.id.btnLeave);
        leave.setVisibility(View.GONE);

        queueText = root.findViewById(R.id.text_queue_not_found);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userId = currentUser.getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        final DatabaseReference queuesRef = userRef.child("queues");

        queuesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("businessName")) {
                    String businessName = snapshot.child("businessName").getValue(String.class);
                    String message = new String("You are currently in line for: " + businessName);
                    queueText.setText(message);

                    leave.setVisibility(View.VISIBLE);

                    leave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            queuesRef.child("businessId").removeValue();
                            queuesRef.child("businessName").removeValue();
                            Toast.makeText(getActivity(), "You have left the queue.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return root;
    }
}