// FIXED: Removed ".ui.theme" to match your actual file location shown in the error screenshot
package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay

// Helper data class for the local UI
data class FoodPackageItem(val id: String, val name: String, val description: String, val items: List<String>, val color: Color)

@Composable
fun DonateFoodScreen(
    donorName: String,
    donorEmail: String,
    onDonate: (FoodDonation) -> Unit,
    onBack: () -> Unit
) {
    var selectedPackage by remember { mutableStateOf<String?>(null) }
    var selectedCharity by remember { mutableStateOf("") }
    var itemQuantities by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var showConfirmation by remember { mutableStateOf(false) }
    var donatedToCharity by remember { mutableStateOf("") }

    val foodPackages = listOf(
        FoodPackageItem("emergency", "Emergency Food Package", "Essential items for immediate relief", listOf("Canned goods", "Rice & pasta", "Dried beans", "Bottled water"), Color(0xFFDC2626)),
        FoodPackageItem("basic", "Basic Grocery Package", "Weekly staples for a family", listOf("Fresh vegetables", "Fruits", "Bread", "Milk", "Eggs"), Color(0xFF16A34A)),
        FoodPackageItem("baby", "Baby Food Package", "Nutrition essentials for infants", listOf("Infant formula", "Baby cereal", "Pureed fruits", "Baby snacks"), Color(0xFFDB2777))
    )

    LaunchedEffect(showConfirmation) {
        if (showConfirmation) {
            delay(3000)
            onBack()
        }
    }

    if (showConfirmation) {
        ConfirmationScreen(donatedToCharity = donatedToCharity)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                    Text("Back", color = Color.Gray)
                }
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(60.dp).background(Color(0xFFEA580C), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CardGiftcard, null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Text("Donate Food Packages", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Choose a charity and customize your donation", fontSize = 14.sp, color = Color.Gray)
                }
            }
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Select Charity", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        // This will now resolve correctly once the package header is fixed
                        charityCampaigns.forEach { charity ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedCharity = charity.id }
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedCharity == charity.id,
                                    onClick = { selectedCharity = charity.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFEA580C))
                                )
                                Column {
                                    Text(charity.organizationName, fontWeight = FontWeight.Medium)
                                    Text(charity.name, fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
            item { Text("Choose Package Type", fontWeight = FontWeight.Bold, fontSize = 14.sp) }

            items(foodPackages.size) { index ->
                val pkg = foodPackages[index]
                val isSelected = selectedPackage == pkg.id
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedPackage = pkg.id
                            itemQuantities = pkg.items.associateWith { 1 }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) pkg.color.copy(alpha = 0.1f) else Color.White)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().border(2.dp, if (isSelected) pkg.color else Color.Transparent, RoundedCornerShape(12.dp)).padding(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(pkg.name, fontWeight = FontWeight.Bold)
                                if (isSelected) Icon(Icons.Default.CheckCircle, null, tint = pkg.color, modifier = Modifier.size(20.dp))
                            }
                            Text(pkg.description, fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                }
            }

            item {
                val currentPkg = foodPackages.find { it.id == selectedPackage }
                if (currentPkg != null && selectedCharity.isNotEmpty()) {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Customize Package", fontWeight = FontWeight.Bold)
                            currentPkg.items.forEach { item ->
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(item, fontSize = 14.sp, modifier = Modifier.weight(1f))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = {
                                            val c = itemQuantities[item] ?: 1
                                            if (c > 1) itemQuantities = itemQuantities + (item to c - 1)
                                        }, modifier = Modifier.size(32.dp)) { Icon(Icons.Default.Remove, null, modifier = Modifier.size(16.dp)) }
                                        Text((itemQuantities[item] ?: 1).toString(), modifier = Modifier.width(32.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                                        IconButton(onClick = {
                                            val c = itemQuantities[item] ?: 1
                                            itemQuantities = itemQuantities + (item to c + 1)
                                        }, modifier = Modifier.size(32.dp)) { Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp)) }
                                    }
                                }
                            }
                            Surface(color = Color(0xFFFFF7ED), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                                Text("Total items: ${itemQuantities.values.sum()}", modifier = Modifier.padding(12.dp), fontSize = 13.sp, color = Color(0xFF9A3412), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Button(
                        onClick = {
                            val charity = charityCampaigns.find { it.id == selectedCharity } ?: return@Button
                            donatedToCharity = charity.organizationName
                            onDonate(
                                FoodDonation(
                                    id = java.util.UUID.randomUUID().toString(),
                                    donorName = donorName,
                                    donorEmail = donorEmail,
                                    charityName = charity.organizationName,
                                    packageType = currentPkg.name,
                                    items = itemQuantities,
                                    date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                                )
                            )
                            showConfirmation = true
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFEA580C)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Confirm Donation", modifier = Modifier.padding(8.dp))
                    }
                } else {
                    Surface(color = Color(0xFFFFF7ED), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Text(if (selectedCharity.isEmpty()) "Select a charity first" else "Select a package", modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center, fontSize = 13.sp, color = Color(0xFF9A3412))
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun ConfirmationScreen(donatedToCharity: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(8.dp)) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(80.dp).background(Color(0xFFDCFCE7), CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Check, null, tint = Color(0xFF16A34A), modifier = Modifier.size(40.dp)) }
                Text("Thank You!", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                Text("Your donation has been sent to:", color = Color.Gray)
                Text(donatedToCharity, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEA580C))
                Surface(color = Color(0xFFFFF7ED), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Text("Your contribution will help provide meals to those in need.", modifier = Modifier.padding(16.dp), fontSize = 13.sp, textAlign = TextAlign.Center, color = Color(0xFF78350F))
                }
            }
        }
    }
}