package com.example.shopease.presentation.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopease.R
import com.example.shopease.presentation.components.AddToCartSnackbarContent
import com.example.shopease.presentation.components.showAddToCartMessage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            com.example.shopease.presentation.components.ShopEaseTopBar(
                title = "",
                onMenuClick = onMenuClick,
                onNotificationsClick = onNotificationsClick,
                onProfileClick = onProfileClick,
                actions = {
                    Image(
                        painter = painterResource(com.example.shopease.R.drawable.onlineshop),
                        contentDescription = null
                    )
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                AddToCartSnackbarContent(data)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading && state.products.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null && state.products.isEmpty() -> {
                    ErrorContent(
                        message = state.error ?: "خطای نامشخص",
                        onRetry = { viewModel.refresh() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(height = 160.dp, width = 400.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xff658844), Color(0xff597D3C))
                                        )
                                    )
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.baner),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp, end = 40.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(height = 19.dp, width = 55.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xFFD06F51), Color(0xFFE31919))
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "تخفیف ویژه",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White

                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 61.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                IconButton(
                                    onClick = {},
                                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                                ) {
                                    Icon(
                                        Icons.Default.ChevronRight,
                                        contentDescription = null
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 61.dp),
                                contentAlignment = Alignment.TopEnd

                            ) {
                                IconButton(
                                    onClick = {},
                                    colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                                ) {
                                    Icon(
                                        Icons.Default.ChevronLeft,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        Text(
                            "تنها با یک کلیک خرید کن!",
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(start = 8.dp)

                        )
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            label = { Text("جستجوی محصول") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            leadingIcon ={
                                Image(painter = painterResource(R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                            }
                        )

                        Text(
                            text = "دسته‌بندی‌ها",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CategoryRow(
                            categories = state.categories,
                            categoryImages = state.categoryImages,
                            onCategoryClick = onCategoryClick
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (state.bestSellers.isNotEmpty()) {
                            Text(
                                text = "پرفروش‌ترین‌ها",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            BestSellersRow(
                                products = state.bestSellers,
                                onProductClick = onProductClick
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        ProductGrid(
                            products = state.filteredProducts,
                            onProductClick = onProductClick,
                            onAddToCart = { product ->
                                viewModel.addToCart(product)
                                coroutineScope.launch {
                                    snackbarHostState.showAddToCartMessage()
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onRetry) {
            Text("تلاش مجدد")
        }
    }
}

@Composable
private fun CategoryRow(
    categories: List<String>,
    categoryImages: Map<String, String>,
    onCategoryClick: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                name = category,
                imageUrl = categoryImages[category],
                onClick = { onCategoryClick(category) },
                modifier = Modifier.width(96.dp)
            )
        }
    }
}
