package com.sopt.now.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.NowSopt
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.teamwss.websoso.ui.common.base.BindingActivity

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupSignUpButtonListener()

        observeSignUpResult()
    }

    private fun setupViewModel() {
        val factory = BaseFactory { SignUpViewModel(NowSopt.getUserRepository()) }
        signUpViewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
    }

    private fun setupSignUpButtonListener() {
        binding.viewSignUpButton.setOnClickListener {
            val username = binding.etSignUpUsername.text.toString()
            val password = binding.etSignUpPassword.text.toString()
            val nickname = binding.etSignUpNickname.text.toString()
            val drinkCapacity = binding.sliderSignUpDrinkCapacity.value

            signUpViewModel.performSignUp(username, password, nickname, drinkCapacity)
        }
    }

    private fun observeSignUpResult() {
        signUpViewModel.uiState.observe(this) { state ->
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
                        Snackbar.LENGTH_SHORT,
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
