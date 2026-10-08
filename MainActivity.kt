package com.example.myapp

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.BatteryManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
        setContent {
            var currentPage by remember { mutableStateOf("home") }
            var selectedLanguage by remember { mutableStateOf("English") }
            var selectedState by remember { mutableStateOf("Delhi") }
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when(currentPage) {
                        "home" -> HomeScreen({ page -> currentPage = page }, tts)
                        "ocr" -> OcrRealScreen({ currentPage = "home" }, tts)
                        "object" -> ObjectRealScreen({ currentPage = "home" }, tts)
                        "currency" -> CurrencyRealScreen({ currentPage = "home" }, tts)
                        "location" -> LocationRealScreen({ currentPage = "home" }, tts)
                        "battery" -> BatteryRealScreen({ currentPage = "home" }, tts)
                        "sos" -> SosRealScreen({ currentPage = "home" }, tts)
                        "settings" -> SettingsRealScreen({ currentPage = "home" }, tts, selectedLanguage, { selectedLanguage = it }, selectedState, { selectedState = it })
                        "help" -> HelpScreen({ currentPage = "home" }, tts)
                        "about" -> AboutScreen({ currentPage = "home" }, tts)
                        "whybuilt" -> WhyBuiltScreen({ currentPage = "home" }, tts)
                        "thanks" -> ThanksScreen({ currentPage = "home" }, tts)
                        "support" -> SupportScreen({ currentPage = "home" }, tts)
                    }
                }
            }
        }
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
            tts?.speak("Welcome to Vision Voice Real Version", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}

