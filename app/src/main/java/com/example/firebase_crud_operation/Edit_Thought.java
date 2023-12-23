package com.example.firebase_crud_operation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Thought extends AppCompatActivity {

    private EditText editTextThought;
    private EditText editTextAdmin;
    private Button updateBtn;
    private String thoughtKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thought);

        editTextThought = findViewById(R.id.editTextThought);
        editTextAdmin = findViewById(R.id.editTextAdmin);
        updateBtn=findViewById(R.id.Updatebtn);

        // Retrieve thought key from the intent
        thoughtKey = getIntent().getStringExtra("thoughtKey");

        fetchThoughtData();


        // Fetch existing data from Firebase using thoughtKey and populate the EditText fields

        updateBtn.setOnClickListener(view -> updateThought());
    }

    private void fetchThoughtData() {
        DatabaseReference thoughtRef = FirebaseDatabase.getInstance().getReference("thought").child(thoughtKey);
        thoughtRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Populate EditText fields with existing data
                    String thought = snapshot.child("thought").getValue(String.class);
                    String admin = snapshot.child("admin").getValue(String.class);

                    editTextThought.setText(thought);
                    editTextAdmin.setText(admin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }

    private void updateThought() {
        String thought = editTextThought.getText().toString();
        String admin = editTextAdmin.getText().toString();

        if (thought.isEmpty() || admin.isEmpty()) {
            // Handle empty fields
            return;
        }

        // Update the record in Firebase
        DatabaseReference thoughtRef = FirebaseDatabase.getInstance().getReference("thought").child(thoughtKey);
        thoughtRef.child("thought").setValue(thought);
        thoughtRef.child("admin").setValue(admin)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Edit_Thought.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after successful update
                    } else {
                        Toast.makeText(Edit_Thought.this, "Failed to update record", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}