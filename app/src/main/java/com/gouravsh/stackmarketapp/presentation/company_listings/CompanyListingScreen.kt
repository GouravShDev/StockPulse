package com.gouravsh.stackmarketapp.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.gouravsh.stackmarketapp.presentation.company_info.CompanyInfoScreen
import com.gouravsh.stackmarketapp.presentation.destinations.CompanyInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun CompanyListingScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    val state = viewModel.state
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Stock Pulse 📈",
                        fontSize = 24.sp,           // Increased font size
                        fontWeight = FontWeight.Bold // Bold text
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {

        Column(modifier = Modifier
            .padding(it)
            .padding(16.dp)) {
            CompanyListingSearchBar(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            SwipeRefresh(state = swipeRefreshState,

                onRefresh = {
                    viewModel.onEvent(CompanyListingEvent.Refresh)
                }) {
                if(state.error != null){
                    Box (
                        modifier = Modifier.padding(it).padding(16.dp)
                    ){
                        Text(text = state.error, color = MaterialTheme.colorScheme.error)
                    }
                    return@SwipeRefresh
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.companies) { company ->
                        Companyitem(company = company, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(CompanyInfoScreenDestination(symbol = company.symbol))
                            }
                            .padding(all = 16.dp))
                    }
                }
            }
        }
    }


}
