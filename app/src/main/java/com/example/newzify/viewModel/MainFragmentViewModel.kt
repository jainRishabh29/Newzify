package com.example.newzify.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newzify.dataClass.News
import com.example.newzify.repository.NewsRepository

class MainFragmentViewModel constructor(application: Application) : AndroidViewModel(application) {
  val repoInstance = NewsRepository(application)
     var news : LiveData<News>

    init {
        Log.d("flow", "init")
        this.news = repoInstance.getServicesApiCall()
   }

//    fun getNews() : LiveData<News> {
//        Log.d("flow", "ViewModel")
//        return news
//    }

}
