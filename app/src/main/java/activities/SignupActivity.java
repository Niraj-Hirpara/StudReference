package activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studreference.MainActivity;
import com.example.studreference.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    //create object of database reference class to access firebase realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://studreference-abfb4-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText fullName = findViewById(R.id.fullname);
        final EditText mobileNo = findViewById(R.id.mobile);
        final EditText emailId = findViewById(R.id.email);
        final EditText collegeName = findViewById(R.id.college);
        final EditText branchName = findViewById(R.id.branch);
        final EditText password = findViewById(R.id.password);

        final Button registerBtn1 = findViewById(R.id.registerbtn1);
        final Button loginBtn2 = findViewById(R.id.loginbtn2);

        //this will check letter
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        registerBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from edit text into string variable
                final String fullnameTxt = fullName.getText().toString();
                final String mobileTxt = mobileNo.getText().toString();
                final String emailTxt = emailId.getText().toString();
                final String collegeTxt = collegeName.getText().toString();
                final String branchTxt = branchName.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(fullnameTxt.isEmpty() || mobileTxt.isEmpty() || emailTxt.isEmpty() || collegeTxt.isEmpty() || branchTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobileTxt)) {
                                Toast.makeText(SignupActivity.this, "Phone number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase realtime database
                                //mobile number is unique id of every user
                                databaseReference.child("users").child(mobileTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(mobileTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(mobileTxt).child("college").setValue(collegeTxt);
                                databaseReference.child("users").child(mobileTxt).child("Branch").setValue(branchTxt);
                                databaseReference.child("users").child(mobileTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}