package com.example.newzify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.google.firebase.auth.FirebaseAuth

class SignOutFragment : Fragment() {

    private val firebase = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebase.signOut()
        findNavController().navigate(R.id.action_signOutFragment_to_logInFragment)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_out, container, false)
    }

}