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
        NewsRepository.getServicesApiCall()
        this.news = NewsRepository.news_
   }

//    fun getNews() : LiveData<News> {
//        Log.d("flow", "ViewModel")
//        return news
//    }


}
