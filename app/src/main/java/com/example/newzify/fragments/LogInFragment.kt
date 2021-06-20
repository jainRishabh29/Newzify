package com.example.newzify.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.example.newzify.databinding.LogInFragmentBinding
import com.example.newzify.view.MainActivity
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import com.google.firebase.auth.FirebaseAuth


class LogInFragment : Fragment() {

    private var _binding: LogInFragmentBinding? = null
    private val binding get() = _binding!!
    private var validate: Boolean = true
    private val firebase = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LogInFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(false)
        binding.SignUpText.setOnClickListener {
            //go to logIn fragment
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        binding.logInButton.setOnClickListener {
            closeKeyboard()
            binding.logInButton.visibility = View.GONE
            binding.circularPBar.visibility = View.VISIBLE
            val email = binding.emailInput.text.toString().trim()
            val pass = binding.paswordInput.text.toString()
            Log.d("check", "$email and $pass")
            validate = true

            if (email.isEmpty()) {
                binding.logInButton.visibility = View.VISIBLE
                binding.circularPBar.visibility = View.GONE
                binding.emailInput.error = "Email is Mandatory"
                binding.emailInput.requestFocus()
                validate = false
            } else {
                if (pass.isEmpty()) {
                    validate = false
                    binding.logInButton.visibility = View.VISIBLE
                    binding.circularPBar.visibility = View.GONE
                    //binding.tvMaterial.endIconMode = END_ICON_NONE
                    //  binding.paswordInput.error = "Password is Mandatory"
                    binding.paswordInput.setError("Password is Mandatory", null)
                    binding.paswordInput.requestFocus()
                }
            }

            if (validate) {
                firebase.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.logInButton.visibility = View.VISIBLE
                        binding.circularPBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                    } else {
                        binding.logInButton.visibility = View.VISIBLE
                        binding.circularPBar.visibility = View.GONE
                        Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        return view
    }

    fun closeKeyboard() {
        val view = requireActivity().currentFocus
        val inputManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (firebase.currentUser != null) {
            findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
        }
    }
}