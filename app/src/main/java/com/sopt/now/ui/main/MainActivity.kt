package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.NowSopt
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.main.home.HomeFragment
import com.sopt.now.ui.main.myPage.MyPageFragment
import com.sopt.now.ui.main.search.SearchFragment
import com.teamwss.websoso.ui.common.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUsername()

        initDefaultFragment(savedInstanceState)

        setupViewModel()
        setupDataBinding()
        setupBottomNavigation()
    }

    private fun initDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance(username))
        }
        binding.bnvMain.selectedItemId = R.id.menu_main_home
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .commit()
    }

    private fun setupViewModel() {
        val factory = BaseFactory { MainViewModel(NowSopt.getUserRepository()) }
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupDataBinding() {
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun setupBottomNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            val selectedFragment = createFragmentByMenuId(item.itemId)
            changeFragment(selectedFragment)
            return@setOnItemSelectedListener true
        }
    }

    private fun createFragmentByMenuId(menuId: Int): Fragment {
        val username = intent.getStringExtra(USER_NAME).orEmpty()
        return when (menuId) {
            R.id.menu_main_search -> SearchFragment.newInstance()
            R.id.menu_main_home -> HomeFragment.newInstance(username)
            R.id.menu_main_my_page -> MyPageFragment.newInstance(username)
            else -> throw IllegalArgumentException()
        }
    }

    private fun getUsername() {
        username = intent.getStringExtra(USER_NAME).orEmpty()
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
