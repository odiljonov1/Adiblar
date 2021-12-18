package com.odiljonov.adiblar

import Adapter.ViewPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.odiljonov.adiblar.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.item_tab_main.view.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ViewPagerAdapter
    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelected = tab?.position!!
                when (tab.position) {
                    0 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.home_selected)
                    }
                    1 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.saqlanganlar_selected)
                    }
                    2 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.settings_selected)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.home_unselected)
                    }
                    1 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.saqlanganlar_unselected)
                    }
                    2 -> {
                        tab.customView?.image_tab_main?.setImageResource(R.drawable.settings_unselected)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout,binding.viewPager){tab,position ->
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_tab_main, null, false)
            tab.customView = inflate
            when(position){
                0->{
                    inflate.image_tab_main.setImageResource(R.drawable.home_unselected)
                }
                1->{
                    inflate.image_tab_main.setImageResource(R.drawable.saqlanganlar_unselected)
                }
                2->{
                    inflate.image_tab_main.setImageResource(R.drawable.settings_unselected)
                }

            }
            if (tabSelected == position){

            }
        }.attach()
    }
}