package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(viewModel: DonorViewModel, onNavigate: (String) -> Unit) {
    when {
        viewModel.userType == null -> WelcomeView { viewModel.userType = it }
        viewModel.donorName.isBlank() -> InfoView { n, e, m -> viewModel.saveDonorInfo(n, e, m) }
        else -> DonorHomeView(viewModel, onNavigate)
    }
}

@Composable
fun InfoView(onSave: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var matric by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Tell us about yourself", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 32.dp))
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = matric, onValueChange = { matric = it }, label = { Text("Matric Number") }, modifier = Modifier.fillMaxWidth())
        Button(
            onClick = { if (name.isNotBlank()) onSave(name, email, matric) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFEA580C))
        ) { Text("Continue") }
    }
}

@Composable
fun DonorHomeView(viewModel: DonorViewModel, onNavigate: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(Color(0xFFEA580C), Color(0xFFF97316)))).padding(24.dp)) {
                val firstName = viewModel.donorName.split(" ").firstOrNull() ?: "Donor"
                Text("Hello $firstName!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Matric: ${viewModel.donorMatric}", color = Color.White.copy(0.9f))
                Text(viewModel.donorEmail, color = Color.White.copy(0.7f), fontSize = 12.sp)
            }
            Text("ACTIVE CAMPAIGNS", fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp), fontSize = 14.sp, color = Color.Gray)
        }

        items(charityCampaigns) { campaign ->
            CharityGoalCard(campaign = campaign, onDonate = { onNavigate("donate-food") })
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun CharityGoalCard(campaign: CharityCampaign, onDonate: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(campaign.imageUrl),
                    contentDescription = null,
                    modifier = Modifier.height(160.dp).fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                // Small badge for organization name
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(topEnd = 12.dp),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Text(
                        text = campaign.organizationName,
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(Modifier.padding(16.dp)) {
                Text(campaign.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(4.dp))

                // Short description (Visible Always)
                Text(
                    text = campaign.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )

                // GOAL DESCRIPTION (Visible Only when Expanded)
                if (isExpanded) {
                    Divider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)
                    Text("Our Mission & Goals:", fontWeight = FontWeight.Bold, color = Color(0xFF9A3412), fontSize = 14.sp)
                    Text(
                        text = "We aim to distribute over 500 food packages weekly to B40 families. " +
                                "Your donation directly funds the logistics and procurement of fresh produce " +
                                "and essential dry goods. Together, we can eliminate food insecurity in our local hub.",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { isExpanded = !isExpanded }) {
                        Text(if (isExpanded) "Show Less" else "Read More", color = Color(0xFFEA580C))
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = Color(0xFFEA580C)
                        )
                    }

                    Button(
                        onClick = onDonate,
                        colors = ButtonDefaults.buttonColors(Color(0xFFEA580C)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Donate Now")
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeView(onStart: (String) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.Favorite, null, tint = Color(0xFFEA580C), modifier = Modifier.size(100.dp))
        Text("FeedForward", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9A3412))
        Button(onClick = { onStart("donor") }, Modifier.padding(top = 32.dp), colors = ButtonDefaults.buttonColors(Color(0xFFEA580C))) {
            Text("I want to donate →")
        }
    }
}