//ok 227 linhas
package com.lr.testvalues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import kotlin.random.Random

data class BatchItem(
    val value: String = "12.02",
    val distance: String = "1.2 mi",
    val itemsInfo: String = "2 shop and deliver • items",
    val stores: List<Store> = emptyList()
)

data class Store(
    val name: String,
    val address: String
)

private fun generateRandomBatchItems(): List<BatchItem> {
    return listOf(
        BatchItem(
            value = String.format("%.2f", Random.nextDouble(7.0, 200.0)),
            distance = "5.3 mi",
            itemsInfo = "2 shop and deliver • 61 items (78 units)",
            stores = listOf(
                Store("Mariano's", "802 Northwest Highway, Arlington Heights"),
                Store("ALDI", "1432 E. Rand Road, Prospect Heights")
            )
        ),
        BatchItem(
            value = String.format("%.2f", Random.nextDouble(7.0, 200.0)),
            distance = "4.8 mi",
            itemsInfo = "2 shop and deliver • 55 items (72 units)",
            stores = listOf(
                Store("Walmart", "1100 S Rand Rd, Lake Zurich"),
                Store("Target", "1850 S Grove Ave, Arlington Heights")
            )
        )
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var items by remember { mutableStateOf<List<BatchItem>>(emptyList()) }
    var changeCount by remember { mutableStateOf(0) }
    var isActive by remember { mutableStateOf(false) }
    var refreshKey by remember { mutableStateOf(0) }
    var showValues by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    fun Int.pxToDp() = with(density) { this@pxToDp.toDp() }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = true,
                    onClick = { },
                    label = { Text("Home") },
                    icon = { }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = { },
                    label = { Text("List") },
                    icon = { }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Botões
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        showValues = false
                        isActive = false
                        items = emptyList()
                        changeCount = 0
                    }
                ) {
                    Text("Clear")
                }

                Button(
                    onClick = {
                        if (!isActive) {
                            isActive = true
                            refreshKey++
                        }
                    }
                ) {
                    Text(if (!isActive) "Refresh" else "Gerando...")
                }
            }

            // Lista
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                if (showValues) {
                    items(items) { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        ) {
                            // Valor principal
                            Text(
                                text = "$${item.value}",
                                fontSize = 98.pxToDp().value.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Informações dos itens
                            Text(
                                text = item.itemsInfo,
                                fontSize = 47.pxToDp().value.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Distância
                            Text(
                                text = item.distance,
                                fontSize = 48.pxToDp().value.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Lojas
                            item.stores.forEach { store ->
                                Text(
                                    text = "${store.name}\n${store.address}",
                                    fontSize = 47.pxToDp().value.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Divider(
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }

            if (showValues) {
                Text(
                    text = "Mudanças: $changeCount/5",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }

    LaunchedEffect(refreshKey) {
        if (isActive) {
            changeCount = 0
            showValues = false  // Limpa a tela
            delay(1000)  // Delay de 1 segundo com a tela limpa
            items = generateRandomBatchItems()
            showValues = true

            while (isActive && changeCount < 5) {
                delay(500)
                showValues = false  // Limpa a tela
                delay(1000)  // Delay de 1 segundo com a tela limpa
                items = generateRandomBatchItems()
                showValues = true
                changeCount++

                if (changeCount >= 5) {
                    isActive = false
                }
            }
        }
    }
}