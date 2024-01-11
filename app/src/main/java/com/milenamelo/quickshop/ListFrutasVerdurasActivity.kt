package com.milenamelo.quickshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milenamelo.quickshop.ui.theme.QuickShopTheme

class ListFrutasVerdurasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FruitsVegetablesShoppingList(
                        items = arrayOf(
                            "Maçã",
                            "Banana",
                            "Laranja",
                            "Abacaxi",
                            "Alface",
                            "Tomate",
                            "Cenoura",
                            "Brócolis",
                            "Morango",
                            "Uva"
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FruitsVegetablesShoppingList(items: Array<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            FruitsVegetablesItemRow(
                item = item,
                isChecked = remember { mutableStateOf(false) },
                onRemoveClick = {
                    items.removeAt(index)
                }
            )
        }
    }
}

private fun <T> Array<T>.removeAt(index: Int) {
    TODO("Not yet implemented")
}

@Composable
fun FruitsVegetablesItemRow(item: String, isChecked: MutableState<Boolean>, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            imageVector = if (isChecked.value) Icons.Default.Check else Icons.Default.Delete,
            contentDescription = if (isChecked.value) "Concluído" else "Remover",
            modifier = Modifier
                .clickable {
                    isChecked.value = !isChecked.value
                }
                .padding(end = 16.dp)
        )

        Text(
            text = item,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FruitsVegetablesShoppingListPreview() {
    QuickShopTheme {
        FruitsVegetablesShoppingList(
            items = arrayOf(
                "Maçã",
                "Banana",
                "Laranja",
                "Abacaxi",
                "Alface",
                "Tomate",
                "Cenoura",
                "Brócolis",
                "Morango",
                "Uva"
            )
        )
    }
}
