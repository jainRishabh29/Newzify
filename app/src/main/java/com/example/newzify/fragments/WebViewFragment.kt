package com.example.newzify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newzify.R
import com.example.newzify.databinding.FragmentWebViewBinding
import com.example.newzify.databinding.SignUpFragmentBinding

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val view = binding.root
        val url :String = arguments?.getString("url").toString()
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)
        return view
    }

}