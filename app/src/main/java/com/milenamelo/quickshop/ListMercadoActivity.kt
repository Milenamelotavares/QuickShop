package com.milenamelo.quickshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.milenamelo.quickshop.ui.theme.QuickShopTheme

class ListMercadoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shoppingList = arrayOf(
            "Arroz",
            "Feijão",
            "Leite",
            "Pão",
            "Carne",
            "Frutas",
            "Legumes",
            "Ovos",
            "Sabonete",
            "Detergente",
            "Shampoo",
            "Escova de dentes",
            "Papel higiênico",
            "Creme dental"
        )

        setContent {
            QuickShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingList(shoppingList)
                }
            }
        }
    }
}

@Composable
fun ShoppingList(items: Array<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        items.forEach { item ->
            ItemRow(item = item)
        }
    }
}

@Composable
fun ItemRow(item: String) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { isChecked = !isChecked }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = item, modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    QuickShopTheme {
        ShoppingList(
            items = arrayOf(
                "Arroz",
                "Feijão",
                "Leite",
                "Pão",
                "Carne",
                "Frutas",
                "Legumes",
                "Ovos",
                "Sabonete",
                "Detergente",
                "Shampoo",
                "Escova de dentes",
                "Papel higiênico",
                "Creme dental"
            )
        )
    }
}
