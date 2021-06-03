package com.example.newzify.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newzify.dataClass.News
import com.example.newzify.repository.NewsRepository

class MainFragmentViewModel : ViewModel() {

     var news : LiveData<News>

    init {
        Log.d("flow", "init")
        this.news = NewsRepository.getServicesApiCall()
   }

//    fun getNews() : LiveData<News> {
//        Log.d("flow", "ViewModel")
//        return news
//    }


}
