package com.babacan05.ewggame.GameCanvas

import com.example.myapplication.Game.scaledElectric
import com.example.myapplication.Game.scaledGas
import com.example.myapplication.Game.scaledHouse
import com.example.myapplication.Game.scaledWater


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import babacan.Game.MyLine
import babacan.Game.MyPath
import com.babacan05.ewggame.AnimatedStarsBackground
import com.example.myapplication.Game
import com.example.myapplication.Game.loadBitmaps
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameCanvasScreen() {

val context= LocalContext.current
var loadingBitmaps by remember {
    mutableStateOf(true)
}
    var lastOffset by remember { mutableStateOf(Offset.Zero) }

    var gameOver by remember {
        mutableStateOf(false)}
    val paths = remember {
        mutableStateListOf<MyPath>()
    }
    val creathingLines=remember {
        mutableStateListOf<MyLine>()
    }

    LaunchedEffect(key1 =gameOver){
            if(gameOver){
                Game.houses.forEach{
                    it.cleanAllSources()
                }
                Game.creathingPath=null
                paths.clear()
                creathingLines.clear()

            }
        }

    if(loadingBitmaps) {
        loadBitmaps(context.resources)
        loadingBitmaps=false
    }
    val number=remember {
        mutableStateOf(10)
    }


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
                    onDragEnd = {
                        if (Game.creathingPath != null) Game.handleHouseSelecting(
                            lastOffset, paths, creathingLines
                        )
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        lastOffset = change.position
                        Game.handleSourceMoving(lastOffset.x, lastOffset.y, creathingLines)

                        // lines.add(Line(p1=change.previousPosition,p2 = change.position))

                    })


            }) {



                drawHouses(this)



                drawSources(this)


            drawPaths(this,paths)
drawCreatgingLines(this,creathingLines)
        }

    }
    LaunchedEffect(number.value){
        delay(1000)
        number.value=number.value-1
        if(number.value==0){
            gameOver=true
        }
    }
    Text(text = number.value.toString())
    Button(onClick ={number.value=100},modifier=Modifier.offset(2.dp,320.dp)){
        Text(text = "Restart")
    }
}

fun drawSources(drawScope: DrawScope) {
    print("Sources çizildi")
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


fun drawCreatgingLines(
    drawScope: DrawScope,

    creathingLines: SnapshotStateList<MyLine>
){
    println("path çizme çağrıldı")


    creathingLines.toList().forEach{line->drawScope.drawLine(Color.Black,Offset(line.p1.x,line.p1.y),Offset(line.p2.x,line.p2.y))}


}

fun drawPaths(
    drawScope: DrawScope,
    paths: SnapshotStateList<MyPath>
){
    println("path çizme çağrıldı")

    paths.toList()
        .forEach{path->path.lines.forEach{line->
            drawScope.drawLine(Color.Black, Offset(line.p1.x,line.p1.y), Offset(line.p2.x,line.p2.y),alpha = 1f)



        }

        }



}





//private fun drawCountDown(drawScope:DrawScope) {
  //  Math.round(Game.countDown.second).toString()
  //  drawScope.drawText(textMeasurer = TextMeasurer(),Math.round(Game.countDown.second).toString(), MyGame.x/30f, MyGame.y/20f
 //       ,textPaint)
//}
