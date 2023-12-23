package com.example.firebase_crud_operation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;
    private ThoughtAdapter thoughtAdapter;
    private List<Thought> thoughtList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddIntroduction.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.RecycleV);
        thoughtList = new ArrayList<>();
        thoughtAdapter = new ThoughtAdapter(thoughtList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(thoughtAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fetchThoughtsFromFirebase();


    }

    private void fetchThoughtsFromFirebase() {
        DatabaseReference thoughtRef = FirebaseDatabase.getInstance().getReference("thought");

        thoughtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thoughtList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Thought thought = dataSnapshot.getValue(Thought.class);
                    thoughtList.add(thought);
                }
                thoughtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {
                ViewGroup parent = null;
                showEditDialog(thoughtList.get(position)); // Pass the context
//                Toast.makeText(MainActivity.this, "Edit item at position " + position, Toast.LENGTH_SHORT).show();
            } else if (direction == ItemTouchHelper.RIGHT) {

//                Toast.makeText(MainActivity.this, "Delete item at position " + position, Toast.LENGTH_SHORT).show();
                deleteThought(thoughtList.get(position));

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // Customize the appearance during swipe
            // (Optional: You can add background color, icon, etc.)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void showEditDialog(Thought thought) {
        Intent intent = new Intent(MainActivity.this, Edit_Thought.class);
            intent.putExtra("thoughtKey", thought.getKey());
            startActivity(intent);

    }

    private void deleteThought(Thought thought) {
        DatabaseReference thoughtRef = FirebaseDatabase.getInstance().getReference("thought").child(thought.getKey());
        thoughtRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                    }

                });

    }
}