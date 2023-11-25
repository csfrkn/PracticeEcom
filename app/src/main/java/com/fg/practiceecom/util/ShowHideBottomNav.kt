package com.fg.practiceecom.util

import android.view.View
import androidx.fragment.app.Fragment
import com.fg.practiceecom.R
import com.fg.practiceecom.presentation.activities.ShoppingActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(
        R.id.bottomNavigation
    )
    bottomNavView.visibility = View.GONE
}
fun Fragment.showBottomNavigationView(){
    val bottomNavView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(
        R.id.bottomNavigation
    )
    bottomNavView.visibility = View.VISIBLE
}