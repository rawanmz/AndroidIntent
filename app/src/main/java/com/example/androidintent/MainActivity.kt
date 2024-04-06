package com.example.androidintent

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.androidintent.ui.theme.AndroidIntentTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidIntentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(onClick = {
                        //  openSpecialAppAccessSettings()
                        // sendIntentDataToApp()
                        sendSMS()
                    }) {
                        Text(text = "Open Intent")
                    }
                }
            }
        }
    }

    private fun openActivity2() {
        try {
            val intent = Intent(this, MainActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun setAlarm() {
        try {
//            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
//                putExtra(AlarmClock.EXTRA_MESSAGE,message)
//                putExtra(AlarmClock.EXTRA_HOUR,hour)
//                putExtra(AlarmClock.EXTRA_MINUTES,min)
//            }
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")
            )
            startActivity(intent)
            startActivity(intent)
        } catch (e: Exception) {
            println(e)
        }
    }


    fun openSpecialAppAccessSettings() {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.parse("package:" + "com.example.androidintent")
            startActivity(intent)
        } catch (e: Exception) {
            println(e)
        }
    }


    fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps handle this.
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)

    }

    fun sendSMS() {
      //  val intent1 = Intent().setComponent(ComponentName("com.example.androidapp2024", "com.example.androidapp2024.MainActivity"))
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra("sms_body", "The SMS text")
        intent.setDataAndType(Uri.parse("data from intent app to any sender app"), "text/plain")
        startActivity(intent)

//        val uriSms = Uri.parse("smsto:1234567899")
//        val intentSMS = Intent(Intent.ACTION_SEND, uriSms)
//        intentSMS.putExtra("sms_body", "The SMS text")
//        intentSMS.setDataAndType(uriSms, "text/plain")
//        startActivity(intentSMS)
    }

    private fun sendIntentDataToApp(){
        val intent1 = Intent().setComponent(ComponentName("com.example.androidapp2024", "com.example.androidapp2024.MainActivity"))
        val intent = Intent("com.example.androidintent")//custom action


        intent.putExtra("num1", 2)
        intent.putExtra("num2", 4)
        sendBroadcast(intent)
        startActivity(intent1)
    }
}
