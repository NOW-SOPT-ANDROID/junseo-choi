package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.ui.signUp.SignUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    companion object {
        private const val USER_NAME = "USER_NAME"

        fun newIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java).apply {
                putExtra(USER_NAME, "USER_NAME")
            }
        }
    }
}