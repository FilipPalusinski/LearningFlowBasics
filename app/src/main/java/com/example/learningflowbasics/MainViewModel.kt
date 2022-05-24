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

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()
    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()



    init {
//        countOperator()
//        reduceOperator()
//       foldOperator()
//        flatteningOperator()
//        bufferOperator()
//        conflateOperator()
//        collectLatestOperator()
        squareNumber(3)

        viewModelScope.launch {
                sharedFlow.collect(){
                delay(2000L)
                println("FIRST FLOW: The received number is $it")
            }
        }

        viewModelScope.launch {
            sharedFlow.collect(){
                delay(3000L)
                println("SECOND FLOW: The received number is $it")
            }
        }
    }

    fun squareNumber(number: Int){
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter() {
        _stateFlow.value += 1
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

    private fun bufferOperator() {
        val flow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main dish")
            delay(100L)
            emit("Dessert")
        }

        viewModelScope.launch {
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .buffer()
                .collect {
                    println("FLOW: Now eating $it")
                    delay(1500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }

    private fun conflateOperator() {
        val flow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main dish")
            delay(100L)
            emit("Dessert")
        }

        viewModelScope.launch {
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .conflate()
                .collect {
                    println("FLOW: Now eating $it")
                    delay(1500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }

    private fun collectLatestOperator() {
        val flow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main dish")
            delay(100L)
            emit("Dessert")
        }

        viewModelScope.launch {
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .collectLatest {
                    println("FLOW: Now eating $it")
                    delay(1500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }

}