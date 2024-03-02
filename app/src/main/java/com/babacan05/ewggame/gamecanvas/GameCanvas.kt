package com.babacan05.ewggame.gamecanvas

import BlinkingRectangleEffect
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import babacan.Game.GameSource
import com.babacan05.ewggame.gamefiles.MyLine
import com.babacan05.ewggame.gamefiles.MyPath
import com.babacan05.ewggame.gamefiles.GameBitmaps
import com.babacan05.ewggame.R
import com.babacan05.ewggame.showAd
import com.babacan05.ewggame.gamefiles.Game
import com.babacan05.ewggame.gamefiles.GameHouse
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameCanvasScreen(

    game: Game,
    navigate: () -> Unit
) {
  val context= LocalContext.current
    val successMusic=MediaPlayer.create(context,R.raw.success)

    
    val gameState =remember{
        mutableStateOf(GameState(lightingSources =game.sources))
    }
    val succes = remember{
        mutableStateOf(false)
    }
    val intersect = remember{
        mutableStateOf(false)
    }
    val beSame = remember{
        mutableStateOf(false)
    }
LaunchedEffect(key1 = intersect.value){
    if (intersect.value){
    delay(750)
        intersect.value=false}
}
    LaunchedEffect(key1 = beSame.value){
        if (beSame.value){
            delay(750)
            beSame.value=false}
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
        mutableIntStateOf(60)
    }




    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .background(Color.Black.copy(alpha = 0.1f))

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
                            lastOffset, paths, creathingLines, gameState, succes, intersect, beSame


                        ) {
                        }
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


            drawPaths(this, paths)
            drawCreatgingLines(this, creathingLines)
                drawHouses(this,game)



                drawSources(this,game)
        }
        AnimatedVisibility(visible = gameOver,modifier= Modifier
            .align(Alignment.Center)
            .background(
                Color.LightGray.copy(alpha = 0.4f),
                RoundedCornerShape(20.dp)
            )) {
            Text(fontWeight = FontWeight.ExtraBold,color= Color.Red,text = "GAME OVER", fontSize = 100.sp)
        }
        AnimatedVisibility(visible = beSame.value,modifier= Modifier
            .align(Alignment.Center)
            .background(
                Color.LightGray.copy(alpha = 0.4f),
                RoundedCornerShape(20.dp)
            )) {
            Text(fontWeight = FontWeight.ExtraBold,color= Color.Red,text = "DON'T BE SAME!", fontSize = 80.sp)
        }
        AnimatedVisibility(visible = intersect.value,modifier= Modifier
            .align(Alignment.Center)
            .background(
                Color.LightGray.copy(alpha = 0.4f),
                RoundedCornerShape(20.dp)
            )) {
            Text(fontWeight = FontWeight.ExtraBold,color= Color.Red,text = "DON'T İNTERSECT!", modifier = Modifier, fontSize = 80.sp)
        }
        AnimatedVisibility(visible = succes.value, modifier = Modifier
            .align(Alignment.Center)
            .size(250.dp)) {
            Icon(painter = painterResource(id = R.drawable.checktrue), contentDescription ="")

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


        Text(fontWeight = FontWeight.Bold,fontSize = 25.sp, text = number.value.toString(), modifier = Modifier
            .align(Alignment.TopStart)
            .padding(2.dp)
            .background(Color.Red.copy(alpha = 0.2f), RoundedCornerShape(20.dp)))

       OutlinedButton(onClick ={
            if(number.value<50) showAd()

            navigate()



        },modifier=Modifier.align(Alignment.BottomStart)){
            Text(text = "Restart")
        }

    }
    LaunchedEffect(number.intValue,gameOver){
       if(!gameOver) {delay(1000)
        number.intValue=number.intValue-1
        if(number.intValue<1){
            gameOver=true
        }
    }}



}

fun drawSources(drawScope: DrawScope, game: Game) {

    var x = 1
    game.sources.forEach {
        val imageBitmap = when (x) {
            2 -> GameBitmaps.scaledWater
            3 -> GameBitmaps. scaledGas
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

    creathingLines: SnapshotStateList<MyLine>
){



    creathingLines.toList().forEach{line->drawScope.drawLine(Color.Green,Offset(line.p1.x,line.p1.y),
        Offset(line.p2.x,line.p2.y), pathEffect = PathEffect.cornerPathEffect(5f), cap = StrokeCap.Round, strokeWidth =10f)}


}

fun drawPaths(
    drawScope: DrawScope,
    paths: SnapshotStateList<MyPath>
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
data class GameState(val lightingHouses:List<GameHouse> = emptyList(), val lightingSources:List<GameSource> =emptyList(), val colorSource:Color= Color.White, val colorHouse:Color= Color.White)














