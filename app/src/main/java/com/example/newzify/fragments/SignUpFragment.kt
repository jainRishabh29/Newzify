package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.example.newzify.dataClass.User
import com.example.newzify.databinding.SignUpFragmentBinding
import com.example.newzify.repository.AppRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class SignUpFragment : Fragment() {
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" + "(?=.*[0-9])" + "(?=.*[A-Z])" + ".{8,20}" + "$"
    )

    //    companion object {
//        fun newInstance() = SignUpFragment()
//    }
    private val firebase = FirebaseAuth.getInstance()
    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private var validate: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.signInText.setOnClickListener {
            //go to signup fragment
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val pass = binding.paswordInput.text.toString()
            val conformPass = binding.confirmPasswordInput.text.toString()
            val age = binding.ageInput.text.toString().trim()
            val phone = binding.phoneNumberInput.text.toString().trim()
            val address = binding.addressInput.text.toString().trim()
            val bio = binding.bioInput.text.toString().trim()
            validate = true

            if (email.isEmpty()) {
                binding.emailInput.error = "Email is Mandatory"
                binding.emailInput.requestFocus()
                validate = false
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    validate = false
                    binding.emailInput.requestFocus()
                    Toast.makeText(this.context, "Please enter valid email", Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (pass.isEmpty()) {
                        validate = false
                        binding.paswordInput.error = "Password is Mandatory"
                        binding.paswordInput.requestFocus()
                    } else {
                        if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                            validate = false
                            binding.paswordInput.requestFocus()
                            Toast.makeText(
                                this.context,
                                "Password must contain atleast a capital letter, a digit and should be of length 8-20",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            if (conformPass.isEmpty()) {
                                validate = false
                                binding.confirmPasswordInput.error = "Field is Mandatory"
                                binding.confirmPasswordInput.requestFocus()
                            } else {
                                if (pass.compareTo(conformPass) != 0) {
                                    validate = false
                                    binding.confirmPasswordInput.requestFocus()
                                    Toast.makeText(
                                        this.context,
                                        "Password doesn't match! please try again",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
            if (!Patterns.PHONE.matcher(phone)
                    .matches() && !phone.isEmpty() && phone.length != 10
            ) {
                validate = false
                Toast.makeText(this.context, "Please enter valid phone number", Toast.LENGTH_LONG)
                    .show()
            }


            if (validate) {
            }

        }

        return view
    }

}