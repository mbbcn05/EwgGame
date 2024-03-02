package com.babacan05.ewggame.presentation

import android.os.Build
import androidx.annotation.RequiresApi


import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.babacan05.ewggame.utilities.MyMediaPlayer
import com.babacan05.ewggame.gamefiles.GameChanger


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameNavigation(showInterstitialAdCallback: () -> Unit, mediaPlayer: MyMediaPlayer){





    val navController=rememberNavController()
    NavHost(navController = navController, startDestination = "start screen",){



        composable(route="game screen"){

           GameCanvasScreen(mediaPlayer = mediaPlayer,game= GameChanger.game) { navController.navigate("start screen"){
               popUpTo("game screen") {
                   inclusive = true
               }
           } }


        }
        composable(route="start screen"){
            
MainScreen(mediaPlayer = mediaPlayer,{navController.navigate("game screen"){

    popUpTo("start screen") {
        inclusive = true
    }

} },loadAd=showInterstitialAdCallback)
        }

    }
}
