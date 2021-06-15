package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newzify.viewModel.MainFragmentViewModel
import com.example.newzify.adapter.NewsRecyclerAdapter
import com.example.newzify.R
import com.example.newzify.dataClass.Article

class MainFragment : Fragment(), NewsRecyclerAdapter.OnNewsClick {
    lateinit var adapter: NewsRecyclerAdapter
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // testing the git
        val view:View = inflater.inflate(R.layout.fragment_main, container, false)
        val progressbar:ProgressBar = view.findViewById(R.id.progressBar)

        val viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.news.observe(viewLifecycleOwner , Observer {
                      if (it!=null){
                          Log.d("flow", "fragment")
                          adapter = NewsRecyclerAdapter(requireContext(),this@MainFragment)
                          adapter.setNews(it.articles)
                     val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView1)
                    progressbar.visibility = View.GONE
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager =  LinearLayoutManager(context)
                      }
        })


        return view
    }

    override fun onItemClick(article: Article, position: Int) {
         val bundle = Bundle()
         bundle.putString("url",article.url)
        val fragment = WebViewFragment()
   //     Log.d("batao","hii4")
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