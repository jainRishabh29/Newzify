package com.example.newzify.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.newzify.dataClass.User
import com.example.newzify.fragments.MainFragment
import com.example.newzify.fragments.SignUpFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

object AppRepo {

    private val firebase = FirebaseAuth.getInstance()
    private val userMutableData = MutableLiveData<FirebaseUser>()
//    val isAlreadyMember = MutableLiveData<Boolean>()
    private val isLoggedOut: MutableLiveData<Boolean> = MutableLiveData()

    init {
        if (firebase.currentUser != null) {
            userMutableData.value = firebase.currentUser
            isLoggedOut.value = false
        }else{
            isLoggedOut.value = true
            userMutableData.value = null
        }
    }

    fun registerUser(
        email: String, password: String, age: String,
        phoneNumber: String,
        user_address: String,
        bio: String
    ) {
        firebase.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = User(email, password, age, phoneNumber, user_address, bio)
                    val ref = FirebaseDatabase
                        .getInstance("https://newzify-bed8a-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Users")
                    val u = ref.push().key
                    Log.d("firebase", " kya y id h "+ u.toString())
                    if (u != null) {
                        ref.child(u)
                            .setValue(user).addOnCompleteListener {
                                if (it.isSuccessful) {
//                                    isAlreadyMember.value = false
                                    Log.d("firebase", "LoginrepoUserSuccessful")
                                    userMutableData.value = firebase.currentUser
                                } else {
                                    Log.d("firebase", "LoginrepoUserfail")
                                    userMutableData.value = null
                                }
                            }
                    }

                } else {
                    Log.d("firebase", "Loginrepofail")
//                    isAlreadyMember.value = true
                }
            }
    }

    fun logInUser(email: String, password: String){
        firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("check", "appRepo success")
                userMutableData.value = firebase.currentUser
                Log.d("check","apprepo value ${userMutableData.value}")
            } else {
                userMutableData.value = null
                Log.d("check", "appRepo failed")
                Log.d("fireobase", it.exception!!.message.toString())
                Log.d("fireobase", it.exception.toString())
            }
        }
    }

    fun loggedOut() {
        firebase.signOut()
        userMutableData.value = null
        isLoggedOut.value = true
    }


    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        Log.d("check", "appRepo Usermutable ${userMutableData.value}")
        return userMutableData
    }

//    fun alreadyMember(): MutableLiveData<Boolean> {
//        return isAlreadyMember
//    }

    fun getIsLoggedOut(): MutableLiveData<Boolean> {
        return isLoggedOut
    }
}