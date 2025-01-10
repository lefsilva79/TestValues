package com.lr.testvalues

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import kotlin.random.Random
import android.util.Log

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
    val value1 = String.format("%.2f", Random.nextDouble(7.0, 200.0))
    val value2 = String.format("%.2f", Random.nextDouble(7.0, 200.0))
    val value3 = String.format("%.2f", Random.nextDouble(7.0, 200.0))
    val value4 = String.format("%.2f", Random.nextDouble(7.0, 200.0))
    val value5 = String.format("%.2f", Random.nextDouble(7.0, 200.0))
    val value6 = String.format("%.2f", Random.nextDouble(7.0, 200.0))

    // Log dos valores gerados
    Log.d("TestValues", "Generated value 1: $$value1")
    Log.d("TestValues", "Generated value 2: $$value2")
    Log.d("TestValues", "Generated value 3: $$value3")
    Log.d("TestValues", "Generated value 4: $$value4")
    Log.d("TestValues", "Generated value 5: $$value5")
    Log.d("TestValues", "Generated value 6: $$value6")

    return listOf(
        BatchItem(
            value = value1,
            distance = "5.3 mi",
            itemsInfo = "2 shop and deliver • 61 items (78 units)",
            stores = listOf(
                Store("Mariano's", "802 Northwest Highway, Arlington Heights"),
                Store("ALDI", "1432 E. Rand Road, Prospect Heights")
            )
        ),
        BatchItem(
            value = value2,
            distance = "4.8 mi",
            itemsInfo = "2 shop and deliver • 55 items (72 units)",
            stores = listOf(
                Store("Walmart", "1100 S Rand Rd, Lake Zurich"),
                Store("Target", "1850 S Grove Ave, Arlington Heights")
            )
        ),
        BatchItem(
            value = value3,
            distance = "3.2 mi",
            itemsInfo = "1 shop and deliver • 42 items (50 units)",
            stores = listOf(
                Store("Costco", "999 N Milwaukee Ave, Vernon Hills")
            )
        ),
        BatchItem(
            value = value4,
            distance = "6.1 mi",
            itemsInfo = "2 shop and deliver • 38 items (45 units)",
            stores = listOf(
                Store("Whole Foods", "760 W Rand Rd, Arlington Heights"),
                Store("Jewel-Osco", "440 E Rand Rd, Arlington Heights")
            )
        ),
        BatchItem(
            value = value5,
            distance = "2.9 mi",
            itemsInfo = "2 shop and deliver • 45 items (52 units)",
            stores = listOf(
                Store("Fresh Market", "1600 S Milwaukee Ave, Libertyville"),
                Store("Trader Joe's", "127 N Milwaukee Ave, Vernon Hills")
            )
        ),
        BatchItem(
            value = value6,
            distance = "5.5 mi",
            itemsInfo = "1 shop and deliver • 28 items (35 units)",
            stores = listOf(
                Store("Sam's Club", "900 S Barrington Rd, Streamwood")
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
    val context = LocalContext.current

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
                            // Valor principal com clickable
                            Text(
                                text = "$${item.value}",
                                fontSize = 98.pxToDp().value.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .clickable {
                                        val intent = Intent(context, DetailActivity::class.java).apply {
                                            putExtra("value", item.value)
                                        }
                                        context.startActivity(intent)
                                    }
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
            showValues = false
            delay(1000)
            items = generateRandomBatchItems()
            Log.d("TestValues", "Values updated - Change count: $changeCount/5")
            showValues = true

            while (isActive && changeCount < 5) {
                delay(300)
                showValues = false
                delay(1000)
                items = generateRandomBatchItems()
                changeCount++
                Log.d("TestValues", "Values updated - Change count: $changeCount/5")
                showValues = true

                if (changeCount >= 5) {
                    isActive = false
                    Log.d("TestValues", "Finished generating values")
                }
            }
        }
    }
}