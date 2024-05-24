package com.sopt.now.ui.main.search

import com.sopt.now.R
import com.sopt.now.databinding.FragmentSearchBinding
import com.sopt.now.ui.common.base.BindingFragment

class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    companion object {
        fun newInstance() = SearchFragment()
    }
}
