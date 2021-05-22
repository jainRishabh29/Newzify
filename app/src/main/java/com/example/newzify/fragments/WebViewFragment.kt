package com.example.newzify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.newzify.R

class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_web_view, container, false)
        val url :String = arguments?.getString("url").toString()
        view.findViewById<WebView>(R.id.webView).webViewClient = WebViewClient()
        view.findViewById<WebView>(R.id.webView).loadUrl(url)
        return view
    }

}