package com.odiljonov.adiblar

import Adapter.RvAdapter
import Cache.MySharedPreference
import Model.Adib
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.odiljonov.adiblar.databinding.FragmentSearchAdibBinding

class SearchAdibFragment : Fragment() {

    lateinit var binding: FragmentSearchAdibBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var rvAdapter: RvAdapter
    var listAdib = ArrayList<Adib>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchAdibBinding.inflate(layoutInflater)
        firebaseFirestore = FirebaseFirestore.getInstance()
        MySharedPreference.init(binding.root.context)

        val type = arguments?.getInt("type")

        if (type == 1) {
            adibList()
        }else{
            saqlanganList()
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()


    }

    private fun filterAdib(toString: String) {
        var filteredList: ArrayList<Adib>
        filteredList = ArrayList()
        filteredList.sortBy { Adib -> Adib.nameAndLastname }

        for (item in listAdib) {
            if (item.nameAndLastname!!.toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item)
                filteredList.sortBy { Adib -> Adib.nameAndLastname }
            }
        }

        rvAdapter.filterList(filteredList)
    }

    private fun adibList() {
        firebaseFirestore.collection("Adib")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    listAdib = ArrayList()
                    result?.forEach { queryDocumentSnapshot ->
                        val user = queryDocumentSnapshot.toObject(Adib::class.java)
                        listAdib.add(user)
                        binding.searchAdib.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                s: CharSequence?,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {
                                filterAdib(s.toString())
                            }

                            override fun onTextChanged(
                                s: CharSequence?,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {

                            }

                            override fun afterTextChanged(s: Editable?) {
                                filterAdib(s.toString())
                            }
                        })

                        rvAdapter =
                            RvAdapter(binding.root.context, listAdib, object : RvAdapter.RvClick {
                                override fun onCLick(adib: Adib) {
                                    findNavController().navigate(
                                        R.id.adibInfoFragment,
                                        bundleOf("keyAdib" to adib, "keyAdapter" to rvAdapter)
                                    )
                                }

                            })
                        rvAdapter.notifyDataSetChanged()
                        binding.rvSearch.adapter = rvAdapter
                    }
                }
            }
    }

    private fun saqlanganList() {
        val saqlanganList = MySharedPreference.objectString

        binding.searchAdib.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                filterSaqlangan(s.toString())
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterSaqlangan(s.toString())
            }
        })

        rvAdapter =
            RvAdapter(binding.root.context, saqlanganList, object : RvAdapter.RvClick {
                override fun onCLick(adib: Adib) {
                    findNavController().navigate(
                        R.id.adibInfoFragment,
                        bundleOf("keyAdib" to adib, "keyAdapter" to rvAdapter)
                    )
                }

            })
        rvAdapter.notifyDataSetChanged()
        binding.rvSearch.adapter = rvAdapter
    }

    private fun filterSaqlangan(toString: String) {
        var filteredList: ArrayList<Adib>
        val mL = MySharedPreference.objectString
        filteredList = ArrayList()
        filteredList.sortBy { Adib -> Adib.nameAndLastname }

        for (item in mL) {
            if (item.nameAndLastname!!.toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item)
                filteredList.sortBy { Adib -> Adib.nameAndLastname }
            }
        }

        rvAdapter.filterList(filteredList)
    }
}