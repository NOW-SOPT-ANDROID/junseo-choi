package com.sopt.now.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.data.remote.response.GetFriendsResponse
import com.sopt.now.data.remote.response.GetUserResponse
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
        getFriendsInfo()
        observeUserInfo()
        observeFriendsInfo()
    }

    private fun setupViewModel() {
        val mainFactory = BaseFactory { MainViewModel() }
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
        mainViewModel.getUserInfo(arguments?.getInt(USER_ID) ?: 0)
    }

    private fun getFriendsInfo() {
        mainViewModel.getFriendsInfo()
    }

    private fun observeUserInfo() {
        mainViewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            if (userInfo != GetUserResponse.defaultUser) {
                updateRecyclerView(
                    userInfo,
                    mainViewModel.friendsInfo.value ?: emptyList(),
                )
            }
        }
    }

    private fun observeFriendsInfo() {
        mainViewModel.friendsInfo.observe(viewLifecycleOwner) { friendList ->
            if (friendList.isNotEmpty()) {
                updateRecyclerView(
                    mainViewModel.userInfo.value ?: GetUserResponse.defaultUser,
                    friendList,
                )
            }
        }
    }

    private fun updateRecyclerView(
        userInfo: GetUserResponse.User,
        friendList: List<GetFriendsResponse.Data>,
    ) {
        adapter.submitList(userInfo, friendList)
    }

    companion object {
        private const val USER_ID = "USER_ID"

        fun newInstance(userId: Int): HomeFragment {
            return HomeFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt(USER_ID, userId)
                    }
            }
        }
    }
}
