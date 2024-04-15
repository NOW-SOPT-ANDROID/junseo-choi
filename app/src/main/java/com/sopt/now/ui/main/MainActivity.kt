package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.NowSopt
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.teamwss.websoso.ui.common.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupDataBinding()
        loadUserInfo()
    }

    private fun setupViewModel() {
        val factory = BaseFactory { MainViewModel(NowSopt.getUserRepository()) }
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupDataBinding() {
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun loadUserInfo() {
        val username = intent.getStringExtra(USER_NAME).orEmpty()
        mainViewModel.getUserInfo(username)
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
