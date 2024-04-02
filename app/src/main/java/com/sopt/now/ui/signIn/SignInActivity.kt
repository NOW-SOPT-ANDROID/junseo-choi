package com.sopt.now.ui.signIn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignInBinding
import com.sopt.now.ui.main.MainActivity
import com.sopt.now.ui.signUp.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val viewModel: SignInViewModel by viewModels { SignInViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSignInResult()
        setupSignInButtonListener()
        setupNavigateToSignUpListener()
    }

    private fun observeSignInResult() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                SignInUiState.UsernameBlank -> {
                    binding.etSignInUsername.error =
                        getString(R.string.error_sign_in_username_blank)
                }

                SignInUiState.PasswordBlank -> {
                    binding.etSignInPassword.error =
                        getString(R.string.error_sign_in_password_blank)
                }

                SignInUiState.UsernameWrong -> {
                    binding.etSignInUsername.error =
                        getString(R.string.error_sign_in_username_wrong)
                }

                SignInUiState.PasswordWrong -> {
                    binding.etSignInPassword.error =
                        getString(R.string.error_sign_in_password_wrong)
                }

                SignInUiState.Failure -> {
                    binding.etSignInUsername.error = getString(R.string.error_sign_in_failure)
                }

                SignInUiState.Success -> {
                    Toast.makeText(this, getString(R.string.success_sign_in), Toast.LENGTH_SHORT)
                        .show()
                    navigateToMainActivity()
                }

                else -> {}
            }
        }
    }

    private fun setupSignInButtonListener() {
        binding.viewSignInButton.setOnClickListener {
            viewModel.performSignIn(
                binding.etSignInUsername.text.toString(),
                binding.etSignInPassword.text.toString()
            )
        }
    }

    private fun setupNavigateToSignUpListener() {
        binding.viewSignInSignUpButton.setOnClickListener {
            val intent = SignUpActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity() {
        val inputUsername: String = binding.etSignInUsername.text.toString()
        val intent = MainActivity.newIntent(this, inputUsername)
        startActivity(intent)
        finish()
    }
}