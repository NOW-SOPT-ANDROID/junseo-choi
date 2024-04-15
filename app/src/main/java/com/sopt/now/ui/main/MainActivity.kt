package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.teamwss.websoso.ui.common.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()
        loadUserInfo()
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = viewModel
        }
    }

    private fun loadUserInfo() {
        val username = intent.getStringExtra(USER_NAME) ?: ""
        viewModel.getUserInfo(username)
    }

    companion object {
        private const val USER_NAME = "USER_NAME"

        fun newIntent(
            context: Context,
            username: String,
        ): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(USER_NAME, username)
            }
        }
    }
}
