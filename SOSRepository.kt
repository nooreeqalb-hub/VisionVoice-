package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.telephony.SmsManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SOSRepository(private val context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val prefs: SharedPreferences = context.getSharedPreferences("vision_voice_prefs", Context.MODE_PRIVATE)

    data class EmergencyContact(val name: String, val phone: String)

    fun getEmergencyContacts(): List<EmergencyContact> {
        val contacts = mutableListOf<EmergencyContact>()
        val set = prefs.getStringSet("emergency_contacts", emptySet())?: emptySet()
        set.forEach {
            val parts = it.split("|")
            if (parts.size == 2) contacts.add(EmergencyContact(parts[0], parts[1]))
        }
        return contacts
    }

    suspend fun triggerSOS(location: Location?) {
        val contacts = getEmergencyContacts()
        if (contacts.isEmpty()) throw Exception("No emergency contacts added")

        val lat = location?.latitude?: prefs.getFloat("last_lat", 0f).toDouble()
        val lng = location?.longitude?: prefs.getFloat("last_lng", 0f).toDouble()
        val mapLink = if (lat!= 0.0) "https://maps.google.com/?q=$lat,$lng" else "Location not available"
        val message = "EMERGENCY! I need help. My location: $mapLink - Sent via VisionVoice"

        // 1. Send SMS
        try {
            val smsManager = SmsManager.getDefault()
            contacts.forEach { contact ->
                smsManager.sendTextMessage(contact.phone, null, message, null, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 2. Save to Firestore History
        try {
            val record = hashMapOf(
                "timestamp" to System.currentTimeMillis(),
                "latitude" to lat,
                "longitude" to lng,
                "contacts" to contacts.map { it.phone },
                "message" to message
            )
            firestore.collection("sos_history").add(record).await()
            // Save locally too
            prefs.edit().putLong("last_sos_time", System.currentTimeMillis()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveContact(name: String, phone: String) {
        val current = prefs.getStringSet("emergency_contacts", mutableSetOf())?.toMutableSet()?: mutableSetOf()
        current.add("$name|$phone")
        prefs.edit().putStringSet("emergency_contacts", current).apply()
    }
}
