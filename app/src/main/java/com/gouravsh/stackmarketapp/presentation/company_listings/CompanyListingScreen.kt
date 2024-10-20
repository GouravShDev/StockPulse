package com.gouravsh.stackmarketapp.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListingScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    val state = viewModel.state
    Column (modifier = Modifier.padding(8.dp)) {
        CompanyListingSearchBar(viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        SwipeRefresh(state = swipeRefreshState,

            onRefresh = {
                viewModel.onEvent(CompanyListingEvent.Refresh)
            }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.companies) { company ->
                    Companyitem(company = company, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(all = 16.dp))
                }
            }
        }
    }


}
