package com.mahantesh.loginscreen.LoginModule


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.mahantesh.loginscreen.R

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var new_password: TextInputEditText;
    lateinit var confirm_password: TextInputEditText;
    lateinit var save_button: AppCompatButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initViews();



        save_button.setOnClickListener {


            if (new_password.text.toString().equals(confirm_password.text.toString())) {
                Toast.makeText(this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                startActivity(Intent(this, logInScreen::class.java));
            } else {
                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private fun initViews() {
        new_password = findViewById(R.id.new_password_text);
        confirm_password = findViewById(R.id.confirm_password_text);
        save_button = findViewById(R.id.save_button);
    }
}