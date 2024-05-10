package com.sopt.now.ui.signIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.R
import com.sopt.now.data.remote.request.SignInRequest
import com.sopt.now.databinding.ActivitySignInBinding
import com.sopt.now.ui.common.base.BindingActivity
import com.sopt.now.ui.main.MainActivity
import com.sopt.now.ui.signUp.SignUpActivity

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        setupSignInButtonListener()
        observeSignInResult()
        setupNavigateToSignUpListener()
    }

    private fun setupSignInButtonListener() {
        binding.viewSignInButton.setOnClickListener {
            performSignIn()
        }
    }

    private fun performSignIn() {
        val inputUsername: String = binding.etSignInUsername.text.toString()
        val inputPassword: String = binding.etSignInPassword.text.toString()

        signInViewModel.performSignIn(SignInRequest(inputUsername, inputPassword))
    }

    private fun observeSignInResult() {
        signInViewModel.signInMessage.observe(this) { message ->
            if (message.split("/")[0] == SignInViewModel.SUCCESS_SIGN_IN) {
                Toast.makeText(
                    this,
                    getString(R.string.success_sign_in),
                    Toast.LENGTH_SHORT,
                ).show()
                navigateToMainActivity(message.split("/")[1].toInt())
                finish()
            } else {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity(userId: Int) {
        val intent = MainActivity.newIntent(this, userId)
        startActivity(intent)
        finish()
    }

    private fun setupNavigateToSignUpListener() {
        binding.viewSignInSignUpButton.setOnClickListener {
            val intent = SignUpActivity.newIntent(this)
            startActivity(intent)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}
