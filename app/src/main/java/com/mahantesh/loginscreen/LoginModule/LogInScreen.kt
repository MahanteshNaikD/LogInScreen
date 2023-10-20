package com.mahantesh.loginscreen.LoginModule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.mahantesh.loginscreen.MainActivity2
import com.mahantesh.loginscreen.R

class logInScreen : AppCompatActivity() {
    lateinit var userNameOrEmail: TextInputEditText;
    lateinit var password: TextInputEditText;
    lateinit var logInButton: AppCompatButton;
    lateinit var forgotPassword: TextView;
    lateinit var faceBook_Button: ImageButton;
    lateinit var google_button: ImageButton;
    lateinit var whatsApp_buttob: ImageButton;
    lateinit var google_plus_button: ImageButton;
    lateinit var signUp_textView: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView();


        logInButton.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java));
//            if(userNameOrEmail.text.toString().isEmpty() && password.text.toString().isEmpty()){
//                println("inside button")
//                Toast.makeText(this,"Please Enter All Feilds",Toast.LENGTH_SHORT).show();
//            }else if(userNameOrEmail.text.toString().isEmpty()){
//                Toast.makeText(this,"Please Enter UserName or Email",Toast.LENGTH_SHORT).show();
//            }else if(password.text.toString().isEmpty()){
//                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
//            }else{
//                startActivity(Intent(this,OtpActivity::class.java));
//            }
        };
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java));
        };
        faceBook_Button.setOnClickListener {
             startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/login/")))
        };
        google_button.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/v3/signin")))
        };
        google_plus_button.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/v3/signin")))
        };
        whatsApp_buttob.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com/")))
        };
        signUp_textView.setOnClickListener {

        };


    }

    private fun initView() {
        userNameOrEmail = findViewById(R.id.userOremailEditText);
        password = findViewById(R.id.passwordEditText);
        logInButton = findViewById(R.id.buttob_login);
        forgotPassword = findViewById(R.id.forgor_password);
        faceBook_Button = findViewById(R.id.faceBook_logIn);
        google_button = findViewById(R.id.google_logIn);
        whatsApp_buttob = findViewById(R.id.whatsapp_logIn);
        google_plus_button = findViewById(R.id.google_plus_logIn);
        signUp_textView = findViewById(R.id.signup_button_text);
    }
}