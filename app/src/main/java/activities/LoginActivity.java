package activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studreference.MainActivity;
import com.example.studreference.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://studreference-abfb4-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText mobileNo = findViewById(R.id.mobile);
        final EditText password = findViewById(R.id.password);

        final Button registerBtn2 = findViewById(R.id.registerbtn2);
        final Button loginBtn1 = findViewById(R.id.loginbtn1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mobileTxt = mobileNo.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(mobileTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobileTxt)) {
                                //get password from firebase database of users using mobileText
                                final String getPassword = snapshot.child(mobileTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt)) {
                                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

                                    //go to home page on success
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Wrong password!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });



        registerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}