package com.sopt.now.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.ui.common.base.BindingActivity
import com.sopt.now.ui.main.home.HomeFragment
import com.sopt.now.ui.main.myPage.MyPageFragment
import com.sopt.now.ui.main.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserId()
        initDefaultFragment(savedInstanceState)
        setupDataBinding()
        setupBottomNavigation()
    }

    private fun getUserId() {
        userId = intent.getIntExtra(USER_ID, 0)
    }

    private fun initDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance(userId))
        }
        binding.bnvMain.selectedItemId = BottomNavItems.HOME.id
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .commit()
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
        return when (menuId) {
            BottomNavItems.SEARCH.id -> SearchFragment.newInstance()
            BottomNavItems.HOME.id -> HomeFragment.newInstance(userId)
            BottomNavItems.MY_PAGE.id -> MyPageFragment.newInstance(userId)
            else -> throw IllegalArgumentException()
        }
    }

    companion object {
        private const val USER_ID = "USER_ID"

        fun newIntent(
            context: Context,
            userId: Int,
        ): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(USER_ID, userId)
            }
        }
    }
}
