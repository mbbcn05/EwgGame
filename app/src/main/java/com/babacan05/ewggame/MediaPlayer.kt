package com.babacan05.ewggame

import android.content.Context
import android.media.MediaPlayer

class MediaPlayer(context: Context) {
   lateinit var successMusic: MediaPlayer
   lateinit var negativeMusic: MediaPlayer

    init{
        successMusic = MediaPlayer.create(context, R.raw.success)
        negativeMusic = MediaPlayer.create(context, R.raw.negative)
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