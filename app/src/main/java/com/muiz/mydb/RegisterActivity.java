package com.muiz.mydb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username_edittext;
    EditText email_edittext;
    EditText password_edittext;
    Button register_button;
    TextView backtologin;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        dbHelper = new DBHelper( this);

        username_edittext=findViewById( R.id.username_editText_register_activity );
        email_edittext=findViewById( R.id.email_editText2_register_activity );
        password_edittext=findViewById( R.id.password_editText3_register_activity );
        register_button=findViewById( R.id.register_button_register_acrivity );
        backtologin=findViewById( R.id.backtologin_textView_registeractivity );

        backtologin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent( RegisterActivity.this,LoginActivity.class );
                startActivity( myintent );
            }
        } );


        register_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String UserName = username_edittext.getText().toString();
                    String Email = email_edittext.getText().toString();
                    String Password = password_edittext.getText().toString();

                    if(!dbHelper.isEmailExists(Email)){
                        dbHelper.addUser(new User(null,UserName,Email,Password));
                        Toast.makeText( getApplicationContext(),"User Created",Toast.LENGTH_SHORT ).show();
                        new Handler(  ).postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },Toast.LENGTH_LONG );
                    }else{
                        //Email Already Register
                        Toast.makeText( getApplicationContext(),"User Already Exists with same Email",Toast.LENGTH_LONG ).show();
                    }
                }
            }
        } );

    }

    public boolean validate(){
        boolean valid = false;

        //get values from Edittext field
        String UserName = username_edittext.getText().toString();
        String Email = email_edittext.getText().toString();
        String Password = password_edittext.getText().toString();
        //handling validation for username
        if(UserName.isEmpty()){
            valid = false;
            Toast.makeText( getApplicationContext(),"Please Enter Username",Toast.LENGTH_LONG ).show();
        }else{
            if(UserName.length()>5){
                valid = true;
            }else{
                valid = false;
                Toast.makeText( getApplicationContext(),"Username too short",Toast.LENGTH_LONG ).show();
            }
        }
        //handling validation for email field
        if(!Patterns.EMAIL_ADDRESS.matcher( Email ).matches()){
            valid = false;
            Toast.makeText( getApplicationContext(),"Please Enter Valid Email Address",Toast.LENGTH_LONG ).show();
        }else{
            valid = true;
        }
        //Handling validation for password field
        if(Password.isEmpty()){
            valid = false;
            Toast.makeText( getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG ).show();
        }else{
            if(Password.length()>5){
                valid = true;
            }else{
                valid = false;
                Toast.makeText( getApplicationContext(),"Password too short",Toast.LENGTH_SHORT ).show();
            }
        }
        return valid;
    }
}
