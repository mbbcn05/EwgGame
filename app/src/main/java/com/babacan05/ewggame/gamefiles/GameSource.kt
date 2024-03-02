package babacan.Game

import com.babacan05.ewggame.gamefiles.MyRectangle


class GameSource(val shape: MyRectangle, val type: SourceType)


enum class SourceType{
        ELECTRIC,GAS,WATER
    }
