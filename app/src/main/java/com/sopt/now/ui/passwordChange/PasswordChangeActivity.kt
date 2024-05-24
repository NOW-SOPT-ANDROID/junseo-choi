package com.sopt.now.ui.passwordChange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.R
import com.sopt.now.data.remote.request.ChangePasswordRequest
import com.sopt.now.databinding.ActivityPasswordChangeBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.common.base.BindingActivity
import com.sopt.now.ui.passwordChange.PasswordChangeViewModel.Companion.SUCCESS_PASSWORD_CHANGE

class PasswordChangeActivity :
    BindingActivity<ActivityPasswordChangeBinding>(R.layout.activity_password_change) {
    private lateinit var passwordChangeViewModel: PasswordChangeViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        getUserId()
        setupSignInButtonListener()
        observeSignInResult()
    }

    private fun setupViewModel() {
        val passwordChangeFactory = BaseFactory { PasswordChangeViewModel() }
        passwordChangeViewModel =
            ViewModelProvider(this, passwordChangeFactory)[PasswordChangeViewModel::class.java]
    }

    private fun getUserId() {
        userId = intent.getIntExtra(USER_ID, 0)
    }

    private fun setupSignInButtonListener() {
        binding.viewPasswordChangeButton.setOnClickListener {
            performSignIn()
        }
    }

    private fun performSignIn() {
        val input =
            ChangePasswordRequest(
                binding.etPasswordChangeCurrent.text.toString(),
                binding.etPasswordChangeToChange.text.toString(),
                binding.etPasswordChangeConfirm.text.toString(),
            )
        passwordChangeViewModel.performPasswordChange(userId, input)
    }

    private fun observeSignInResult() {
        passwordChangeViewModel.passwordChangeMessage.observe(this) { message ->
            if (message == SUCCESS_PASSWORD_CHANGE) {
                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
                finish()
            } else {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val USER_ID = "USER_ID"

        fun newIntent(
            context: Context,
            userId: Int,
        ): Intent {
            return Intent(context, PasswordChangeActivity::class.java).apply {
                putExtra(USER_ID, userId)
            }
        }
    }
}
