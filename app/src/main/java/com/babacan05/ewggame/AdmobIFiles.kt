package com.babacan05.ewggame


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView

import com.google.android.gms.ads.*
import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback



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
    adView.adUnitId = "ca-app-pub-1329781431864366/7299829579" // AdMob'dan aldığınız Banner Ad Unit ID

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { adView }
    ) { view ->
        val adRequest = AdRequest.Builder().build()
        view.loadAd(adRequest)
    }
}





class AdMobInterstitial(private val activity: Activity) {
    var mInterstitialAd: InterstitialAd? = null
    private val context = activity.applicationContext
    fun loadAd() {
        val adUnitId = context.getString(R.string.interstitial_ad_unit_id)
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                mInterstitialAd = null
                Ad.institialad=false
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                mInterstitialAd = ad
                Ad.institialad=true
                //Toast.makeText(context, "add loaded", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun showAdd(context:ComponentActivity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    mInterstitialAd = null
                    loadAd()
                    openGameActivity(context)
                    //Toast.makeText(context, "dismissed", Toast.LENGTH_SHORT).show()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    mInterstitialAd = null
                    openGameActivity(context)
                    //Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    //openGameActivity(context)

                    // Toast.makeText(context, "showed", Toast.LENGTH_SHORT).show()
                }
            }
            mInterstitialAd?.show(activity)
            loadAd()
        } else {
           // loadAd()
        }
    }
}
fun openGameActivity(context: ComponentActivity){
    val intent = Intent(context, GameActivity::class.java) // Ana aktiviteye geçiş yapılacak aktiviteyi belirtin
    context.startActivity(intent)

}
