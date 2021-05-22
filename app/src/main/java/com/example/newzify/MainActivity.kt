package com.example.newzify

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.newzify.fragments.AboutFragment
import com.example.newzify.fragments.LicenseFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator = menuInflater
        inflator.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val a = supportFragmentManager.beginTransaction()
                supportFragmentManager.popBackStack()
                val frag = AboutFragment()
                a.replace(R.id.containerView, frag).addToBackStack("backStack")
                a.commit()
//                Navigation.findNavController(item).navigate(R.id.action_mainFragment_to_aboutFragment2)
                true
            }
            R.id.license -> {
                val a = supportFragmentManager.beginTransaction()
                supportFragmentManager.popBackStack()
                val frag = LicenseFragment()
                a.replace(R.id.containerView, frag).addToBackStack("backStack")
                a.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}