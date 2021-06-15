package com.example.newzify.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newzify.dataClass.User
import com.example.newzify.repository.AppRepo
import com.example.newzify.util.log
import com.google.firebase.auth.FirebaseUser

class LogInSignUpViewModel : ViewModel() {

    var user: MutableLiveData<FirebaseUser> = AppRepo.getUserMutableLiveData()
 //   var isLoggedOut : MutableLiveData<Boolean> = AppRepo.getIsLoggedOut()

    fun registerUser(
        email: String, password: String, age: String,
        phoneNumber: String,
        user_address: String,
        bio: String
    ) {
        AppRepo.registerUser(email, password, age, phoneNumber, user_address, bio)
    }

    fun getUser() : LiveData<FirebaseUser> {
        return user
    }

    fun loginUser( email: String, password: String) {
        log("viewmodel loginUser")
        AppRepo.logInUser(email, password)
    }

//    fun getIsLoggedOut(): MutableLiveData<Boolean> {
//        return isLoggedOut
//    }

//    fun logOutUser(){
//        log("viewmodel logOutUser")
//        AppRepo.loggedOut()
//    }
}