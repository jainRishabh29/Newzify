package com.example.newzify.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newzify.dataClass.Article
import com.example.newzify.dataClass.News
import com.example.newzify.repository.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel constructor(application: Application) : AndroidViewModel(application) {
  val repoInstance = NewsRepository(application)
    lateinit var news : Call<News>
    val news_ = MutableLiveData<News>()
    val isLoading = MutableLiveData<Boolean>()
     var  articles : MutableList<Article> = mutableListOf()

    init {
        Log.d("flow", "init")
   }

    fun getNews( pageNum: Int ) : MutableLiveData<News>{
        this.news = repoInstance.getServicesApiCall(pageNum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    articles
                    Log.d("progress", "viewModel news = ${news.toString()}")
                    Log.d("progress", news.articles.size.toString())
                    articles.addAll(news.articles)
                    val results = news.totalResults
                    news_.value = News(results ,articles)
                    Log.d("flow", "repository")
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("batao", "Error in fetching", t)
            }
        })
        return news_
    }

}
