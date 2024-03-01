package com.example.myapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import babacan.Game.GameSource
import babacan.Game.MyLine
import babacan.Game.MyPath
import babacan.Game.MyPoint
import babacan.Game.MyRectangle
import babacan.Game.SourceType
import com.babacan05.ewggame.MainActivity
import com.babacan05.ewggame.gamecanvas.GameState

class Game {


    var y=MainActivity.y
    var x=MainActivity.x


    var creathingPath: MyPath? = null

    val myPathList: MutableList<MyPath> = mutableListOf()


    val houses: List<GameHouse> = listOf(
        GameHouse(MyRectangle(MyPoint(x / 8f, y / 5f))),
        GameHouse(MyRectangle(MyPoint(x / 2f - 0.5f * x / 8f, y / 5f))),
        GameHouse(MyRectangle(MyPoint(x - 2 * x / 8f, y / 5f)))
    )

    val sources: List<GameSource> = listOf(
        GameSource(
            MyRectangle(MyPoint(x / 8f, y - 3 * y / 7f)),
            SourceType.ELECTRIC,
        ),
        GameSource(
            MyRectangle(
                MyPoint(
                    x / 2f - 0.5f * x / 8f,
                    y - 3 * y / 7f
                )
            ), SourceType.GAS
        ),
        GameSource(
            MyRectangle(MyPoint(x - 2 * x / 8f, y - 3 * y / 7f)),
            SourceType.WATER
        )
    )

    fun handleSourceSelecting(offset: Offset, gameState: MutableState<GameState>) {

        if (creathingPath == null) {

            sources.forEach { it ->

                if(it.shape.rect.contains(offset)) {


                    creathingPath = MyPath(it,this)
gameState.value=gameState.value.copy(lightingSources = listOf(it), colorSource = Color.Blue, lightingHouses =houses.filter { it.IsNotFilled() }, colorHouse = Color.Green)



                }
            }
        }
    }

    fun handleSourceMoving(x: Float, y: Float, creathingLines: SnapshotStateList<MyLine>) {

        creathingPath?.let { it.addLines(x, y,creathingLines)


        }

    }

    fun handleHouseSelecting(
        offset: Offset,
        paths: SnapshotStateList<MyPath>,
        creathingLines: SnapshotStateList<MyLine>,
        gameState: MutableState<GameState>,
        succes: MutableState<Boolean>
    ) {

        var houseSelecting=false
        houses.forEach {
            if (it.rectangle.rect.contains(offset) &&! creathingPath!!.intersectsWithPaths(myPathList)&&!creathingPath!!.isIntersectOtherHouses(it) &&!creathingPath!!.isIntersectOtherSources()&&it.acceptIfNotContained(creathingPath!!.source)) {
                houseSelecting=true
                println("house başarıyla seçildi")
                creathingPath!!.apply {
                   val clippingPath=clipLinesInRectangle(it.rectangle,this.source.shape)
                    myPathList.add(clippingPath)
                    paths.add(clippingPath)
                    creathingPath = null
                    creathingLines.clear()
                    val lightingSources = sources.toMutableList()
                    val sourceCounts = houses.flatMap { it.getSourceTypes() }.groupingBy { it }.eachCount()

                    sourceCounts.forEach { (sourceType, count) ->
                        if (count > 2) {
                            lightingSources.removeIf { it.type == sourceType }
                        }
                    }

                   gameState.value=gameState.value.copy(colorSource = Color.White, colorHouse =Color.White, lightingHouses = emptyList(), lightingSources =lightingSources)
                   succes.value=true
                    return
                }
            }
        }


        if(!houseSelecting){
            creathingPath=null
            creathingLines.clear()
        }
        val lightingSources = sources.toMutableList()
        val sourceCounts = houses.flatMap { it.getSourceTypes() }.groupingBy { it }.eachCount()

        sourceCounts.forEach { (sourceType, count) ->
            if (count > 2) {
                lightingSources.removeIf { it.type == sourceType }
            }
        }

    gameState.value=gameState.value.copy(colorSource = Color.White, colorHouse =Color.White, lightingHouses = emptyList(), lightingSources =lightingSources )
    }
}
