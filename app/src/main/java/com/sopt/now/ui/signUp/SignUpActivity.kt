package com.sopt.now.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.R
import com.sopt.now.data.remote.request.SignUpRequest
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.ui.common.base.BindingActivity
import com.sopt.now.ui.signUp.SignUpViewModel.Companion.SUCCESS_SIGN_UP

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        setupSignUpButtonListener()
        observeSignUpResult()
    }

    private fun setupSignUpButtonListener() {
        binding.viewSignUpButton.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val request =
            SignUpRequest(
                authenticationId = binding.etSignUpUsername.text.toString(),
                password = binding.etSignUpPassword.text.toString(),
                nickname = binding.etSignUpNickname.text.toString(),
                phone = binding.etSignUpPhoneNumber.text.toString(),
            )
        signUpViewModel.performSignUp(request)
    }

    private fun observeSignUpResult() {
        signUpViewModel.signUpMessage.observe(this) { message ->
            if (message.split("/")[0] == SUCCESS_SIGN_UP) {
                Toast.makeText(
                    this,
                    getString(R.string.success_sign_up, message.split("/")[1]),
                    Toast.LENGTH_SHORT,
                ).show()
                finish()
            } else {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java).apply {}
        }
    }
}
