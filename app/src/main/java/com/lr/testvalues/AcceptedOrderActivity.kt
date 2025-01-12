package com.lr.testvalues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AcceptedOrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val value = intent.getStringExtra("value") ?: "0.00"

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White  // Fundo branco igual à tela anterior
            ) {
                AcceptedOrderScreen(value)
            }
        }
    }
}

@Composable
fun AcceptedOrderScreen(value: String) {
    val density = LocalDensity.current
    fun Int.pxToDp() = with(density) { this@pxToDp.toDp() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Mensagem de sucesso no topo
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Order Accepted!",
                fontSize = 64.pxToDp().value.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF43B02A)  // Verde do Instacart
            )
        }

        // Valor no meio
        Text(
            text = "$$value",
            fontSize = 98.pxToDp().value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black  // Mesma cor do texto da tela anterior
        )

        // Botão Start Shopping na parte inferior
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
                    backgroundColor = Color(0xFF43B02A)  // Verde do Instacart
                )
            ) {
                Text(
                    text = "Start Shopping",
                    fontSize = 47.pxToDp().value.sp,
                    color = Color.White  // Texto branco igual ao botão Accept
                )
            }
        }
    }
}