package com.babacan05.ewggame


import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.babacan05.ewggame.navigation.GameNavigation


import com.babacan05.ewggame.theme.EwgTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MySplashActivity : ComponentActivity() {


    // private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        MobileAds.initialize(this) { initializationStatus: InitializationStatus ->
                    // SDK başlatıldığında gerçekleştirilecek işlemler
                }

        val adMobInterstitial = AdMobInterstitial(this)


        Ad.showAd={adMobInterstitial.showAdd(this)}
        Ad.loadadAd={adMobInterstitial.loadAd()}

        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
           // finish()
            // Geri tuşuna basıldığında hiçbir işlem yapmak için bu metodu boş bırakabilirsiniz.
                // Eğer bir işlem yapılmasını istemiyorsanız, super.handleOnBackPressed() çağrısını kaldırabilirsiniz.
            }
        }

        // Geri çağrıyı geri çağrı (dispatcher) ile ilişkilendirin.
        onBackPressedDispatcher.addCallback(this, callback)






        setContent {

    EwgTheme{



        GameNavigation(this)


    }


}




        /*** fun funForAd() {
        val adRequest = AdRequest.Builder().build()
        //teset "***"
        //      resmi***
        InterstitialAd.load(this, "*******", adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
        // Reklam yüklenemediğinde kullanıcıya bir hata mesajı göster
        Log.d("TAG", adError.message)
        mInterstitialAd=null
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
        Log.d("TAG", "Reklam yüklendi.")
        mInterstitialAd = interstitialAd
        mInterstitialAd?.show(this@SplashActivity)

        // Reklam yüklendiyse göster
        val intent = Intent(this, GameActivity::class.java) // Ana aktiviteye geçiş yapılacak aktiviteyi belirtin
        startActivity(intent)
        finish()

        }
        })
         */
    }


}

