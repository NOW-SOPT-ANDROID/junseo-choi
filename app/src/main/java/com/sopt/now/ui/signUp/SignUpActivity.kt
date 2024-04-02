package com.sopt.now.ui.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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
            val drinkCapacity = binding.sliderSignUpDrinkCapacity.value.toInt()

            viewModel.performSignUp(username, password, nickname, drinkCapacity)
        }
    }

    private fun observeSignUpResult() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                SignUpUiState.UsernameError -> {
                    binding.etSignUpUsername.error = "아이디는 6자 이상 10자 이하여야 합니다."
                }

                SignUpUiState.PasswordError -> {
                    binding.etSignUpPassword.error = "비밀번호는 8자 이상 12자 이하여야 합니다."
                }

                SignUpUiState.NicknameError -> {
                    binding.etSignUpNickname.error = "닉네임을 입력해주세요."
                }

                SignUpUiState.UsernameTaken -> {
                    binding.etSignUpUsername.error = "이미 사용 중인 아이디입니다."
                }

                SignUpUiState.NicknameTaken -> {
                    binding.etSignUpNickname.error = "이미 사용 중인 닉네임입니다."
                }

                SignUpUiState.Success -> {
                    Toast.makeText(this, "회원가입에 성공하셨습니다!", Toast.LENGTH_SHORT).show()
                    finish()
                }

                SignUpUiState.Failure -> {
                    Snackbar.make(binding.root, "회원가입 오류입니다.", Snackbar.LENGTH_SHORT).show()
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