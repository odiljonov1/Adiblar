package com.odiljonov.adiblar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.odiljonov.adiblar.databinding.FragmentDasturInfoBinding

class DasturInfoFragment : Fragment() {

    lateinit var binding: FragmentDasturInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDasturInfoBinding.inflate(layoutInflater)

        binding.telefon.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:" + Uri.encode("+998903013607"))
            )
            startActivity(intent)
        }

        binding.telegram.setOnClickListener {
            val url = "https://t.me/Muhammad_Azim"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


        // Inflate the layout for this fragment
        return binding.root
    }
}