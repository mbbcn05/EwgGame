package com.babacan05.ewggame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.properties.Delegates

object GameBitmaps {
    var density by Delegates.notNull<Float>()
    var bitmapsLoaded=false
    var y=MainActivity.y
    var x=MainActivity.x
    lateinit var houseBitmap: Bitmap
    lateinit var electricBitmap: Bitmap
    lateinit var scaledHouse: ImageBitmap
    lateinit var scaledElectric: ImageBitmap
    lateinit var scaledWater: ImageBitmap
    lateinit var waterBitmap: Bitmap
    lateinit var gasBitmap: Bitmap
    lateinit var scaledGas: ImageBitmap
    lateinit var rectButton: RectF

    fun loadBitmaps(resources: Resources){
        println("bitmapler çizildi")
        houseBitmap= BitmapFactory.decodeResource(resources, R.drawable.house)
        electricBitmap= BitmapFactory.decodeResource(resources, R.drawable.electric)
        waterBitmap= BitmapFactory.decodeResource(resources, R.drawable.water)
        scaledHouse= Bitmap.createScaledBitmap(houseBitmap,x/10,y/7,true).asImageBitmap()
        scaledElectric= Bitmap.createScaledBitmap(electricBitmap,x/10,y/7,true).asImageBitmap()
        scaledWater= Bitmap.createScaledBitmap(waterBitmap,x/10,y/7,true).asImageBitmap()
        gasBitmap= BitmapFactory.decodeResource(resources, R.drawable.gas)
        scaledGas= Bitmap.createScaledBitmap(gasBitmap,x/10,y/7,true).asImageBitmap()
        rectButton= RectF(x/10f,y/7f,x/10f,y/7f)

        houseBitmap.recycle()
        electricBitmap.recycle()
        waterBitmap.recycle()
        gasBitmap.recycle()
    }

}