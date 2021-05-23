package com.cleanspace.healthyhome1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logIn(View view) {
        EditText email = findViewById(R.id.userNameEditText);
        EditText password = findViewById(R.id.passwordEditText);

        ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    changeActivity(Homes.class);
                    Log.i("SessionToken", ParseUser.getCurrentSessionToken());
                    Toast.makeText(getApplicationContext(),"Logged In", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("ERROR!!!!!", e.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void goBack(View view){
        changeActivity(MainActivity.class);
    }

    public void changeActivity(Class activity){
        Intent switchActivity = new Intent(getApplicationContext(), activity);
        startActivity(switchActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
       if(ParseUser.getCurrentSessionToken() != null){
           ParseUser.logOutInBackground(new LogOutCallback() {
               @Override
               public void done(ParseException e) {
                   if(e == null){
                       Log.i("Logged out succesfully", "");
                       Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Log.i("ERROR!!!", e.getLocalizedMessage());
                       Toast.makeText(getApplicationContext(),"Error logging out: "+ e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }
    }
}