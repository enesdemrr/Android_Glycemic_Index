package com.project.glisemikindeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.repositories.Repo
import com.project.glisemikindeks.service.Service

class SplashScreen : AppCompatActivity() {
    val service = Service(this)
    val db = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
       if(db.isEmptyFood()&& db.isEmptycat()) {
           service.addCategory()
           Repo.son.observe(this,{
               Log.d("splash","splash")
               if(it){
                   Log.d("splash","if")
                   val intent = Intent(this,MainActivity::class.java)
                   startActivity(intent)
                   finish()
               }else{
                   Log.d("splash","else")
               }
           })
       }
       else{
           val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       }
    }
}