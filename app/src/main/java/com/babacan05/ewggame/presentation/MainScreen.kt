package com.babacan05.ewggame.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.babacan05.ewggame.utilities.AdMobBanner
import com.babacan05.ewggame.utilities.MyMediaPlayer
import com.babacan05.ewggame.R
import com.babacan05.ewggame.gamefiles.GameLogic
import com.babacan05.ewggame.gamefiles.GameBitmaps
import com.babacan05.ewggame.gamefiles.GameChanger
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke

import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mediaPlayer: MyMediaPlayer,
    navigate: () -> Unit,

    loadAd: () -> Unit,



    ){

    val context= LocalContext.current
var startGame by remember {
    mutableStateOf(false)
}
    GameBitmaps.density=context.resources.displayMetrics.density
   // loadAd()
    LaunchedEffect(true){
        GameChanger.game= GameLogic()

        // GameChanger.game.loadBitmaps(context.resources)
        if(!GameBitmaps.bitmapsLoaded){
            GameBitmaps.loadBitmaps(context.resources)
            GameBitmaps.bitmapsLoaded=true
        }
        delay(1000)
        startGame=true

    }





    Box (contentAlignment = Alignment.Center, modifier= Modifier
        .background(color = Color.Black)
        .fillMaxSize()) {
        Box{

            Image(
                painter = painterResource(R.drawable.main),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black, RoundedCornerShape(220.dp))
                    .clip(
                        RoundedCornerShape(320.dp)
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {

            AnimatedVisibility(visible = startGame) {

    Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        StarBurst()
        Spacer(modifier = Modifier.size(15.dp))
        Button(border = BorderStroke(4.dp,Color.Red),onClick = {  navigate()
        }){
            Text(fontWeight = FontWeight.ExtraBold,color=Color.Green,text = "START GAME", modifier = Modifier.background(Color.Cyan.copy(alpha = 0.3f),  RoundedCornerShape(20.dp)))
        }
        Spacer(modifier = Modifier.size(15.dp))
        StarBurst()

    }
}




        }


        // Text("Draw paths from all sources to all houses without any intersection")

        AnimatedVisibility(modifier=Modifier.alpha(0.2f).align(Alignment.BottomCenter),visible =true) {
            AdMobBanner()
        }

    }

}


