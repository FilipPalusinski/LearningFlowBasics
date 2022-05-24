package com.example.learningflowbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learningflowbasics.ui.theme.LearningFlowBasicsTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningFlowBasicsTheme {
                val viewModel = viewModel<MainViewModel>()
                val count = viewModel.stateFlow.collectAsState(initial = 0)

                LaunchedEffect(key1 = true) {
                    viewModel.sharedFlow.collect { number ->
                    //i can show something with jetpack compose

                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                   Button(onClick = {viewModel.incrementCounter()}) {
                       Text(text = "Counter: ${count.value}")
                   }
                }

            }
        }
    }
}

