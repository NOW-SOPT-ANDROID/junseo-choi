package com.sopt.now.ui.signIn

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.NowSopt
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignInBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.main.MainActivity
import com.sopt.now.ui.signUp.SignUpActivity
import com.teamwss.websoso.ui.common.base.BindingActivity

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupSignInButtonListener()
        setupNavigateToSignUpListener()

        observeSignInResult()
    }

    private fun setupViewModel() {
        val factory = BaseFactory { SignInViewModel(NowSopt.getUserRepository()) }
        signInViewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]
    }

    private fun observeSignInResult() {
        signInViewModel.uiState.observe(this) { state ->
            Log.e("SignInActivityTest", "observeSignInResult: $state")
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
            Log.e("SignInActivityTest", "Button")
            signInViewModel.checkIsInputValidAndSignIn(
                binding.etSignInUsername.text.toString(),
                binding.etSignInPassword.text.toString(),
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
