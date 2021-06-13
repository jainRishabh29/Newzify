package com.example.newzify.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.newzify.R
import com.example.newzify.fragments.AboutFragment
import com.example.newzify.fragments.LicenseFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Newzify)
        setContentView(R.layout.activity_main)

//        val toolbar : Toolbar = findViewById(R.id.tool_bar)
//        setSupportActionBar(toolbar)
//        val top_app_bar = findViewById<MaterialToolbar>(R.id.topAppBar)
//
//        top_app_bar.setOnMenuItemClickListener{menuItem ->
//            when (menuItem.itemId) {
//                R.id.about -> {
////                    val a = supportFragmentManager.beginTransaction()
////                    supportFragmentManager.popBackStack()
////                    val frag = AboutFragment()
////                    a.replace(R.id.containerView, frag).addToBackStack("backStack")
////                    a.commit()
////                Navigation.findNavController(item).navigate(R.id.action_mainFragment_to_aboutFragment2)
//                    true
//                }
//                R.id.license -> {
////                    val a = supportFragmentManager.beginTransaction()
////                    supportFragmentManager.popBackStack()
////                    val frag = LicenseFragment()
////                    a.replace(R.id.containerView, frag).addToBackStack("backStack")
////                    a.commit()
//                    Navigation.createNavigateOnClickListener(R.id.licenseFragment)
//                    true
//                }
//                else -> super.onOptionsItemSelected(menuItem)
//            }
//        }
    }
}