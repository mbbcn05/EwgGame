package com.babacan05.ewggame.presentation

import androidx.compose.ui.graphics.Color
import babacan.Game.GameSource
import com.babacan05.ewggame.gamefiles.GameHouse

data class GameState(val lightingHouses:List<GameHouse> = emptyList(), val lightingSources:List<GameSource> =emptyList(), val colorSource: Color = Color.White, val colorHouse: Color = Color.White)
