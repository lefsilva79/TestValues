package com.lr.testvalues

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomValuesDisplay()
                }
            }
        }
    }
}

@Composable
fun RandomValuesDisplay() {
    var values by remember { mutableStateOf<List<String>>(emptyList()) }
    var changeCount by remember { mutableStateOf(0) }
    var isActive by remember { mutableStateOf(false) }
    var refreshKey by remember { mutableStateOf(0) }
    var showValues by remember { mutableStateOf(false) }

    LaunchedEffect(refreshKey) {
        if (isActive) {
            changeCount = 0
            values = generateRandomValues()
            showValues = true

            android.util.Log.d("TestValues", "===== VALORES INICIAIS GERADOS =====")
            values.forEachIndexed { index, value ->
                android.util.Log.d("TestValues", "Valor ${index + 1}: $$value")
            }
            android.util.Log.d("TestValues", "Horário: ${LocalTime.now()}")

            while (isActive && changeCount < 5) {
                delay(500)
                val newValues = generateRandomValues()
                values = newValues
                changeCount++

                android.util.Log.d("TestValues", "===== NOVOS VALORES GERADOS =====")
                android.util.Log.d("TestValues", "Mudança: $changeCount de 5")
                newValues.forEachIndexed { index, value ->
                    android.util.Log.d("TestValues", "Valor ${index + 1}: $$value")
                }
                android.util.Log.d("TestValues", "Horário: ${LocalTime.now()}")

                if (changeCount >= 5) {
                    isActive = false
                    android.util.Log.d("TestValues", "===== CICLO FINALIZADO =====")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    showValues = false
                    isActive = false
                    values = emptyList()
                    changeCount = 0
                    Log.d("TestValues", "===== CLEAR SOLICITADO =====")
                    Log.d("TestValues", "→ Horário do clear: ${LocalTime.now()}")
                }
            ) {
                Text("Clear")
            }

            Button(
                onClick = {
                    if (!isActive) {
                        isActive = true
                        refreshKey++
                        Log.d("TestValues", "===== REFRESH SOLICITADO =====")
                        Log.d("TestValues", "→ Horário do gerar: ${LocalTime.now()}")
                    }
                }
            ) {
                Text(if (!isActive) "Refresh" else "Gerando...")
            }
        }

        if (showValues) {
            values.forEach { value ->
                Text(
                    text = "$$value",
                    fontSize = 24.sp,
                    modifier = Modifier.clickable { }
                )
            }

            Text(
                text = "Mudanças a cada 0.5 segundos: $changeCount/5",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun generateRandomValues(): List<String> {
    return List(4) {
        when (Random.nextInt(3)) {
            0 -> Random.nextInt(1, 200).toString()
            1 -> String.format("%.2f", Random.nextDouble(1.0, 199.99))
            else -> Random.nextInt(1, 200).toString()
        }
    }
}