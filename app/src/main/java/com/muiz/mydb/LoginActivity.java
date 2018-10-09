package com.muiz.mydb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText email_edittext;
    EditText password_edittext;
    Button login_button;
    TextView donthave_textView;
    TextView create_account_textView;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        dbHelper = new DBHelper( this );

        email_edittext = findViewById( R.id.email_edittext_login_activity );
        password_edittext = findViewById( R.id.password_edittext_login_activity );
        login_button = findViewById( R.id.login_button_login_acitvity );
        donthave_textView = findViewById( R.id.Dont_have_account_textview_Login_activity );
        create_account_textView = findViewById( R.id.create_account_login_activity );

        create_account_textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent( LoginActivity.this,RegisterActivity.class );
                startActivity( myintent );
            }
        } );


                //set click event of login button
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check user input is correct or not
                if(validate()){

                    //get values from edittext fields
                    String Email = email_edittext.getText().toString();
                    String Password = password_edittext.getText().toString();

                    //authenticating user in database
                    User currentUser = dbHelper.Authenticate(new User(null,null,Email,Password));

                    //check Authentication is successful or not
                    if(currentUser != null){
                        Toast.makeText( getApplicationContext(),"Login Success",Toast.LENGTH_SHORT ).show();
                        //user login successfully now opening next activity
                        Intent intent = new Intent( LoginActivity.this,MainApp.class );
                        startActivity( intent );
                        finish();
                    }else{
                        //user logged in failed
                        Toast.makeText( getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } );

    }



    public boolean validate(){
        boolean valid = false;
        //get values from Edittext field
        String Email = email_edittext.getText().toString();
        String Password = password_edittext.getText().toString();
        //handling validation for Email field
        if(!Patterns.EMAIL_ADDRESS.matcher( Email ).matches()){
            valid = false;
            Toast.makeText( this,"Please Enter A valid email address",Toast.LENGTH_SHORT ).show();
        }else{
            valid = true;
        }

        //handling validation for password field
        if(Password.isEmpty()){
            valid = false;
            Toast.makeText( this,"Please Enter Valid Password",Toast.LENGTH_SHORT ).show();
        }else {
            if(Password.length()>5){
                valid = true;
            }else{
                valid = false;
                Toast.makeText( this,"Password is too Short",Toast.LENGTH_LONG ).show();
            }
        }
        return valid;
    }
}