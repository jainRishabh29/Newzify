package com.example.newzify.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newzify.retrofit.NewsService
import com.example.newzify.dataClass.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository constructor(val application: Application) {
    val news_ = MutableLiveData<News>()

    fun getServicesApiCall(): MutableLiveData<News> {
        val news = NewsService.getClient(application).getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.d("batao", news.toString())
                    val articles = news.articles
                    news_.value = News(articles)
                    Log.d("flow", "repository")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("batao", "Error in fetching", t)
            }
        })
        return news_
    }
}