package com.example.newzify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.newzify.R
import com.example.newzify.databinding.FragmentWebViewBinding
import java.util.*

class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWebViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false)
        val view = binding.root
        binding.progressBar.visibility = View.VISIBLE
        val url :String = arguments?.getString("url").toString()
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.webView.loadUrl(url)
        return view
    }
}