package com.example.newzify.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.newzify.dataClass.Article
import com.example.newzify.retrofit.NewsService
import com.example.newzify.dataClass.News
import retrofit2.Call

class NewsRepository constructor(val application: Application) {
 //   val news_ = MutableLiveData<News>()
 //   lateinit var  articles : List<Article>

    fun getServicesApiCall(pageNum: Int): Call<News> {
        return NewsService.getClient(application).getHeadlines("in", pageNum)
    }
}