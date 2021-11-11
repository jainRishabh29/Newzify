package com.example.newzify.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newzify.R
import com.example.newzify.adapter.NewsRecyclerAdapter
import com.example.newzify.dataClass.Article
import com.example.newzify.databinding.FragmentMainBinding
import com.example.newzify.viewModel.MainFragmentViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class MainFragment : Fragment() {

    lateinit var progressbar: ProgressBar
    lateinit var loadingB: ProgressBar
    lateinit var adapter: NewsRecyclerAdapter
    private val viewModel: MainFragmentViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    var articles: ArrayList<Article> = ArrayList()
    var isScrolling: Boolean = false
    var isLastPage: Boolean = false
    private val TOTAL_PAGES = 2
    private val PAGE_START = 1
    var currentPage = PAGE_START

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // testing the git
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val view = binding.root
        // val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        progressbar = binding.progressBar
        loadingB = binding.loadingB
        recyclerView = binding.recyclerView1
//        loadingB = view.findViewById(R.id.loadingB)
//        recyclerView = view.findViewById(R.id.recyclerView1)
        // val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        //  val viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = NewsRecyclerAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val scrollListenerPer = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Log.d("pagi", "onscroll")
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                Log.d("pagi", "$visibleItemCount + $totalItemCount + $firstVisibleItemPosition")

                if ( isScrolling && visibleItemCount + firstVisibleItemPosition >=
                    totalItemCount && firstVisibleItemPosition >= 0 && currentPage < TOTAL_PAGES + 1
                ) {
                    isScrolling = false
                    loadingB.visibility = View.VISIBLE
                    loadData(currentPage)
                }
            }
        }
        loadData(currentPage)
        recyclerView.addOnScrollListener(scrollListenerPer)

        return view
    }

    fun loadData(pageNumber: Int) {
//        if (pageNumber>1){
//            Log.d("progress","inIf")
//            loadingB.visibility = View.VISIBLE
//        }else{
//            Log.d("progress","outIf")
//            loadingB.visibility = View.GONE
//        }
        currentPage += 1
        viewModel.getNews(pageNumber).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d("flow", "fragment")
                Log.d("pagi", "VM")
                articles = it.articles as ArrayList<Article>
                adapter.setNews(articles)
                Log.d("progress", "fragment news = ${it.toString()}")
                progressbar.visibility = View.GONE
                //               loadingB.visibility = View.GONE
            }
        })
        Log.d("progress", "endFun")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                findNavController().navigate(R.id.aboutFragment)
                true
            }
            R.id.license -> {
                startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
                true
            }
            R.id.profile -> {
                findNavController().navigate(R.id.profileFragment)
                true
            }
            R.id.signOut -> {
                findNavController().navigate(R.id.action_mainFragment_to_signOutFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}