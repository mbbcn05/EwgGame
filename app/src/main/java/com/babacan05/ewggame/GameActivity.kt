package com.babacan05.ewggame


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.WindowManager
import androidx.annotation.RequiresApi

import com.example.myapplication.MyGame
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus

class GameActivity : AppCompatActivity() {
    //private lateinit var gameView: MyGame

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        //İntersityAd.activityForAds=this
        //  setTheme(R.style.AppTheme)


       MyGame.x = getResources().getDisplayMetrics().widthPixels
       MyGame.y = getResources().getDisplayMetrics().heightPixels


        //gameView = MyGame(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_game)
    }

    override fun onBackPressed() {

    }

}

