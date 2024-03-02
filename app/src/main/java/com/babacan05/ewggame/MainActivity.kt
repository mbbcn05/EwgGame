package com.babacan05.ewggame


import android.os.Build
import android.os.Bundle
import android.view.WindowManager

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi

import com.babacan05.ewggame.presentation.GameNavigation


import com.babacan05.ewggame.theme.EwgTheme
import com.babacan05.ewggame.utilities.MyMediaPlayer
import com.babacan05.ewggame.utilities.showInterstitialAd


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



        val showInterstitialAdCallback: () -> Unit = {
            showInterstitialAd(this)
        }

val mediaPlayer= MyMediaPlayer(this)


        setContent {

    EwgTheme{



        GameNavigation(mediaPlayer = mediaPlayer,
            showInterstitialAdCallback = showInterstitialAdCallback
        )


    }


}




    }
companion object{
    var x=0
    var y=0
}

}

