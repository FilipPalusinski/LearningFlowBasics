package com.example.learningflowbasics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while(currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
//        countOperator()
//        reduceOperator()
//       foldOperator()
        flatteningOperator()
    }

    private fun countOperator() {
        viewModelScope.launch {
            val count = countDownFlow
                .count {
                    it % 2 == 0
                }
            println("the count is $count")
        }
    }

    private fun reduceOperator() {
        viewModelScope.launch {
            val reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    accumulator + value
                }
            println("the count is $reduceResult")
        }
    }

    private fun foldOperator() {
        viewModelScope.launch {
            val reduceResult = countDownFlow
                .fold(100) { accumulator, value ->
                    accumulator + value
                }
            println("the count is $reduceResult")
        }
    }
    private fun flatteningOperator() {
        val flow1 = flow {
            emit(1)
            delay(500L)
            emit(2)
        }

        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect { value ->
                println("The value is $value")
            }

        }
    }
}