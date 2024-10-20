package com.gouravsh.stackmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gouravsh.stackmarketapp.domain.repository.StockRepository
import com.gouravsh.stackmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    val state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol: String = savedStateHandle.get<String>("symbol") ?: return@launch
            state.copy(isLoading = true)
            val companyInfo = async{ repository.getCompanyInfo(symbol) };
            val intraday = async{  repository.getIntradayInfo(symbol) };

            companyInfo.await().collect { res ->
                when (res) {
                    is Resource.Error -> state.copy(isLoading = false, error = res.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(isLoading = false, company = res.data)
                }
            }
            intraday.await().collect{
                res ->
                when(res){
                    is Resource.Error -> state.copy(isLoading = false, error = res.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(isLoading = false, stockInfo = res.data ?: emptyList()
)
                }
            }


        }
    }

}