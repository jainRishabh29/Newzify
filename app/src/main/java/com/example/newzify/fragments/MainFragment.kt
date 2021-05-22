package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newzify.NewsRecyclerAdapter
import com.example.newzify.NewsService
import com.example.newzify.R
import com.example.newzify.dataClass.Article
import com.example.newzify.dataClass.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    lateinit var adapter:NewsRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_main, container, false)
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!=null){
                    Log.d("batao",news.toString())
                    adapter = NewsRecyclerAdapter(context!!,news.articles)
                     val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView1)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager =  LinearLayoutManager(context)

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("batao", "Error in fetching", t)
            }
        })
        return view
    }

}