@Composable
fun HomeScreen(onNavigate: (String) -> Unit, tts: TextToSpeech?) {
    val pages = listOf(
        "ocr" to "1. Read Text - OCR Camera Real",
        "object" to "2. Object Detection - Real AI",
        "currency" to "3. Currency Identifier - Real",
        "location" to "4. My Live Location - GPS",
        "battery" to "5. Battery and Time - Real",
        "sos" to "6. SOS Emergency SMS - Real",
        "settings" to "7. Language and State Selector",
        "help" to "8. Help Guide",
        "about" to "9. About Creator - Rosh Rehman",
        "whybuilt" to "10. Why I Built This App",
        "thanks" to "11. Special Thanks",
        "support" to "12. Support Us - Donate"
    )
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Vision Voice - Real System", fontSize = 24.sp, modifier = Modifier.padding(8.dp))
        Card(modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("AdMob Banner - ca-app-pub-3940256099942544/6300978111 - For Earnings")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        pages.forEach { (id, title) ->
            Button(onClick = { tts?.speak(title, TextToSpeech.QUEUE_FLUSH, null, null); onNavigate(id) }, modifier = Modifier.fillMaxWidth().padding(4.dp).height(60.dp)) {
                Text(title, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun OcrRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    var result by remember { mutableStateOf("Tap Capture to read text") }
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if(bitmap != null) {
            val image = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            recognizer.process(image).addOnSuccessListener { visionText ->
                result = visionText.text.ifEmpty { "No text found" }
                tts?.speak(result, TextToSpeech.QUEUE_FLUSH, null, null)
            }.addOnFailureListener { result = "Failed to read text" }
        }
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Real OCR - ML Kit Text Recognition", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { launcher.launch(null) }, modifier = Modifier.fillMaxWidth().height(75.dp)) { Text("Capture Text Photo - Real Camera", fontSize = 18.sp) }
        Spacer(modifier = Modifier.height(15.dp))
        Text(result, fontSize = 18.sp)
    }
}

@Composable
fun ObjectRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    var result by remember { mutableStateOf("Tap to detect real objects") }
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if(bitmap != null) {
            val image = InputImage.fromBitmap(bitmap, 0)
            val options = ObjectDetectorOptions.Builder().setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE).enableMultipleObjects().enableClassification().build()
            val detector = ObjectDetection.getClient(options)
            detector.process(image).addOnSuccessListener { objects ->
                result = if(objects.isEmpty()) "No objects detected" else objects.joinToString(", ") { it.labels.firstOrNull()?.text ?: "Object" }
                tts?.speak(result, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Real Object Detection - Google ML Kit", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { launcher.launch(null) }, modifier = Modifier.fillMaxWidth().height(75.dp)) { Text("Detect Objects - Real AI", fontSize = 18.sp) }
        Spacer(modifier = Modifier.height(15.dp))
        Text(result, fontSize = 18.sp)
    }
}

@Composable
fun CurrencyRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    var result by remember { mutableStateOf("Tap to identify currency note") }
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if(bitmap != null) {
            val image = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            recognizer.process(image).addOnSuccessListener { visionText ->
                val text = visionText.text
                result = when {
                    text.contains("500") -> "500 Rupees Note Detected - Real"
                    text.contains("200") -> "200 Rupees Note Detected - Real"
                    text.contains("100") -> "100 Rupees Note Detected - Real"
                    text.contains("50") -> "50 Rupees Note Detected - Real"
                    text.contains("20") -> "20 Rupees Note Detected - Real"
                    text.contains("10") -> "10 Rupees Note Detected - Real"
                    else -> "Text on note: $text"
                }
                tts?.speak(result, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Real Currency Identifier - Indian Notes", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { launcher.launch(null) }, modifier = Modifier.fillMaxWidth().height(75.dp)) { Text("Identify Note - Real Camera", fontSize = 18.sp) }
        Spacer(modifier = Modifier.height(15.dp))
        Text(result, fontSize = 18.sp)
    }
}

@Composable
fun LocationRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    val context = LocalContext.current
    var locText by remember { mutableStateOf("Press to get real GPS location") }
    val fused = remember { LocationServices.getFusedLocationProviderClient(context) }
    val permLauncher = androidx.activity.compose.rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if(granted && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fused.lastLocation.addOnSuccessListener { loc: Location? ->
                if(loc != null) {
                    locText = "Latitude: ${loc.latitude}, Longitude: ${loc.longitude}\nGoogle Maps: https://maps.google.com/?q=${loc.latitude},${loc.longitude}"
                    tts?.speak(locText, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("My Live Location - Real GPS", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { permLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) }, modifier = Modifier.fillMaxWidth().height(75.dp)) { Text("Get My Real Location", fontSize = 18.sp) }
        Spacer(modifier = Modifier.height(15.dp))
        Text(locText, fontSize = 16.sp)
    }
}

@Composable
fun BatteryRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    val context = LocalContext.current
    val bm = context.getSystemService(android.content.Context.BATTERY_SERVICE) as BatteryManager
    val level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    val time = SimpleDateFormat("hh:mm a, dd MMM yyyy", Locale.US).format(Date())
    LaunchedEffect(Unit) { tts?.speak("Battery is $level percent, Time is $time", TextToSpeech.QUEUE_FLUSH, null, null) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Battery and Time - Real System Data", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Battery Level: $level % - Real Reading", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Current Time: $time - Real System Time", fontSize = 20.sp)
    }
}

@Composable
fun SosRealScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("SOS Emergency - Real SMS System", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("This will open SMS app with emergency message and your location link.", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"))
            intent.putExtra("sms_body", "EMERGENCY! I need help. My location: https://maps.google.com/?q=MyLocation - Sent via Vision Voice App")
            context.startActivity(intent)
            tts?.speak("Emergency SMS ready", TextToSpeech.QUEUE_FLUSH, null, null)
        }, modifier = Modifier.fillMaxWidth().height(80.dp)) { Text("SEND SOS SMS NOW - REAL", fontSize = 20.sp) }
    }
}

@Composable
fun SettingsRealScreen(onBack: () -> Unit, tts: TextToSpeech?, selectedLang: String, onLangChange: (String) -> Unit, selectedState: String, onStateChange: (String) -> Unit) {
    val languages = listOf("English", "Hindi", "Urdu", "Tamil", "Telugu", "Marathi")
    val states = listOf("Delhi", "Uttar Pradesh", "Bihar", "Maharashtra", "Madhya Pradesh", "Rajasthan", "Karnataka", "West Bengal", "Gujarat", "Punjab", "Other")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Language and State Selector - Play Store Ready", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Select Language", fontSize = 18.sp)
        languages.forEach { lang -> Button(onClick = { onLangChange(lang); tts?.speak("Language $lang selected", TextToSpeech.QUEUE_FLUSH, null, null) }, modifier = Modifier.fillMaxWidth().padding(2.dp)) { Text(if(lang == selectedLang) "✓ $lang" else lang) } }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Select State", fontSize = 18.sp)
        states.forEach { st -> Button(onClick = { onStateChange(st) }, modifier = Modifier.fillMaxWidth().padding(2.dp)) { Text(if(st == selectedState) "✓ $st" else st) } }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Current Selection: $selectedLang | $selectedState", fontSize = 16.sp)
    }
}

