package com.fg.practiceecom.presentation.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fg.practiceecom.R
import com.fg.practiceecom.databinding.FragmentHomeBinding
import com.fg.practiceecom.presentation.adapters.HomeViewpagerAdapter
import com.fg.practiceecom.presentation.fragments.categories.AccessoryFragment
import com.fg.practiceecom.presentation.fragments.categories.TshirtFragment
import com.fg.practiceecom.presentation.fragments.categories.ShoeFragment
import com.fg.practiceecom.presentation.fragments.categories.FurnitureFragment
import com.fg.practiceecom.presentation.fragments.categories.MainCategoryFragment
import com.fg.practiceecom.presentation.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            TshirtFragment(),
            ShoeFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Tshirt"
                2 -> tab.text = "Shoe"
                3 -> tab.text = "Coat"
                4 -> tab.text = "Sweatshirt"
                5 -> tab.text = "Trouser"
            }
        }.attach()
    }


}