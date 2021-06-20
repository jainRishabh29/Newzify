package com.example.newzify.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newzify.R
import com.example.newzify.dataClass.User
import com.example.newzify.databinding.SignUpFragmentBinding
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

        binding.emailInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.emailInputLay.helperText =
                        "Please enter valid email"
                } else {
                    binding.emailInputLay.helperText = ""
                }
            }
        })

        binding.paswordInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (!PASSWORD_PATTERN.matcher(s).matches()) {
                    binding.passLay.helperText =
                        "Password must contain atleast a capital letter, a digit and should be of length 8-20"
                } else {
                    binding.passLay.helperText = ""
                }
            }
        })

        binding.confirmPasswordInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (!(binding.paswordInput.text.toString().compareTo(s.toString()) == 0)) {
                    binding.cnfrmPassLay.helperText = "Passwords doesn't match!"
                } else if (binding.confirmPasswordInput.toString().isNotEmpty()) {
                    binding.cnfrmPassLay.helperText = ""
                }
            }
        })

        binding.signUpButton.setOnClickListener {
            closeKeyboard()
            binding.signUpButton.visibility = View.GONE
            binding.circularProgBar.visibility = View.VISIBLE

            val email = binding.emailInput.text.toString().trim()
            val pass = binding.paswordInput.text.toString()
            val conformPass = binding.confirmPasswordInput.text.toString()
            val age = binding.ageInput.text.toString().trim()
            val phone = binding.phoneNumberInput.text.toString().trim()
            val address = binding.addressInput.text.toString().trim()
            val bio = binding.bioInput.text.toString().trim()
            validate = true

            if (email.isEmpty()) {
                binding.signUpButton.visibility = View.VISIBLE
                binding.circularProgBar.visibility = View.GONE
                binding.emailInput.error = "Email is Mandatory"
                binding.emailInput.requestFocus()
                validate = false
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    validate = false
                    binding.signUpButton.visibility = View.VISIBLE
                    binding.circularProgBar.visibility = View.GONE
                    binding.emailInput.error = "Please enter valid email"
                    binding.emailInput.requestFocus()
                } else {
                    if (pass.isEmpty()) {
                        validate = false
                        binding.signUpButton.visibility = View.VISIBLE
                        binding.circularProgBar.visibility = View.GONE
                        binding.paswordInput.setError("Password is Mandatory", null)
                        binding.paswordInput.requestFocus()
                    } else {
                        if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                            validate = false
                            binding.signUpButton.visibility = View.VISIBLE
                            binding.circularProgBar.visibility = View.GONE
                            binding.paswordInput.requestFocus()
                            Toast.makeText(
                                this.context,
                                "Password must contain atleast a capital letter, a digit and should be of length 8-20",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            if (conformPass.isEmpty()) {
                                validate = false
                                binding.signUpButton.visibility = View.VISIBLE
                                binding.circularProgBar.visibility = View.GONE
                                binding.paswordInput.setError("Conform Password is Mandatory", null)
                                binding.confirmPasswordInput.requestFocus()
                            } else {
                                if (pass.compareTo(conformPass) != 0) {
                                    validate = false
                                    binding.signUpButton.visibility = View.VISIBLE
                                    binding.circularProgBar.visibility = View.GONE
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
            if (phone.isNotEmpty()) {
                if (!Patterns.PHONE.matcher(phone).matches() || phone.length != 10) {
                    validate = false
                    binding.signUpButton.visibility = View.VISIBLE
                    binding.circularProgBar.visibility = View.GONE
                    binding.phoneNumberInput.error = "Please enter valid phone number"
                    binding.phoneNumberInput.requestFocus()
                }
            }
            if (age.isNotEmpty()) {
                if (age.toInt() in 1..100) {
                } else {
                    validate = false
                    binding.signUpButton.visibility = View.VISIBLE
                    binding.circularProgBar.visibility = View.GONE
                    binding.ageInput.error = "Please enter valid age"
                    binding.ageInput.requestFocus()
                }
            }
            if (validate) {
                firebase.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val ref = FirebaseDatabase
                                .getInstance("https://newzify-bed8a-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("Users")
//                            val userId = ref.push().key
                            val userId = FirebaseAuth.getInstance().currentUser!!.uid
                            val user = User(email, pass, age, phone, address, bio)
                            if (userId != null) {
                                ref.child(userId)
                                    .setValue(user).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            binding.signUpButton.visibility = View.VISIBLE
                                            binding.circularProgBar.visibility = View.GONE
                                            findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
                                        } else {
                                            binding.signUpButton.visibility = View.VISIBLE
                                            binding.circularProgBar.visibility = View.GONE
                                            Toast.makeText(
                                                context,
                                                "Sign Up fail",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("firebase", "LoginrepoUserfail")
                                        }
                                    }
                            }
                        } else {
                            binding.signUpButton.visibility = View.VISIBLE
                            binding.circularProgBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Already have an account please Log In",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("firebase", "Loginrepofail")
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
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}