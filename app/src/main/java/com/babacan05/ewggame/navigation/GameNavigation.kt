package com.babacan05.ewggame.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

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

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.babacan05.ewggame.AdMobBanner
import com.babacan05.ewggame.gamefiles.GameBitmaps
import com.babacan05.ewggame.gamecanvas.GameCanvasScreen
import com.babacan05.ewggame.gamefiles.GameChanger
import com.babacan05.ewggame.R
import com.babacan05.ewggame.gamefiles.Game


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameNavigation( showInterstitialAdCallback: () -> Unit){





    val navController=rememberNavController()
    NavHost(navController = navController, startDestination = "start screen"){



        composable(route="game screen"){

           GameCanvasScreen(game= GameChanger.game) { navController.navigate("start screen"){
               popUpTo("game screen") {
                   inclusive = true
               }
           } }


        }
        composable(route="start screen"){
            
MainScreen({navController.navigate("game screen"){

    popUpTo("start screen") {
        inclusive = true
    }

} },loadAd=showInterstitialAdCallback)
        }

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    navigate: () -> Unit,

    loadAd: () -> Unit,



    ){

val context= LocalContext.current

    GameBitmaps.density=context.resources.displayMetrics.density
    loadAd()
LaunchedEffect(true){
    GameChanger.game= Game()

   // GameChanger.game.loadBitmaps(context.resources)
if(!GameBitmaps.bitmapsLoaded){
    GameBitmaps.loadBitmaps(context.resources)
    GameBitmaps.bitmapsLoaded=true
}

}






Box (contentAlignment = Alignment.Center, modifier= Modifier
    .background(color = Color.Black)
    .fillMaxSize()) {
    Box{

        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize().background(Color.Black,RoundedCornerShape(220.dp)).clip(RoundedCornerShape(320.dp))
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.align(Alignment.TopCenter)
    ) {
        AdMobBanner()
        Button(onClick = {  navigate()}){
            Text(text = "START GAME")
        }


        }
       // Text("Draw paths from all sources to all houses without any intersection")


    }

}
