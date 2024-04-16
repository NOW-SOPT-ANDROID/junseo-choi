package com.sopt.now.ui.main.home

import com.sopt.now.R
import com.sopt.now.databinding.FragmentHomeBinding
import com.teamwss.websoso.ui.common.base.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }
}
