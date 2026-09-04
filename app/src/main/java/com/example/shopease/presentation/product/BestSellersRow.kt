package com.example.shopease.presentation.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopease.domain.model.Product

@Composable
fun BestSellersRow(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        items(products, key = { it.id }) { product ->
            BestSellerCard(product = product, onClick = { onProductClick(product.id) })
        }
    }
}