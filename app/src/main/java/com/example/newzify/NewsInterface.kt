package com.example.newzify

import com.example.newzify.dataClass.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val base_url: String = "https://newsapi.org/"
const val api_key: String = "f38219279fde4fdf9ddbbedfe2f7cd1e"

interface NewsInterface {

          @GET("v2/top-headlines?apiKey=$api_key")
          fun getHeadlines(@Query("country") country :String , @Query("page") page:Int):Call<News>
}

object NewsService{
    val newsInstance : NewsInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)

    }

}