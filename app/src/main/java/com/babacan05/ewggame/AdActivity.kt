package com.babacan05.ewggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adMobInterstitial = AdMobInterstitial(this)

        adMobInterstitial.loadAd()
         Ad.showAd={adMobInterstitial.showAdd(this)}

    }
}