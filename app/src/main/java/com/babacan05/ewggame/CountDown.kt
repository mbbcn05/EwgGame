package com.example.myapplication

import babacan.Game.MyPath

class CountDown {
    var second:Double=60.00

    val frameTime=0.05
    fun updateTime(){
        second-=frameTime
        if(second<0){
            Game.gameOver=true

            refreshTime()

        }

    }
    fun refreshTime(){
        second=60.00

    }
}