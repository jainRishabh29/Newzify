package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.example.newzify.databinding.LogInFragmentBinding
import com.example.newzify.viewModel.LogInSignUpViewModel
import com.example.newzify.viewModel.MainFragmentViewModel
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private val firebase = FirebaseAuth.getInstance()
    private var _binding: LogInFragmentBinding? = null
    private lateinit var viewModel : LogInSignUpViewModel
    private val binding get() = _binding!!
    private var validate: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = LogInFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(LogInSignUpViewModel::class.java)

//        viewModel.getIsLoggedOut().observe(viewLifecycleOwner ,{
//            if (!it){
//                findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
//            }
//        })

        binding.SignUpText.setOnClickListener {
            //go to logIn fragment
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
        binding.logInButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val pass = binding.paswordInput.text.toString()
            Log.d("check", "$email and $pass")
            validate = true

            if (email.isEmpty()) {
                binding.emailInput.error = "Email is Mandatory"
                binding.emailInput.requestFocus()
                validate = false
            } else {
                if (pass.isEmpty()) {
                    validate = false
                    binding.paswordInput.error = "Password is Mandatory"
                    binding.paswordInput.requestFocus()
                }
            }

            if (validate) {
                viewModel.loginUser(email,pass)
                viewModel.getUser().observe(viewLifecycleOwner ,  {
                    if (it != null){
                        findNavController().navigate(R.id.action_logInFragment_to_mainFragment)
                    }
                    else{
                        Toast.makeText(context,"Wrong Credentials",Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }
        return view
    }

}