package com.example.m12_mvvm.ui.main

sealed class State {

    object Search : State()
    class Success(string: String) : State()
    data class Data(val string: String?) : State()

}

