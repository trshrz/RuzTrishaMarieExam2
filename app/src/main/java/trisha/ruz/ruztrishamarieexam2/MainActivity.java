package trisha.ruz.ruztrishamarieexam2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etExam1;
    private EditText etExam2;
    private TextView txtTotal;
    private Button mButtonCompute;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> keyList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Grade");
        etFirstName = findViewById(R.id.userFirstName);
        etLastName = findViewById(R.id.userLastName);
        etExam1 = findViewById(R.id.userExam1);
        etExam2 = findViewById(R.id.userExam2);
        mButtonCompute = findViewById(R.id.compute);
        txtTotal = findViewById(R.id.total);
        keyList = new ArrayList<>();

        mButtonCompute.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                displayAverage();
            }
        });
    }
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ss : dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void displayAverage() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        Double examNo1 = Double.parseDouble(etExam1.getText().toString().trim());
        Double examNo2 = Double.parseDouble(etExam2.getText().toString().trim());

        Double total = (examNo1 + examNo2) / 2;
        Grade sgrade = new Grade(firstName, lastName, total);
        String key = myRef.push().getKey();
        myRef.child(key).setValue(sgrade);
        keyList.add(key);


        txtTotal.setText("Your average is: " + total);
    }



}
