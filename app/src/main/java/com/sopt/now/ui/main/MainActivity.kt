package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val username = intent.getStringExtra(USER_NAME) ?: ""
        viewModel.getUserInfo(username)
    }

    companion object {
        private const val USER_NAME = "USER_NAME"

        fun newIntent(context: Context, username: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(USER_NAME, username)
            }
        }
    }
}