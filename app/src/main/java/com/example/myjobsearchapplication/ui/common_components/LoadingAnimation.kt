package com.example.myjobsearchapplication.ui.common_components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun LoadingAnimation() {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        ), label = ""
    )
    val rotation2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ), label = ""
    )
    val rotation3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)){
            CircularProgressIndicator(
                modifier = Modifier.size(150.dp)
                    .graphicsLayer{rotationZ = rotation1}
                    .padding(8.dp),
                strokeWidth = 5.dp
            )

            CircularProgressIndicator(
                modifier = Modifier.size(110.dp)
                    .graphicsLayer{rotationZ = rotation2}
                    .padding(16.dp),
                strokeWidth = 5.dp
            )

            CircularProgressIndicator(
                modifier = Modifier.size(80.dp)
                    .graphicsLayer{rotationZ = rotation3}
                    .padding(24.dp),
                strokeWidth = 5.dp
            )
        }
    }


}