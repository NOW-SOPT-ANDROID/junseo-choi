package com.sopt.now.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignUpButtonListener()
        observeSignUpResult()
    }

    private fun setupSignUpButtonListener() {
        binding.viewSignUpButton.setOnClickListener {
            val username = binding.etSignUpUsername.text.toString()
            val password = binding.etSignUpPassword.text.toString()
            val nickname = binding.etSignUpNickname.text.toString()
            val drinkCapacity = binding.sliderSignUpDrinkCapacity.value

            viewModel.performSignUp(username, password, nickname, drinkCapacity)
        }
    }

    private fun observeSignUpResult() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                SignUpUiState.UsernameError -> {
                    binding.etSignUpUsername.error = getString(R.string.error_sign_up_username)
                }

                SignUpUiState.PasswordError -> {
                    binding.etSignUpPassword.error = getString(R.string.error_sign_up_password)
                }

                SignUpUiState.NicknameError -> {
                    binding.etSignUpNickname.error = getString(R.string.error_sign_up_nickname)
                }

                SignUpUiState.UsernameTaken -> {
                    binding.etSignUpUsername.error =
                        getString(R.string.error_sign_up_username_taken)
                }

                SignUpUiState.NicknameTaken -> {
                    binding.etSignUpNickname.error =
                        getString(R.string.error_sign_up_nickname_taken)
                }

                SignUpUiState.Success -> {
                    Toast.makeText(this, getString(R.string.success_sign_up), Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

                SignUpUiState.Failure -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_sign_up_failure),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java).apply {}
        }
    }
}