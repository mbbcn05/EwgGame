package com.babacan05.ewggame


import android.os.Build
import android.os.Bundle
import android.view.WindowManager

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

import com.babacan05.ewggame.navigation.GameNavigation


import com.babacan05.ewggame.theme.EwgTheme
import com.example.myapplication.Game
import com.example.myapplication.MyGame
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus


class MainActivity : ComponentActivity() {


    // private var mInterstitialAd: InterstitialAd? = null
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        x = resources.displayMetrics.widthPixels
       y = resources.displayMetrics.heightPixels



        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)













        setContent {

    EwgTheme{



        GameNavigation(this)


    }


}




    }
companion object{
    var x=0
    var y=0
}

}

