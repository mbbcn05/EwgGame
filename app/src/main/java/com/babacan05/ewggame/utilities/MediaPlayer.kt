package com.babacan05.ewggame.utilities

import android.content.Context
import android.media.MediaPlayer
import com.babacan05.ewggame.R

class MyMediaPlayer(context: Context) {


   lateinit var successMusic: MediaPlayer
   lateinit var negativeMusic: MediaPlayer
    lateinit var mainMusic: MediaPlayer
    lateinit var gameOver:MediaPlayer
    init{
        successMusic = MediaPlayer.create(context, R.raw.success)
        negativeMusic = MediaPlayer.create(context, R.raw.negative)
        mainMusic = MediaPlayer.create(context, R.raw.gamemusiclooop7)
            .apply { setVolume(0.5f,0.5f)
            isLooping=true}
        gameOver=MediaPlayer.create(context, R.raw.gameover)
    }

    fun startMedia(media: MediaPlayer) {
        if (media.isPlaying) {
            media.pause()
            media.seekTo(0)
        }
        media.start()

    }
   fun pauseMedia(media: MediaPlayer){
      if( media.isPlaying) media.pause()
   }


}