package com.lr.testvalues

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

data class BatchItem(
    val value: String,
    val distance: String,
    val itemsInfo: String,
    val stores: List<Store>
)

data class Store(
    val name: String,
    val address: String
)

private const val NUMBER_OF_ITEMS = 6

private fun generateRandomBatchItems(): List<BatchItem> {
    val batchItems = mutableListOf<BatchItem>()

    repeat(NUMBER_OF_ITEMS) { index ->
        val value = String.format("%.2f", Random.nextDouble(7.0, 200.0))
        Log.d("TestValues", "Generated value ${index + 1}: $$value")

        batchItems.add(BatchItem(
            value = value,
            distance = "5.3 mi",
            itemsInfo = "2 shop and deliver • 61 items (78 units)",
            stores = listOf(
                Store("Mariano's", "802 Northwest Highway, Arlington Heights"),
                Store("ALDI", "1432 E. Rand Road, Prospect Heights")
            )
        ))
    }

    return batchItems
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
    var isAutoGenerateEnabled by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAutoGenerateEnabled,
                    onCheckedChange = { checked ->
                        isAutoGenerateEnabled = checked
                    }
                )
                Text(
                    text = "Gerar valores automaticamente",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

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

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    if (!isAutoGenerateEnabled && !isActive) {
                        isRefreshing = true
                        isActive = true
                        refreshKey++
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    if (showValues) {
                        items(items) { item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                            ) {
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

                                Text(
                                    text = item.itemsInfo,
                                    fontSize = 47.pxToDp().value.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Text(
                                    text = item.distance,
                                    fontSize = 48.pxToDp().value.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

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
            }

//            if (showValues) {
//                Text(
//                    text = "Mudanças: $changeCount/4",
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//            }
        }
    }

    LaunchedEffect(refreshKey) {
        if (isActive) {
            changeCount = 0
            showValues = false
            delay(1000)
            items = generateRandomBatchItems()
            Log.d("TestValues", "Values generated - Change count: $changeCount/4")
            showValues = true
            isRefreshing = false

            if (isAutoGenerateEnabled) {
                repeat(4) {
                    delay(300)
                    showValues = false
                    delay(1000)
                    items = generateRandomBatchItems()
                    changeCount = it + 1
                    Log.d("TestValues", "Values updated - Change count: $changeCount/4")
                    showValues = true
                }
            }

            isActive = false
            Log.d("TestValues", "Finished generating values")
        }
    }
}