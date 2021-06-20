package com.example.newzify.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.newzify.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Newzify)
        setContentView(R.layout.activity_main)
//        val toolbar : Toolbar = findViewById(R.id.tool_bar)
//        setSupportActionBar(toolbar)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment
//        navController = navHostFragment.navController
//        auth = FirebaseAuth.getInstance()

//        val top_app_bar = findViewById<MaterialToolbar>(R.id.topAppBar)
//        top_app_bar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.about -> {
//                    navController.navigate(R.id.aboutFragment)
////                    val a = supportFragmentManager.beginTransaction()
////                    supportFragmentManager.popBackStack()
////                    val frag = AboutFragment()
////                    a.replace(R.id.containerView, frag).addToBackStack("backStack")
////                    a.commit()
////                Navigation.findNavController(item).navigate(R.id.action_mainFragment_to_aboutFragment2)
//                    true
//                }
//                R.id.license -> {
//                    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
//                    true
//                }
//                R.id.profile -> {
//                    navController.navigate(R.id.profileFragment)
//                    true
//                }
//                else -> super.onOptionsItemSelected(menuItem)
//            }
//        }
    }
}