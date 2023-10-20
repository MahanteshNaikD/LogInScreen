package com.mahantesh.loginscreen.LoginModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText
import com.mahantesh.loginscreen.R

class OtpActivity : AppCompatActivity() {


    lateinit var otp_text: TextInputEditText;
    lateinit var continue_button: AppCompatButton;
    lateinit var resend_otp: AppCompatTextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        initViews();


        continue_button.setOnClickListener {
            if(otp_text.text.toString().isEmpty()){
                Toast.makeText(this,"OTP should Not Empty",Toast.LENGTH_SHORT).show();
            }
        }


        resend_otp.setOnClickListener {
            Toast.makeText(this,"OTP Send Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    private fun initViews() {
        otp_text = findViewById(R.id.enter_your_otp_text_view);
        continue_button = findViewById(R.id.otp_continue_button);
        resend_otp = findViewById(R.id.resend_otp_text);
    }
}