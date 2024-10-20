package com.gouravsh.stackmarketapp.presentation.company_listings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gouravsh.stackmarketapp.domain.model.CompanyListing

@Composable
fun Companyitem(
    company: CompanyListing,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = company.name,
                fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = company.symbol,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,

                )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "(${company.symbol})" , fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onBackground,)
    }

}