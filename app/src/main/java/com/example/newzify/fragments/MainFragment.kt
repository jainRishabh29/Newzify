package com.example.newzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newzify.viewModel.MainFragmentViewModel
import com.example.newzify.adapter.NewsRecyclerAdapter
import com.example.newzify.R
import com.example.newzify.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var adapter: NewsRecyclerAdapter
    private lateinit var viewModel: MainFragmentViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.news.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d("flow", "fragment")
                adapter = NewsRecyclerAdapter(requireContext(), it.articles)
                binding.progressBar.visibility = View.GONE
                binding.recyclerView1.adapter = adapter
                binding.recyclerView1.layoutManager = LinearLayoutManager(context)
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
