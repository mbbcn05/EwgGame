package com.example.myapplication

import babacan.Game.GameSource
import babacan.Game.MyRectangle
import babacan.Game.SourceType

class GameHouse(val rectangle: MyRectangle) {

    private val sourceMap: HashMap<SourceType, GameSource?> = HashMap()

    private fun notContains(source: GameSource) = sourceMap.get(source.type) == null

    private fun accept(source: GameSource) = sourceMap.put(source.type, source)

    fun acceptIfNotContained(source: GameSource): Boolean {
        return notContains(source)
            .apply {
                if (this) {
                    accept(source)
                }
            }

    }

    fun cleanAllSources(){
        println("SİLİNDİ")
        sourceMap.clear()

    }
    fun IsNotFilled() : Boolean{
        return !sourceMap.keys.containsAll(listOf(SourceType.ELECTRIC,SourceType.WATER,SourceType.GAS))
    }
    fun getSourceTypes():List<SourceType>{
        return sourceMap.keys.toMutableList()
    }

}