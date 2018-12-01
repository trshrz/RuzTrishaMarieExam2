package trisha.ruz.ruztrishamarieexam2;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etExam1;
    private EditText etExam2;
    private TextView txtTotal;
    private FirebaseDatabase mFirebaseDatabase;
    private Button mButtonCompute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFirstName = findViewById(R.id.userFirstName);
        etLastName = findViewById(R.id.userLastName);
        etExam1 = findViewById(R.id.userExam1);
        etExam2 = findViewById(R.id.userExam2);
        mButtonCompute = findViewById(R.id.compute);
        txtTotal = findViewById(R.id.total);

        mButtonCompute.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                displayAverage();
            }
        });
    }


    public void displayAverage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Grade");
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        Long examNo1 = Long.parseLong(etExam1.getText().toString().trim());
        Long examNo2 = Long.parseLong(etExam2.getText().toString().trim());

        Long total = (examNo1 + examNo2) / 2;
        Grade sgrade = new Grade(firstName, lastName, total);
        String key = myRef.push().getKey();
        myRef.child(key).setValue(sgrade);


        txtTotal.setText("Your average is: " + total);
    }



}
