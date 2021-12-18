package com.odiljonov.adiblar

import Adapter.RvAdapter
import Model.Adib
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.odiljonov.adiblar.databinding.FragmentJahonAdabiyotiBinding

class JahonAdabiyotiFragment : Fragment() {

    lateinit var binding: FragmentJahonAdabiyotiBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var rvAdapter: RvAdapter
    var listAdib = ArrayList<Adib>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJahonAdabiyotiBinding.inflate(layoutInflater)

        firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("Adib")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    listAdib = ArrayList()
                    result?.forEach { queryDocumentSnapshot ->
                        val user = queryDocumentSnapshot.toObject(Adib::class.java)
                        listAdib.add(user)
                        val listM = ArrayList<Adib>()
                        for (a in listAdib) {
                            if (a.turi == 2) {
                                listM.add(a)
                            }
                        }
                        rvAdapter =
                            RvAdapter(binding.root.context, listM, object : RvAdapter.RvClick {
                                override fun onCLick(adib: Adib) {
                                    findNavController().navigate(
                                        R.id.adibInfoFragment,
                                        bundleOf("keyAdib" to adib, "keyAdapter" to rvAdapter)
                                    )
                                }

                            })
                        binding.rvJahon.adapter = rvAdapter
                    }
                }
            }

        onResume()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

}