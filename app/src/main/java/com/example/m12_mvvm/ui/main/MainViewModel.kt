package com.example.m12_mvvm.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

private const val TAG = "MainViewModel"

class MainViewModel() : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Success(""))
    val state: StateFlow<State> = _state.asStateFlow()
    private val _data: Channel<String> = Channel()
    val data: Flow<String> = _data.receiveAsFlow()

    init {
        Log.d(TAG, "init: ")
    }

    fun onButtonClick() {
        Log.d(TAG, "onButtonClick: ")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }

    fun onSingInClick(string: String) {
        viewModelScope.launch {
            _state.value = State.Search
            delay(3000)
            _state.value = State.Data(string)
            _data.send(State.Data(string).toString())
            _state.value = State.Success(data.toString())
        }
    }
}
