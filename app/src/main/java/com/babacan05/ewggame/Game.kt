package com.example.myapplication

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.util.Log
import babacan.Game.GameSource
import babacan.Game.MyPath
import babacan.Game.MyPoint
import babacan.Game.MyRectangle
import babacan.Game.SourceType
import com.babacan05.ewggame.MainActivity
import com.babacan05.ewggame.R

object Game {

    var y=MainActivity.y
    var x=MainActivity.x
    lateinit var countDown: CountDown
    lateinit var houseBitmap:Bitmap
    lateinit var electricBitmap: Bitmap
    lateinit var scaledHouse:Bitmap
    lateinit var scaledElectric:Bitmap
    lateinit var scaledWater:Bitmap
    lateinit var waterBitmap: Bitmap
    lateinit var gasBitmap: Bitmap
    lateinit var scaledGas:Bitmap
    lateinit var rectButton:RectF

    var adsOn = false
    fun loadBitmaps(resources: Resources){
        countDown = CountDown()
        houseBitmap= BitmapFactory.decodeResource(resources, R.drawable.house)
        electricBitmap= BitmapFactory.decodeResource(resources, R.drawable.electric)
        waterBitmap= BitmapFactory.decodeResource(resources, R.drawable.water)
        scaledHouse= Bitmap.createScaledBitmap(houseBitmap,x/10,y/7,true)
        scaledElectric= Bitmap.createScaledBitmap(electricBitmap,x/10,y/7,true)
        scaledWater= Bitmap.createScaledBitmap(waterBitmap,x/10,y/7,true)
        gasBitmap= BitmapFactory.decodeResource(resources, R.drawable.gas)
        scaledGas= Bitmap.createScaledBitmap(gasBitmap,x/10,y/7,true)
        rectButton= RectF(x/10f,y/7f,x/10f,y/7f)

        houseBitmap.recycle()
        electricBitmap.recycle()
        waterBitmap.recycle()
        gasBitmap.recycle()
    }

    var creathingPath: MyPath? = null

    val myPathList: MutableList<MyPath> = mutableListOf()
    var gameOver = false

    val houses: List<GameHouse> = listOf(
        GameHouse(MyRectangle(MyPoint(x / 8f, y / 5f))),
        GameHouse(MyRectangle(MyPoint(x / 2f - 0.5f * x / 8f, y / 5f))),
        GameHouse(MyRectangle(MyPoint(x - 2 * x / 8f, y / 5f)))
    )

    val sources: List<GameSource> = listOf(
        GameSource(
            MyRectangle(MyPoint(x / 8f, y - 3 * y / 7f)),
            SourceType.ELECTRIC
        ),
        GameSource(
            MyRectangle(
                MyPoint(
                    x / 2f - 0.5f * x / 8f,
                    y - 3 * y / 7f
                )
            ), SourceType.GAS
        ),
        GameSource(
            MyRectangle(MyPoint(x - 2 * x / 8f, y - 3 * y / 7f)),
            SourceType.WATER
        )
    )

    fun handleSourceSelecting(point: MyPoint) {
        if (creathingPath == null) {
            sources.forEach {
                if (it.shape.isPointInRectangle(point)) {
                    creathingPath = MyPath(it)
                    Log.i("Source selecting", "source seçildi")
                }
            }
        }
    }

    fun handleSourceMoving(x: Float, y: Float) {
        creathingPath?.let {
            it.addLines(x, y)
        }
    }

    fun handleHouseSelecting(point: MyPoint) {
        var houseSelecting=false
        houses.forEach {
            if (it.rectangle.isPointInRectangle(point) &&! creathingPath!!.intersectsWithPaths(myPathList)&&!creathingPath!!.isIntersectOtherHouses(it) &&!creathingPath!!.isIntersectOtherSources()&&it.acceptIfNotContained(creathingPath!!.source)) {
                houseSelecting=true
                creathingPath!!.apply {
                    myPathList.add(clipLinesInRectangle(it.rectangle,this.source.shape))
                    creathingPath = null
                    //countDown.refreshTime()
                    return
                }
            }
        }
        if(!houseSelecting){
            creathingPath=null
        }
    }
}
