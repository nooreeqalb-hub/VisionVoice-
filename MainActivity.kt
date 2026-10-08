package com.example.myapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
        
        setContent {
            var currentPage by remember { mutableStateOf("home") }
            
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when(currentPage) {
                        "home" -> HomeScreen({ page -> currentPage = page }, tts)
                        "ocr" -> SimplePage("Text Padho - OCR", "Camera se text padha jayega", { currentPage = "home" }, tts)
                        "voice" -> SimplePage("Voice Assistant", "Bolo - Time, Battery, Location", { currentPage = "home" }, tts)
                        "sos" -> SimplePage("SOS Emergency", "Emergency SMS jayega family ko", { currentPage = "home" }, tts)
                        "location" -> SimplePage("Meri Jagah", "Aapki current location", { currentPage = "home" }, tts)
                        "battery" -> SimplePage("Battery Time", "Battery aur Time bolega", { currentPage = "home" }, tts)
                        "object" -> SimplePage("Object Detection", "Samne kya hai batayega", { currentPage = "home" }, tts)
                        "currency" -> SimplePage("Note Pehchano", "Rupye ka note batayega", { currentPage = "home" }, tts)
                        "history" -> SimplePage("History", "Pehle padhe hue text", { currentPage = "home" }, tts)
                        "settings" -> SimplePage("Settings", "Awaz Speed Setting", { currentPage = "home" }, tts)
                        "help" -> SimplePage("Help Guide", "App kaise use kare", { currentPage = "home" }, tts)
                        "about" -> SimplePage("About Creator", "Noor - VisionVoice Creator", { currentPage = "home" }, tts)
                    }
                }
            }
        }
    }
    
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            tts?.language = Locale("hi", "IN")
            tts?.speak("Vision Voice me aapka swagat hai", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}

@Composable
fun HomeScreen(onNavigate: (String) -> Unit, tts: TextToSpeech?) {
    val pages = listOf(
        "ocr" to "1. Text Padho - OCR",
        "voice" to "2. Voice Assistant Bolo",
        "sos" to "3. SOS Emergency",
        "location" to "4. Meri Jagah Kaha Hai",
        "battery" to "5. Battery Aur Time",
        "object" to "6. Samne Kya Hai",
        "currency" to "7. Note Pehchano",
        "history" to "8. History Dekho",
        "settings" to "9. Settings",
        "help" to "10. Help Guide",
        "about" to "11. Creator Ke Baare Me",
        "home" to "12. Home - Dashboard"
    )
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("VisionVoice - 12 Pages", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
        pages.forEach { (id, title) ->
            Button(
                onClick = { 
                    tts?.speak(title, TextToSpeech.QUEUE_FLUSH, null, null)
                    if(id != "home") onNavigate(id) 
                },
                modifier = Modifier.fillMaxWidth().padding(4.dp).height(55.dp)
            ) {
                Text(title, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SimplePage(title: String, desc: String, onBack: () -> Unit, tts: TextToSpeech?) {
    LaunchedEffect(Unit) { tts?.speak("$title page khula. $desc", TextToSpeech.QUEUE_FLUSH, null, null) }
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("Wapas Home Jao") }
        Spacer(modifier = Modifier.height(20.dp))
        Text(title, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(desc, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Ye feature jald hi pura kaam karega. Abhi ye accessible demo hai.", fontSize = 16.sp)
    }
}                  
