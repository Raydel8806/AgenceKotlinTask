package com.campingstudio.agencekotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.campingstudio.agencekotlin.core.AuthUserHelper
import com.campingstudio.agencekotlin.data.model.AuthUser
import com.campingstudio.agencekotlin.databinding.ActivityAgenceBinding
import com.campingstudio.agencekotlin.ext.toastLong
import com.campingstudio.agencekotlin.ext.toastShort
import com.campingstudio.agencekotlin.ui.view.login.LoginActivity
import com.google.android.material.navigation.NavigationView

class AgenceActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val TAG = "AgenceActivity"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var navController : NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var binding: ActivityAgenceBinding
    private lateinit var authUserHelper: AuthUserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authUserHelper =  AuthUserHelper(this@AgenceActivity)
        if(authUserHelper.user==null) {
            toastShort("authUserHelper.user==null")
            logout()
        }


        toolbar = binding.appBarAgence.toolbarAgenceMain
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        toolbar.setNavigationIcon(R.drawable.ic_out)

        supportActionBar?.setHomeButtonEnabled(true)
        navView = binding.navView
        navView.setNavigationItemSelectedListener(this)
        navController = findNavController(R.id.nav_host_fragment_content_agence)
    }

    private fun logout() {
        try {
            Log.d(TAG, "\"Regresa pronto ${authUserHelper.user!!.userName}.\"")
            toastLong( "Regresa pronto ${authUserHelper.user!!.userName}.")
            authUserHelper.logout()
            val intent = Intent(this.applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } catch (e: Exception) {
            e.printStackTrace()
            val intent = Intent(this.applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    //To deny return to the login activity


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.draw_menu_item_out ->{
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.draw_menu_item_home ->navController.navigate(R.id.nav_home)
            R.id.draw_menu_item_profile ->navController.navigate(R.id.nav_profile)
            R.id.draw_menu_my_products -> navController.navigate(R.id.nav_my_products)
            R.id.draw_menu_item_settings -> navController.navigate(R.id.nav_settings)
            R.id.draw_menu_item_out ->{
                logout()
            }
            R.id.draw_menu_item_exit ->{
               finishAffinity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_agence)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}