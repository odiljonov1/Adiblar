package com.odiljonov.adiblar

import Adapter.RvAdapter
import Cache.MySharedPreference
import Model.Adib
import Utils.InternetData
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.odiljonov.adiblar.databinding.FragmentAdibInfoBinding
import com.squareup.picasso.Picasso

class AdibInfoFragment : Fragment() {

    lateinit var binding: FragmentAdibInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdibInfoBinding.inflate(layoutInflater)

        var adib = arguments?.getSerializable("keyAdib") as Adib

        val mL = MySharedPreference.objectString

        Picasso.get().load(adib.imageUri).into(binding.imageInfo)
        binding.txtNameInfo.text = adib.nameAndLastname
        binding.txtYilInfo.text = "${adib.tugilganYili} - ${adib.olganYili}"
        binding.txtInfo.text = adib.info

        binding.cardBack.setOnClickListener { findNavController().popBackStack() }

        binding.card2.setOnClickListener {
            var index = -1
            for (a in mL.indices) {
                if (mL[a].imageUri == adib.imageUri) {
                    index = a
                    break
                }
            }
            if (index != -1) {
                mL.removeAt(index)
                MySharedPreference.objectString = mL
                binding.card2.setImageResource(R.drawable.saqlangan_1)
            } else {
                mL.add(adib)
                MySharedPreference.objectString = mL
                binding.card2.setImageResource(R.drawable.saqlangan_2)
            }
        }

        // searchView isn't in working order

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDestroy() {
        super.onDestroy()

        val rvAdibAdapter = arguments?.getSerializable("keyAdapter") as RvAdapter
        rvAdibAdapter.list = InternetData.list
        rvAdibAdapter.notifyDataSetChanged()
    }
}