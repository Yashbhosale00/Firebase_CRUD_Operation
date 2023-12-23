package com.example.firebase_crud_operation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddIntroduction extends AppCompatActivity {

    private EditText editTextThought;
    private EditText editTextAdmin;

    private Button AddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_introduction);

        editTextThought=findViewById(R.id.editTextThought);
        editTextAdmin=findViewById(R.id.editTextAdmin);
        AddBtn=findViewById(R.id.AddBtn);

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thought = editTextThought.getText().toString();
                String admin = editTextAdmin.getText().toString();

                if (thought.isEmpty()){
                    editTextThought.setError("Cannot be empty");
                    return;
                }
                if (admin.isEmpty()){
                    editTextAdmin.setError("Cannot be empty");
                    return;
                }

                addThoughttoDB(thought,admin);

            }
        });
    }

    private void addThoughttoDB(String thought, String admin) {
        HashMap<String, String> thoughtHashmap = new HashMap<>();
        thoughtHashmap.put("thought",thought);
        thoughtHashmap.put("admin" , admin);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference thoughtRef = database.getReference("thought");

        String key = thoughtRef.push().getKey();
        thoughtHashmap.put("key",key);

        thoughtRef.child(key).setValue(thoughtHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddIntroduction.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                editTextThought.getText().clear();
                editTextAdmin.getText().clear();
            }
        });

    }
}