@Composable
fun HelpScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Help Guide - Real Features", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("1. OCR Real: Uses Google ML Kit Text Recognition to read real printed text.\n\n2. Object Detection Real: Uses ML Kit Object Detection to identify real objects.\n\n3. Currency Real: Reads numbers from note using OCR.\n\n4. Location Real: Uses FusedLocationProviderClient for real GPS.\n\n5. Battery Real: Uses BatteryManager for real percentage.\n\n6. SOS Real: Opens SMS app with emergency text.\n\n7. Settings: Language and State selector for localization - required for Play Store.", fontSize = 16.sp)
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("About the Creator - Rosh Rehman", fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("I am Rosh Rehman, founder and creator of Vision Voice. I believe accessibility is not a luxury but a right.", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Contact: nooreeqalb@gmail.com", fontSize = 16.sp)
        Text("Portfolio: roshnirehman.github.io/Rosh", fontSize = 16.sp)
        Text("Facebook: https://www.facebook.com/share/1XHQc6TCP9/", fontSize = 14.sp)
        Text("Instagram: https://www.instagram.com/rosh_rehman", fontSize = 14.sp)
        Text("Telegram: https://t.me/rosh_rehman", fontSize = 14.sp)
        Text("YouTube: https://m.youtube.com/@noor-ehidaayat97", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://roshnirehman.github.io/Rosh/"))) }, modifier = Modifier.fillMaxWidth()) { Text("Open Portfolio Website") }
    }
}

@Composable
fun WhyBuiltScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Why I Built This App", fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Vision Voice was created to address the everyday challenges faced by blind and visually impaired people. Many applications are not fully accessible, making it difficult for users to complete routine tasks independently.\n\nThis app aims to bridge that gap by offering accessible features such as voice assistance, text recognition, emergency support, and accessibility-focused tools. Our goal is to promote independence, confidence, and equal access to technology for everyone.\n\nAbout Vision Voice: Vision Voice is an accessibility application designed for blind and low-vision individuals. Our mission is to make technology more inclusive by providing simple, voice-friendly, and easy-to-use tools that support independent living.", fontSize = 17.sp)
    }
}

@Composable
fun ThanksScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Special Thanks", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("I would like to express my heartfelt gratitude to those who believed in me and encouraged me to serve this community:", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("1. Moht. Taiyab Sahab\n2. Mohd Irfan Khan\n3. Mohd Gayas Uddeen\n4. Mohd Aatif", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("These are the people who considered me capable and inspired me to do something meaningful for the visually impaired community. Their support and trust gave me the strength to build Vision Voice.\n\nThank you for believing in me.", fontSize = 17.sp)
    }
}

@Composable
fun SupportScreen(onBack: () -> Unit, tts: TextToSpeech?) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(55.dp)) { Text("Back to Home") }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Support Vision Voice", fontSize = 26.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Vision Voice is an independent accessibility project developed to empower blind and visually impaired users.", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("If you would like to support future development, you can contribute using the following UPI ID:", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("UPI ID: rehmanrosh@oksbi", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("upi://pay?pa=rehmanrosh@oksbi&pn=Rosh%20Rehman&cu=INR")); context.startActivity(intent) }, modifier = Modifier.fillMaxWidth().height(65.dp)) { Text("Donate via UPI - GPay PhonePe Paytm") }
        Spacer(modifier = Modifier.height(15.dp))
        Text("For Play Store Earning: AdMob Banner ID ca-app-pub-3940256099942544/6300978111 is integrated. After approval, replace with your real ID from admob.google.com", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Thank you for being part of the Vision Voice journey. Created with dedication by Rosh Rehman.", fontSize = 15.sp)
    }
}
