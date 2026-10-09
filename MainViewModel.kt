package com.example.myapp

import android.app.Application
import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(application, this)

    private val _uiState = MutableStateFlow<VisionState>(VisionState.Idle)
    val uiState: StateFlow<VisionState> = _uiState

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            tts.setSpeechRate(0.9f)
        }
    }

    fun describeImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = VisionState.Loading
            try {
                // Call ML Kit / Gemini API for image captioning
                val result = "A person standing in front of a desk with a laptop"
                _recognizedText.value = result
                _uiState.value = VisionState.Success(result)
                speak(result)
            } catch (e: Exception) {
                _uiState.value = VisionState.Error(e.message ?: "Failed to describe image")
            }
        }
    }

    fun readTextFromImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = VisionState.Loading
            try {
                // Text Recognition logic
                val result = "Detected text will appear here"
                _recognizedText.value = result
                _uiState.value = VisionState.Success(result)
                speak(result)
            } catch (e: Exception) {
                _uiState.value = VisionState.Error(e.message ?: "OCR failed")
            }
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stopSpeaking() {
        tts.stop()
    }

    override fun onCleared() {
        tts.shutdown()
        super.onCleared()
    }
}

sealed class VisionState {
    object Idle : VisionState()
    object Loading : VisionState()
    data class Success(val text: String) : VisionState()
    data class Error(val message: String) : VisionState()
}
