package com.odiljonov.adiblar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.odiljonov.adiblar.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.addCard.setOnClickListener {
            findNavController().navigate(R.id.addFragment)
        }

        binding.infoCard.setOnClickListener {
            findNavController().navigate(R.id.dasturInfoFragment)
        }
        binding.shareCard.setOnClickListener {
            val shareBody = "Play market linki"
            val shareSub = "Description"

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            startActivity(shareIntent)
        }
        return binding.root
    }
}