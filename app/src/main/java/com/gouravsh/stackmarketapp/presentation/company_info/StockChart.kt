package com.gouravsh.stackmarketapp.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gouravsh.stackmarketapp.domain.model.IntradayInfo
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<IntradayInfo>,
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    val spacing = 100f;
    val transaparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close })?.plus(1)?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        (infos.minOfOrNull { it.close })?.toInt() ?: 0
    }

}