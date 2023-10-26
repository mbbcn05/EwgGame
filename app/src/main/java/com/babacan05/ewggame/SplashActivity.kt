package com.babacan05.ewggame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.w3c.dom.Text

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DURATION = 5000 // 3 saniye (değiştirebilirsiniz)
    lateinit var myText:TextView
    private var mAdView: AdView? = null
    private var mInterstitialAd: InterstitialAd? = null


    //val text=  findViewById<TextView>(R.id.text)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.activity_splash)

       mAdView = findViewById(R.id.adView);
myText=findViewById(R.id.textView)
        if(splashMode) {
            val adRequest = AdRequest.Builder().build();
            mAdView?.loadAd(adRequest)
            splashMode = false
            funForAd()
        }else {
            myText.visibility=View.GONE
            funForAd()
        }

        // Belirli bir süre sonra ana aktiviteye geçiş yapmak için Handler kullanımı



        Handler().postDelayed({
            val intent = Intent(this, GameActivity::class.java) // Ana aktiviteye geçiş yapılacak aktiviteyi belirtin
            startActivity(intent)
            finish()
        }, SPLASH_DURATION.toLong())
    }




    fun funForAd() {
        val adRequest = AdRequest.Builder().build()
//teset "ca-app-pub-3940256099942544/1033173712"
        //      resmi ca-app-pub-1329781431864366/6167970488
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
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

            }
        })

    }
    companion object{

        var splashMode = true

    }
}