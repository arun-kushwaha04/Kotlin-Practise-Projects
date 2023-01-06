package com.example.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    lateinit var serviceIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(this, MainService::class.java)
        setContent {
            TimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(startService)
                }
            }
        }
    }
    private val startService:()->Unit = {
        startService(serviceIntent)
    }
}



@Composable
fun MainView(startService: ()->Unit){
    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "00 : 00 : 00",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(Color.White, fontSize = 32.sp, fontWeight = FontWeight(700), textAlign = TextAlign.Center))

        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {startService()}) {
                Text(text = "START", style=TextStyle(Color.White, fontSize = 20.sp ))
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "PAUSE", style=TextStyle(Color.White, fontSize = 20.sp ))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TimerTheme {
//        MainView(() ->)
//    }
//}