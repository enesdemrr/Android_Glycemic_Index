package com.project.glisemikindeks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.project.glisemikindeks.databinding.ActivityMainBinding
import com.project.glisemikindeks.fragments.AddCategoryFragment
import com.project.glisemikindeks.fragments.AddFoodFragment
import com.project.glisemikindeks.fragments.MainFragment


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
               R.id.nav_home -> {
                   changeFragmentMain()
                   bind.drawerLayout.closeDrawer(GravityCompat.START)
               }
               R.id.nav_item ->{
                   changeAddFood()
                   bind.drawerLayout.closeDrawer(GravityCompat.START)
               }
               R.id.nav_cat ->{
                   changeAddCategory()
                   bind.drawerLayout.closeDrawer(GravityCompat.START)
               }
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
        fragment.replace(bind.layoutFrg.id,fragmentChange)
        fragment.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return false
    }
}