package com.example.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    lateinit var serviceIntent: Intent
    private var time = mutableStateOf(0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(updateTime, IntentFilter(MainService.UPDATED_TIME))
        serviceIntent = Intent(this, MainService::class.java)
        serviceIntent.putExtra(MainService.CURRENT_TIME,time.value)
        setContent {
            TimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(time,MainService.isServiceRunning,MainService.isServicePause,startStopService,pauseResumeService)
                }
            }
        }
    }
    private val startStopService:()->Unit = {
        Log.i(MainService.TAG,"Starting Service")
        if(!MainService.isServiceRunning.value){
            startService()
        }else{
            stopService()
        }
    }
    private val startService:()->Unit = {
        startService(serviceIntent)
        MainService.isServiceRunning.value = true
    }
    private val stopService:()->Unit={
        stopService(serviceIntent)
        time.value = 0.0
        MainService.isServiceRunning.value = false;
    }
    private val pauseResumeService:() -> Unit = {
        if(MainService.isServicePause.value){
            //true
            MainService.isServicePause.value = false
        }else{
            MainService.isServicePause.value = true
        }
    }
    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            time.value = intent.getDoubleExtra(MainService.CURRENT_TIME,0.0)
            Log.i(MainService.TAG,time.toString())
        }
    }
}

@Composable
fun MainView(time: MutableState<Double>,isServiceRunning: MutableState<Boolean>,isServicePause: MutableState<Boolean>,startStopService: ()->Unit,pauseResumeService: ()->Unit){
    fun formatTimeString(time: Double) : String{
        var timeString:String;
        val value = time.toInt();
        if(value >= 86400){
            timeString = (value / 86400).toString().padStart(2,'0') + " : "
        }else{
            timeString = ""
        }
        timeString += (value % 86400 / 3600).toString().padStart(2,'0') + " : "
        timeString += (value % 3600 / 60).toString().padStart(2,'0') + " : "
        timeString += (value % 3600 % 60).toString().padStart(2,'0')
        return timeString
    }
    Column(modifier = Modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formatTimeString(time.value),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(Color.White, fontSize = 32.sp, fontWeight = FontWeight(700), textAlign = TextAlign.Center))

        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {startStopService()}) {
                if(isServiceRunning.value){
                    Text(text = "STOP", style=TextStyle(Color.White, fontSize = 20.sp ))
                }else{
                    Text(text = "START", style=TextStyle(Color.White, fontSize = 20.sp ))
                }
            }
            Button(onClick = {pauseResumeService() }) {
                if(isServicePause.value){
                    Text(text = "RESUME", style = TextStyle(Color.White, fontSize = 20.sp))
                }else {
                    Text(text = "PAUSE", style = TextStyle(Color.White, fontSize = 20.sp))
                }
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