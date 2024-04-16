package com.sopt.now.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.NowSopt
import com.sopt.now.R
import com.sopt.now.data.model.Friend
import com.sopt.now.data.model.UserInfoEntity
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.main.MainViewModel
import com.teamwss.websoso.ui.common.base.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var mainViewModel: MainViewModel
    private val adapter: HomeAdapter by lazy { HomeAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupDataBinding()
        setupAdapter()
        getUserInfo()
        observeUserInfo()
    }

    private fun setupViewModel() {
        val mainFactory = BaseFactory { MainViewModel(NowSopt.getUserRepository()) }
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun setupDataBinding() {
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun setupAdapter() {
        binding.rvHome.adapter = adapter
    }

    private fun getUserInfo() {
        mainViewModel.getUserInfo(arguments?.getString(USER_NAME).orEmpty())
    }

    private fun observeUserInfo() {
        mainViewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            updateRecyclerView(userInfo, Friend.dummyData)
        }
    }

    private fun updateRecyclerView(
        userInfo: UserInfoEntity,
        friendList: List<Friend>,
    ) {
        adapter.submitList(userInfo, friendList)
    }

    companion object {
        private const val USER_NAME = "USER_NAME"

        fun newInstance(username: String): HomeFragment {
            return HomeFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(HomeFragment.USER_NAME, username)
                    }
            }
        }
    }
}
