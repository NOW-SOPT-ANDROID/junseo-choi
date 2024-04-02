package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ui.signUp.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private const val USER_NAME = "USER_NAME"

        fun newIntent(context: Context, username: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(USER_NAME, username)
            }
        }
    }
}