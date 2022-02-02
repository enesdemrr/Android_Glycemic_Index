package com.project.glisemikindeks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView

import com.project.glisemikindeks.adapter.DBAdapter
import com.project.glisemikindeks.adapter.DBCatAdapter
import com.project.glisemikindeks.databinding.ActivityMainBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.fragments.AddCategoryFragment
import com.project.glisemikindeks.fragments.AddFoodFragment
import com.project.glisemikindeks.fragments.MainFragment
import com.project.glisemikindeks.model.Food
import com.project.glisemikindeks.service.Service

class MainActivity : AppCompatActivity() {
    private lateinit var toogle : ActionBarDrawerToggle
    private lateinit var bind : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val drawerLayout : DrawerLayout  = bind.drawerLayout
         changeFragmentMain()
       toogle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
       drawerLayout.addDrawerListener(toogle)
       toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind.navView?.setNavigationItemSelectedListener {
            it.isChecked =true
            when(it.itemId){
               R.id.nav_home -> changeFragmentMain()
               R.id.nav_item -> changeAddFood()
               R.id.nav_cat -> changeAddCategory()
            }
            true
        }
        }

    private fun changeAddCategory() {
        val fragment = AddCategoryFragment()
        changeFragment(fragment)
    }

    private fun changeAddFood() {
        val fragment = AddFoodFragment()
        changeFragment(fragment)
    }

    private fun changeFragmentMain() {
        val fragment = MainFragment()
        changeFragment(fragment)
    }
    private fun changeFragment(fragmentChange: Fragment){
        val fragment  = supportFragmentManager.beginTransaction()
        fragment.replace(bind.layourFrg.id,fragmentChange)
        fragment.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return false
    }
}