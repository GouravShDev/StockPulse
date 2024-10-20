package com.gouravsh.stackmarketapp.presentation.company_listings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingSearchBar(
    viewModel: CompanyListingViewModel
) {
    val defaultValue = "";

    val searchValue = remember {
        mutableStateOf(defaultValue);
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
           Text(text = "Search")
        },
        value = searchValue.value,
         onValueChange = {
             Log.d("Searched", it);
             searchValue.value = it;
            viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))
        })
}