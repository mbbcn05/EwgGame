package com.babacan05.ewggame.GameCanvas

import com.example.myapplication.Game.scaledElectric
import com.example.myapplication.Game.scaledGas
import com.example.myapplication.Game.scaledHouse
import com.example.myapplication.Game.scaledWater


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import babacan.Game.MyPath
import com.example.myapplication.Game


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameCanvasScreen() {

val context= LocalContext.current
Game.loadBitmaps(context.resources)
val paths=Game.myPathList.collectAsState().value

    var lastOffset by remember { mutableStateOf(Offset.Zero) }


    Box(modifier = Modifier
        .fillMaxSize()
            ) {


        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {


                detectDragGestures(
                    onDragStart = { offset ->

                        Game.handleSourceSelecting((offset))
                        lastOffset = offset
                    },
                    onDragEnd = { if(Game.creathingPath!=null)Game.handleHouseSelecting(lastOffset) },
                    onDrag = { change, dragAmount ->

                        lastOffset = change.position
                        Game.handleSourceMoving(lastOffset.x, lastOffset.y)

                    })


            }) {


            drawHouses(this)
            drawSources(this)
            drawPaths(this,paths)
        }
    }
}

fun drawSources(drawScope: DrawScope) {
    var x = 1
    Game.sources.forEach {
        val imageBitmap = when (x) {
            2 -> scaledWater
            3 -> scaledGas
            else -> scaledElectric
        }.asImageBitmap()

        drawScope.drawImage(
            alpha = 1f,
            image = imageBitmap,
            topLeft = Offset(it.shape.p1.x, it.shape.p1.y)
        )
        x++
    }
}

fun drawHouses(drawScope: DrawScope) {
    Game.houses.forEach {

        drawScope.drawImage(
            alpha = 1f,
            image = scaledHouse.asImageBitmap(),
            topLeft = Offset(it.rectangle.p1.x, it.rectangle.p1.y)
        )
    }
}




fun drawPaths(drawScope: DrawScope, paths: MutableList<MyPath>){
    paths.toList()
        .forEach{path->path.lines.forEach{line->
            drawScope.drawLine(Color.Black, Offset(line.p1.x,line.p1.y), Offset(line.p2.x,line.p2.y))


        }

        }
    Game.creathingPath?.let{it.lines.toList().forEach{line->drawScope.drawLine(Color.Black,Offset(line.p1.x,line.p1.y),Offset(line.p2.x,line.p2.y))}}


}





//private fun drawCountDown(drawScope:DrawScope) {
  //  Math.round(Game.countDown.second).toString()
  //  drawScope.drawText(textMeasurer = TextMeasurer(),Math.round(Game.countDown.second).toString(), MyGame.x/30f, MyGame.y/20f
 //       ,textPaint)
//}
