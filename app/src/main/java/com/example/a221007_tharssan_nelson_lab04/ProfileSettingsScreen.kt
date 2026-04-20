package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun ProfileSettingsScreen(donorName: String, donorEmail: String, donorMatric: String, onEditProfile: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }

        Column(Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(80.dp).background(Color(0xFFEA580C), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(40.dp))
            }
            Text("My Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileRow(Icons.Default.Person, "Name", donorName)
                ProfileRow(Icons.Default.Email, "Email", donorEmail)
                ProfileRow(Icons.Default.Badge, "Matric", donorMatric)

                Button(onClick = onEditProfile, Modifier.fillMaxWidth().padding(top = 16.dp), colors = ButtonDefaults.buttonColors(Color(0xFFEA580C))) {
                    Text("Edit Profile")
                }
            }
        }
    }
}

@Composable
fun ProfileRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Column(Modifier.padding(start = 12.dp)) {
            Text(label, fontSize = 11.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Medium)
        }
    }
}