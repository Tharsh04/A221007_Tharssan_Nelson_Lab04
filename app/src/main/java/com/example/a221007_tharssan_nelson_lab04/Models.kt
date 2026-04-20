package com.example.a221007_tharssan_nelson_lab04

import androidx.compose.ui.graphics.Color

data class FoodDonation(
    val id: String,
    val donorName: String,
    val donorEmail: String,
    val charityName: String,
    val packageType: String,
    val items: Map<String, Int>,
    val date: String
)

data class CharityCampaign(
    val id: String,
    val name: String,
    val description: String,
    val organizationName: String,
    val mealsShared: Int,
    val imageUrl: String
)

val charityCampaigns = listOf(
    CharityCampaign("1", "Community Food Bank", "Providing essential food supplies to families in need.", "Community Food Bank", 12500, "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?w=400"),
    CharityCampaign("2", "Children's Nutrition", "Ensuring children have access to nutritious meals.", "Children's Welfare Foundation", 8500, "https://images.unsplash.com/photo-1593113598332-cd288d649433?w=400"),
    CharityCampaign("3", "Emergency Relief Fund", "Rapid response to natural disasters.", "Disaster Response Team", 4200, "https://images.unsplash.com/photo-1516733968668-dbdce39c4651?w=400")
)