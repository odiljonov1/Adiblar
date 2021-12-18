package com.odiljonov.adiblar

import Model.Adib
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.odiljonov.adiblar.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    private val TAG = "AddFragment"
    lateinit var firestore: FirebaseFirestore
    var list = ArrayList<Adib>()

    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    var imageUrl: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)

        firestore = FirebaseFirestore.getInstance()

        firebaseStorage = FirebaseStorage.getInstance()

        reference = firebaseStorage.getReference("Photo")

        binding.btnImageAdib.setOnClickListener {
            getImageContent.launch("image/*")
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.btnSave.setOnClickListener {
            Toast.makeText(binding.root.context, "Pressed", Toast.LENGTH_SHORT).show()
            val nameAndLastname = binding.edtIsm.text.toString()
            val tugilganYili = binding.edtTugilgan.text.toString()
            val olganYili = binding.edtVafot.text.toString()
            val info = binding.edtMalumot.text.toString()
            val turi = binding.spinTuri.selectedItemPosition

            if (imageUrl!="" && nameAndLastname!="" && tugilganYili!="" && olganYili!="" && info!=""){
                val adib = Adib(imageUrl, nameAndLastname, tugilganYili, olganYili, turi, info)

                firestore.collection("Adib")
                    .add(adib)
                    .addOnSuccessListener {
                        Toast.makeText(binding.root.context, "${adib.nameAndLastname} yuklandi ", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.homeFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(binding.root.context, "Error", Toast.LENGTH_SHORT).show()
                    }
            }

        }
        return binding.root
    }

    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imgAdib.setImageURI(uri)

            val m = System.currentTimeMillis()

            val uploadTask = reference.child(m.toString()).putFile(uri)

            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imageUri ->
                        imageUrl = imageUri.toString()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(binding.root.context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
}