package com.odiljonov.adiblar

import Adapter.ViewPagerAdapter2
import Model.Adib
import Utils.InternetData
import Utils.SearchCLick
import Utils.SearchViewB
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.odiljonov.adiblar.databinding.FragmentAdiblarBinding
import kotlinx.android.synthetic.main.fragment_adiblar.view.*
import kotlinx.android.synthetic.main.item_tab_category.view.*

class AdiblarFragment : Fragment(), SearchCLick {

    lateinit var binding: FragmentAdiblarBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var adapter2: ViewPagerAdapter2

    var tabSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdiblarBinding.inflate(layoutInflater)


        binding.root.search_adib_list.setOnClickListener {
            findNavController().navigate(R.id.searchAdibFragment, bundleOf("type" to 1))
        }

        SearchViewB.s = binding.root.tab_category

        onResume()



        return binding.root
    }

    override fun onResume() {
        super.onResume()

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("Photo")

        InternetData.list.clear()

        firebaseFirestore.collection("adiblar")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val adib = queryDocumentSnapshot.toObject(Adib::class.java)
                        InternetData.list.add(adib)
                    }
                }
                println(InternetData.list)
                adapter2 = ViewPagerAdapter2(childFragmentManager)
                binding.viewPagerCategory.adapter = adapter2
                binding.tabCategory.setupWithViewPager(binding.viewPagerCategory)
                adapter2.notifyDataSetChanged()
                setTabs()
            }

        binding.tabCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelected = tab?.position!!
                val inflate = tab?.customView
                inflate?.item_tab_category_back?.background =
                    resources.getDrawable(R.drawable.ic_tab_background)
                inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val inflate = tab?.customView
                inflate?.item_tab_category_back?.background = null
                inflate?.txt_item_tab_category?.setTextColor(resources.getColor(R.color.black_2))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setTabs() {
        val tabCount = binding.tabCategory.tabCount

        for (i in 0 until tabCount) {
            val tabView =
                LayoutInflater.from(context).inflate(R.layout.item_tab_category, null, false)
            val tab = binding.tabCategory.getTabAt(i)
            tab?.customView = tabView

            when (i) {
                0 -> {
                    tabView.txt_item_tab_category.text = "Mumtoz adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }
                1 -> {
                    tabView.txt_item_tab_category.text = "O'zbek adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }
                2 -> {
                    tabView.txt_item_tab_category.text = "Jahon adabiyoti"
                    tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.black_2))
                    tabView.item_tab_category_back.background = null
                }

            }
            if (i == tabSelected) {
                tabView.item_tab_category_back.background =
                    resources.getDrawable(R.drawable.ic_tab_background)
                tabView.txt_item_tab_category.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabSelected = 0
    }

    override fun searchOpen() {
        binding.root.tab_category.visibility = View.INVISIBLE
        Toast.makeText(context, "Invisible", Toast.LENGTH_SHORT).show()
    }

    override fun searchClose() {
        binding.root.tab_category.visibility = View.INVISIBLE
    }


}