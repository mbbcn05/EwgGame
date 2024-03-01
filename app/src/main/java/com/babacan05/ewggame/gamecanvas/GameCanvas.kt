package com.babacan05.ewggame.gamecanvas

import BlinkingRectangleEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import babacan.Game.GameSource
import babacan.Game.MyLine
import babacan.Game.MyPath
import com.babacan05.ewggame.GameBitmaps
import com.babacan05.ewggame.R
import com.babacan05.ewggame.showAd
import com.example.myapplication.Game
import com.example.myapplication.GameHouse
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameCanvasScreen(

    game: Game,
    navigate: () -> Unit
) {
    var gameState =remember{
        mutableStateOf(GameState(lightingSources =game.sources))
    }
    var succes = remember{
        mutableStateOf(false)
    }
LaunchedEffect(key1 = succes.value){
    if (succes.value){
    delay(500)
    succes.value=false}
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
    val number=remember {
        mutableStateOf(60)
    }




    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.3f))

            ) {


        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {


                detectDragGestures(
                    onDragStart = { offset ->
                        if (!gameOver) {
                            game.handleSourceSelecting((offset), gameState)
                            lastOffset = offset
                        }


                    },
                    onDragEnd = {
                        if (!gameOver && game.creathingPath != null) game.handleHouseSelecting(
                            lastOffset, paths, creathingLines, gameState, succes
                        )
                    },
                    onDrag = { change, dragAmount ->
                        if (!gameOver) {
                            change.consume()
                            lastOffset = change.position
                            game.handleSourceMoving(lastOffset.x, lastOffset.y, creathingLines)

                        }


                        // lines.add(Line(p1=change.previousPosition,p2 = change.position))

                    })


            }) {


            drawPaths(this,paths,game)
            drawCreatgingLines(this,creathingLines,game)
                drawHouses(this,game)



                drawSources(this,game)
        }
        AnimatedVisibility(visible = gameOver,modifier=Modifier.align(Alignment.Center)) {
            Text(fontWeight = FontWeight.ExtraBold,color= Color.Red,text = "GAME OVER", modifier = Modifier, fontSize = 100.sp)
        }
        AnimatedVisibility(visible = succes.value, modifier = Modifier
            .align(Alignment.Center)
            .size(250.dp)) {
            Icon(painter = painterResource(id = R.drawable.checktrue), contentDescription ="")
            //succes.value=false
        }

        if(!gameOver) {
          gameState.value.lightingHouses.forEach {
                BlinkingRectangleEffect(
                    width = (it.rectangle.width / GameBitmaps.density).dp, // Dikdörtgenin genişliği
                    height = (it.rectangle.height / GameBitmaps.density).dp, // Dikdörtgenin yüksekliği
                    offset = it.rectangle.rect.topLeft, // Dikdörtgenin y konumu
                    strokeWidth = 4f, // Kenarlık kalınlığı
                    borderColor = gameState.value.colorHouse, // Kenarlık rengi
                    radius = 20f // Işık efektinin yarıçapı
                )
            }
        }
        if(!gameOver) {
            gameState.value.lightingSources.forEach {
                BlinkingRectangleEffect(
                    width = (it.shape.width / GameBitmaps.density).dp, // Dikdörtgenin genişliği
                    height = (it.shape.height / GameBitmaps.density).dp, // Dikdörtgenin yüksekliği
                    offset = it.shape.rect.topLeft, // Dikdörtgenin y konumu
                    strokeWidth = 4f, // Kenarlık kalınlığı
                    borderColor = gameState.value.colorSource, // Kenarlık rengi
                    radius = 20f // Işık efektinin yarıçapı
                )

            }
        }

    }
    LaunchedEffect(number.value,gameOver){
       if(!gameOver) {delay(1000)
        number.value=number.value-1
        if(number.value<0){
            gameOver=true
        }
    }}
    Text(text = number.value.toString())
    Button(onClick ={
        if(number.value<50) showAd()

        navigate()



          },modifier=Modifier.offset(2.dp,320.dp)){
        Text(text = "Restart")
    }

}

fun drawSources(drawScope: DrawScope, game: Game) {

    var x = 1
    game.sources.forEach {
        val imageBitmap = when (x) {
            2 -> GameBitmaps.scaledWater
            3 ->GameBitmaps. scaledGas
            else -> GameBitmaps.scaledElectric
        }

        drawScope.drawImage(
            alpha = 1f,
            image = imageBitmap,
            topLeft = Offset(it.shape.p1.x, it.shape.p1.y)
        )
        x++
    }
}

fun drawHouses(drawScope: DrawScope, game: Game) {
    game.houses.forEach {

        drawScope.drawImage(
            alpha = 1f,
            image = GameBitmaps.scaledHouse,
            topLeft = Offset(it.rectangle.p1.x, it.rectangle.p1.y)
        )

    }
}


fun drawCreatgingLines(
    drawScope: DrawScope,

    creathingLines: SnapshotStateList<MyLine>,
    game: Game
){



    creathingLines.toList().forEach{line->drawScope.drawLine(Color.Green,Offset(line.p1.x,line.p1.y),
        Offset(line.p2.x,line.p2.y), pathEffect = PathEffect.cornerPathEffect(5f), cap = StrokeCap.Round, strokeWidth =15f)}


}

fun drawPaths(
    drawScope: DrawScope,
    paths: SnapshotStateList<MyPath>,
    game: Game
){


    paths.toList()
        .forEach { path ->
            path.lines.forEach { line ->
                drawScope.drawLine(

                    Color.Black,
                    Offset(line.p1.x, line.p1.y),
                    Offset(line.p2.x, line.p2.y),
                    alpha = 1f,
                    cap = StrokeCap.Square, strokeWidth =3f
                )


            }

        }}
data class GameState(val lightingHouses:List<GameHouse> = emptyList(), val lightingSources:List<GameSource> =emptyList(),val colorSource:Color= Color.White ,val colorHouse:Color= Color.White)














