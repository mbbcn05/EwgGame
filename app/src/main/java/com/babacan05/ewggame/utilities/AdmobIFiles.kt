package com.babacan05.ewggame.utilities


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView

import com.google.android.gms.ads.*
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


var showAd: () -> Unit = {  }
@Composable
fun AdMobBanner() {
    val currentWidth = LocalConfiguration.current.screenWidthDp
    AndroidView(
        factory = {
            AdView(it).apply {
                setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, currentWidth))
                adUnitId = "ca-app-pub-1329781431864366/7299829579"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}


@Composable
fun AdBanner() {
    val adView = AdView(LocalContext.current)
    adView.setAdSize(AdSize.BANNER)
    adView.adUnitId = "ca-app-pub-1329781431864366/7299829579"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { adView }
    ) { view ->
        val adRequest = AdRequest.Builder().build()
        view.loadAd(adRequest)
    }
}


fun showInterstitialAd(activity: Activity) {
    InterstitialAd.load(
        activity,
        "ca-app-pub-1329781431864366/6167970488",
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
          showAd ={interstitialAd.show(activity)}

            }
        }
    )
}

