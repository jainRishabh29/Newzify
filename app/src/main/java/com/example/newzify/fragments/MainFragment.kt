package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

class MainFragment : Fragment(), NewsRecyclerAdapter.OnNewsClick {
    lateinit var adapter:NewsRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_main, container, false)
        val progressbar:ProgressBar = view.findViewById(R.id.progressBar)
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!=null){
                    Log.d("batao",news.toString())
                    adapter = NewsRecyclerAdapter(context!!,news.articles,this@MainFragment)
                     val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView1)
                    progressbar.visibility = View.GONE
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

    override fun onItemClick(article: Article, position: Int) {
         val bundle = Bundle()
         bundle.putString("url",article.url)
        val fragment = WebViewFragment()
        Log.d("batao","hii4")
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.containerView,fragment)
            .addToBackStack("Hello").commit()
//        Toast.makeText(context,article.url,Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}