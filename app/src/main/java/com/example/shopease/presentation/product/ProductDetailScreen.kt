package com.example.shopease.presentation.product

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.shopease.R
import com.example.shopease.presentation.components.AddToCartSnackbarContent
import com.example.shopease.presentation.components.shimmerEffect
import com.example.shopease.presentation.components.showAddToCartMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private enum class DetailTab(val label: String) {
    DESCRIPTION("توضیحات"),
    SPECS("ویژگی‌ها"),
    REVIEWS("نظرات"),
    SIMILAR("محصولات مشابه")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val sectionOffsets = remember { mutableStateMapOf<DetailTab, Int>() }
    var selectedTab by remember { mutableStateOf(DetailTab.DESCRIPTION) }


    Scaffold(
        topBar = {
            com.example.shopease.presentation.components.ShopEaseTopBar(
                title = "",
                onMenuClick = onMenuClick,
                onNotificationsClick = onNotificationsClick,
                onProfileClick = onProfileClick,
                actions = {
                    Image(
                        painter = painterResource(R.drawable.onlineshop),
                        contentDescription = null
                    )
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "بازگشت")
                    }
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
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawCircle(
                    color = Color(0xfffceddf),
                    center = Offset(x = 800f, y = 75f),
                    radius = 392f
                )
                drawCircle(
                    color = Color(0xfffceddf),
                    center = Offset(x = 5f, y = 500f),
                    radius = 150f
                )
                drawCircle(
                    color = Color(0xfffceddf),
                    center = Offset(x = 5f, y = 1450f),
                    radius = 350f
                )
            }
            if (state.product == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                return@Scaffold
            }

            val p = state.product!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
            ) {
                var isImageLoading by remember { mutableStateOf(true) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    if (isImageLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerEffect()
                        )
                    }
                    AsyncImage(
                        model = p.imageUrl,
                        contentDescription = p.title,
                        contentScale = ContentScale.Fit,
                        onState = { s -> isImageLoading = s is AsyncImagePainter.State.Loading },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )

                }
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = p.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${p.price} $",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = { viewModel.toggleFavorite() }) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "علاقه‌مندی",
                        tint = if (state.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    DetailTab.entries.forEach { tab ->
                        TabBox(
                            label = tab.label,
                            selected = selectedTab == tab,
                            onClick = {
                                selectedTab = tab
                                sectionOffsets[tab]?.let { offset ->
                                    coroutineScope.launch {
                                        scrollState.animateScrollTo(offset)
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()

                SectionContainer(
                    modifier = Modifier.onGloballyPositioned { coords ->
                        sectionOffsets[DetailTab.DESCRIPTION] = coords.positionInParent().y.toInt()
                    }
                ) {
                    SectionTitle("توضیحات")
                    Text(text = p.description, style = MaterialTheme.typography.bodyMedium)
                }

                HorizontalDivider()

                SectionContainer(
                    modifier = Modifier.onGloballyPositioned { coords ->
                        sectionOffsets[DetailTab.SPECS] = coords.positionInParent().y.toInt()
                    }
                ) {
                    SectionTitle("ویژگی‌ها")
                    SpecRow("دسته‌بندی", p.category)
                    SpecRow("امتیاز", "${p.rating} از ۵")
                    SpecRow("تعداد نظرات", "${p.reviewCount}")
                    SpecRow("موجودی", "${p.stock} عدد")
                }

                HorizontalDivider()

                SectionContainer(
                    modifier = Modifier.onGloballyPositioned { coords ->
                        sectionOffsets[DetailTab.REVIEWS] = coords.positionInParent().y.toInt()
                    }
                ) {
                    SectionTitle("نظرات (${state.reviews.size})")
                    if (state.reviews.isEmpty()) {
                        Text("هنوز نظری ثبت نشده است", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        state.reviews.forEach { review ->
                            ReviewRow(review)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

                HorizontalDivider()

                SectionContainer(
                    modifier = Modifier.onGloballyPositioned { coords ->
                        sectionOffsets[DetailTab.SIMILAR] = coords.positionInParent().y.toInt()
                    }
                ) {
                    SectionTitle("محصولات مشابه")
                    if (state.similarProducts.isEmpty()) {
                        Text("محصول مشابهی یافت نشد", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        BestSellersRow(
                            products = state.similarProducts,
                            onProductClick = onProductClick
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.addToCart()
                        coroutineScope.launch {
                            snackbarHostState.showAddToCartMessage()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFF33224))
                ) {
                    Text("افزودن به سبد خرید")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun TabBox(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor =
        if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val textColor =
        if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun SectionContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ReviewRow(review: com.example.shopease.domain.model.Review) {
    val dateStr = remember(review.date) {
        SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(review.date))
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(review.userName, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(14.dp)
            )
            Text(" ${review.rating}", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(review.comment, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            dateStr,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}