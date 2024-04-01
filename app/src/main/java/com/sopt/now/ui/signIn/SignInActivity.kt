package com.sopt.now.ui.signIn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignInBinding
import com.sopt.now.ui.signUp.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigateToSignUpListener()
    }

    private fun setupNavigateToSignUpListener() {
        binding.viewSignInSignUpButton.setOnClickListener {
            val intent = SignUpActivity.newIntent(this)
            startActivity(intent)
        }
    }
}