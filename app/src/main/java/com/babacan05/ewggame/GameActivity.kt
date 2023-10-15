package com.babacan05.ewggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class GameActivity : AppCompatActivity() {
    // private lateinit var gameView: MyGame

    override fun onCreate(savedInstanceState: Bundle?) {

        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

      //  setTheme(R.style.AppTheme)
        //    gameView = MyGame(this)
        x = getResources().getDisplayMetrics().widthPixels
        y = getResources().getDisplayMetrics().heightPixels

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_game)
    }
    companion object{
        var x:Int=0
        var y:Int=0

    }
}