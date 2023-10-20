package com.mahantesh.loginscreen.LoginModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import com.mahantesh.loginscreen.R

class ResetPassword : AppCompatActivity() {

    lateinit var old_password: TextInputEditText;
    lateinit var new_password: TextInputEditText;
    lateinit var save_button: AppCompatButton;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initViews();




        save_button.setOnClickListener {
            if(old_password.equals(new_password)) {
                Toast.makeText(this,"Old Password and New Password Should Not Same",Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun initViews() {
        old_password = findViewById(R.id.old_password_text);
        new_password = findViewById(R.id.new_password_text);
        save_button = findViewById(R.id.save_button);
    }
}