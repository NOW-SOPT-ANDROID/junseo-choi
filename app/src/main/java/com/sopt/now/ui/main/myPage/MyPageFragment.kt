package com.sopt.now.ui.main.myPage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.main.MainViewModel
import com.teamwss.websoso.ui.common.base.BindingFragment

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupDataBinding()

        loadUserInfo()
    }

    private fun setupViewModel() {
        val factory = BaseFactory { MainViewModel() }
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupDataBinding() {
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun loadUserInfo() {
        mainViewModel.getUserInfo(arguments?.getInt(USER_ID) ?: 0)
    }

    companion object {
        private const val USER_ID = "USER_ID"

        fun newInstance(userId: Int): MyPageFragment {
            return MyPageFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt(USER_ID, userId)
                    }
            }
        }
    }
}
