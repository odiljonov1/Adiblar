package com.odiljonov.adiblar

import Adapter.RvAdapter
import Cache.MySharedPreference
import Model.Adib
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.odiljonov.adiblar.databinding.FragmentSaqlanganBinding

class SaqlanganFragment : Fragment() {

    lateinit var binding: FragmentSaqlanganBinding
    lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaqlanganBinding.inflate(layoutInflater)

        MySharedPreference.init(context)

        binding.searchAdibList.setOnClickListener {
            findNavController().navigate(R.id.searchAdibFragment, bundleOf("type" to 2))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val list = MySharedPreference.objectString
        rvAdapter = RvAdapter(context, list, object : RvAdapter.RvClick{
            override fun onCLick(adib: Adib) {
                findNavController().navigate(R.id.adibInfoFragment, bundleOf("keyAdib" to adib, "keyInt" to 1))
            }
        }, 1)
        binding.rvSaqlangan.adapter = rvAdapter
    }
}