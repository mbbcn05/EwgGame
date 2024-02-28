package com.babacan05.ewggame.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.babacan05.ewggame.Ad
import com.babacan05.ewggame.AdMobBanner
import com.babacan05.ewggame.GameCanvas.GameCanvasScreen
import com.babacan05.ewggame.R


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameNavigation(context:ComponentActivity){




    val navController=rememberNavController()
    NavHost(navController = navController, startDestination = "splash screen"){



        composable(route="splash screen"){

           GameCanvasScreen()


      
}
        composable(route="interstitial ad"){
            

        }

    }
}

@Composable
fun MainScreen(context: ComponentActivity, modifier: Modifier = Modifier ){


Box (contentAlignment = Alignment.Center, modifier= modifier
    .background(color = Color.Cyan)
    .fillMaxSize()){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = modifier) {
        AdMobBanner()
      Row (horizontalArrangement =Arrangement.Center, verticalAlignment =Alignment.CenterVertically, modifier =Modifier.fillMaxWidth()){
          Image(
              painter = painterResource(R.drawable.splash),
              contentDescription = null,
              modifier = Modifier
                  .width(220.dp)
                  .height(300.dp)
          )
          Spacer(modifier=modifier.size(15.dp))
          Button(onClick = {
              if (Ad.institialad) {
                  Ad.showAd()
              } else {
                  Ad.loadadAd()
                 // openGameActivity(context = context)
              }


          }) {
              Text(text = "Start Game")
          }
      }
     Text("Draw paths from all sources to all houses without any intersection")


    }
}
}
@Preview(showSystemUi = true)
@Composable
fun ShowMainScreen(){

    MainScreen(ComponentActivity())
}



