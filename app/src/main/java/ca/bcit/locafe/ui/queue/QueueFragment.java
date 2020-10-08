package ca.bcit.locafe.ui.queue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.bcit.locafe.R;

public class QueueFragment extends Fragment {

    private QueueViewModel queueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queueViewModel =
                ViewModelProviders.of(this).get(QueueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_queue, container, false);
        final TextView textView = root.findViewById(R.id.text_queue);
        queueViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}