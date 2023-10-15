package com.babacan05.ewggame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DURATION = 3000 // 3 saniye (değiştirebilirsiniz)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)


        // Belirli bir süre sonra ana aktiviteye geçiş yapmak için Handler kullanımı
        Handler().postDelayed({
            val intent = Intent(this, GameActivity::class.java) // Ana aktiviteye geçiş yapılacak aktiviteyi belirtin
            startActivity(intent)
            finish()
        }, SPLASH_DURATION.toLong())
    }
}