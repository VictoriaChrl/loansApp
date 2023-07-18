package com.example.empty_project.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty_project.domain.usecase.GetLoanByIdUseCase
import com.example.empty_project.presentation.states.LoanDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class LoanDetailsViewModel  @Inject constructor(
    private val getLoanByIdUseCase: GetLoanByIdUseCase
) : ViewModel() {

    private val _state: MutableLiveData<LoanDetailsUiState> =
        MutableLiveData(LoanDetailsUiState.Initial)
    val state: LiveData<LoanDetailsUiState> = _state

    fun getLoan(id: Long) {
        _state.value = LoanDetailsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.postValue(LoanDetailsUiState.Complete(getLoanByIdUseCase(id)))
            }catch (exception: Exception){
                Log.v("excep", exception.toString())
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: Exception){
        when (exception) {
            is SSLHandshakeException,
            is ConnectException,
            is UnknownHostException,
            is NoRouteToHostException -> _state.postValue(LoanDetailsUiState.Error.NoInternet)

            else -> _state.postValue(LoanDetailsUiState.Error.Unknown)
        }
    }
}