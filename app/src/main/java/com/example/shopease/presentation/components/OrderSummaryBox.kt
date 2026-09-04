package com.example.shopease.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopease.domain.model.OrderSummary

@Composable
fun OrderSummaryBox(
    summary: OrderSummary,
    buttonText: String,
    onButtonClick: () -> Unit,
    buttonEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(shadowElevation = 8.dp, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {

            SummaryRow(label = "جمع قیمت", value = "${summary.subtotal} $")

            if (summary.discount > 0) {
                SummaryRow(
                    label = "تخفیف",
                    value = "- ${summary.discount} $",
                    valueColor = MaterialTheme.colorScheme.error
                )
            }

            if (summary.shippingCost > 0) {
                SummaryRow(label = "هزینه ارسال", value = "${summary.shippingCost} $")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            SummaryRow(
                label = "مبلغ نهایی",
                value = "${summary.total} $",
                labelWeight = FontWeight.Bold,
                valueWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onButtonClick,
                enabled = buttonEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    labelWeight: FontWeight = FontWeight.Normal,
    valueWeight: FontWeight = FontWeight.Normal,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = labelWeight)
        Text(text = value, fontWeight = valueWeight, color = valueColor)
    }
}