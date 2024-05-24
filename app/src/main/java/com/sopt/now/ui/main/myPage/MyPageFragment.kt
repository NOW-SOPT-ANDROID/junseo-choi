package com.sopt.now.ui.main.myPage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.ui.common.base.BaseFactory
import com.sopt.now.ui.common.base.BindingFragment
import com.sopt.now.ui.main.MainViewModel
import com.sopt.now.ui.passwordChange.PasswordChangeActivity
import com.sopt.now.ui.signIn.SignInActivity

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupDataBinding()
        setupPasswordChangeButtonListener()
        setupSignOutButtonListener()
        loadUserInfo()
    }

    private fun setupViewModel() {
        val mainFactory = BaseFactory { MainViewModel() }
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun setupDataBinding() {
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun setupPasswordChangeButtonListener() {
        binding.tvMyPagePasswordChange.setOnClickListener {
            navigateToPasswordChangeActivity()
        }
    }

    private fun navigateToPasswordChangeActivity() {
        val intent =
            PasswordChangeActivity.newIntent(requireContext(), arguments?.getInt(USER_ID) ?: 0)
        startActivity(intent)
    }

    private fun setupSignOutButtonListener() {
        binding.tvMyPageSignOut.setOnClickListener {
            navigateToSignInActivity()
        }
    }

    private fun navigateToSignInActivity() {
        val intent = SignInActivity.newIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
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
