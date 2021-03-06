package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.example.newzify.dataClass.User
import com.example.newzify.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private val firebase = FirebaseAuth.getInstance()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var rootnode: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var user: FirebaseUser
    lateinit var userr : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        rootnode = FirebaseDatabase.getInstance("https://newzify-bed8a-default-rtdb.asia-southeast1.firebasedatabase.app/")
        user = FirebaseAuth.getInstance().currentUser!!
        reference = rootnode.getReference("Users").child(user.uid)
        Log.d("access", "batao ${user.uid}")

        reference.get().addOnSuccessListener {
            val userEmail : String = it.child("email").value.toString()
            val userAge : String = it.child("age").value.toString()
            val userAddress : String = it.child("user_address").value.toString()
            val userPhone : String = it.child("phoneNumber").value.toString()
            val userBio : String = it.child("bio").value.toString()
            binding.progressBar.visibility = View.GONE

            binding.emailText.text = userEmail
            binding.ageText.text = userAge
            binding.addressText.text = userAddress
            binding.phoneText.text = userPhone
            binding.bioText.text = userBio

            Log.d("snapshot",it.value.toString())
        }

        return view
    }
}