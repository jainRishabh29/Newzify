package com.example.newzify.retrofit

import android.content.Context
import com.example.newzify.dataClass.News
import com.example.newzify.util.InternetConnectivity
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val base_url: String = "https://newsapi.org/"
const val api_key: String = "f38219279fde4fdf9ddbbedfe2f7cd1e"

interface NewsInterface {

          @GET("v2/top-headlines?apiKey=$api_key")
          fun getHeadlines(@Query("country") country :String , @Query("page") page:Int): Call<News>

}

object NewsService{

    private const val cacheSize : Long = 10 * 1024 * 1024
    fun getClient(context: Context): NewsInterface {

        val cache = Cache(context.cacheDir, cacheSize)

        val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            val maxAge = 60
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }

        val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = Interceptor { chain ->
            var request: Request = chain.request()
            val maxStale = 60 * 60 * 24 * 30
            if (!InternetConnectivity.isNetworkAvailable(context)) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
            .build()

        val retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(base_url)
                .client(okHttpClient)
                .build()
        }
        val api: NewsInterface by lazy {
            retrofit.create(NewsInterface::class.java)
        }
        return api

    }


//    val newsInstance : NewsInterface
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(base_url)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//        newsInstance = retrofit.create(NewsInterface::class.java)
//
//    }

}