package com.lr.testvalues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val value = intent.getStringExtra("value") ?: "0.00"

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                DetailScreen(value)
            }
        }
    }
}

@Composable
fun DetailScreen(value: String) {
    val density = LocalDensity.current
    fun Int.pxToDp() = with(density) { this@pxToDp.toDp() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Valor no topo
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$$value",
                fontSize = 98.pxToDp().value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Spacer para empurrar o botão para baixo
        Spacer(modifier = Modifier.weight(1f))

        // Botão Accept na parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .height(142.pxToDp())
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF43B02A)  // Cor verde do Instacart
                )
            ) {
                Text(
                    text = "Accept",
                    fontSize = 47.pxToDp().value.sp,
                    color = Color.White
                )
            }
        }
    }
}