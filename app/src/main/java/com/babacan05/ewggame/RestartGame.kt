package com.babacan05.ewggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class RestartGame : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, "YOUR_INTERSTITIAL_AD_UNIT_ID", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Reklam yüklenemediğinde kullanıcıya bir hata mesajı göster
                Log.d("TAG", adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TAG", "Reklam yüklendi.")
                mInterstitialAd = interstitialAd

                // Reklam yüklendiyse göster
                mInterstitialAd?.show(this@RestartGame)
            }
        })

        setContentView(R.layout.activity_restart_game)

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Log.d("TAG", "Reklam tıklandı.")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d("TAG", "Reklam tam ekran içeriği kapatıldı.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                Log.d("TAG", "Reklam izlenme kaydedildi.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("TAG", "Reklam tam ekran içeriği gösterildi.")
            }
        }
    }
}
