package com.example.plantproject2024.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantproject2024.R
import com.example.plantproject2024.db.model.Plants
import com.example.plantproject2024.db.model.samplePlant
import com.example.plantproject2024.ui.theme.PlantProject2024Theme


@Composable
fun MainTodoScreen() {
    //Todo calendar
    Column {
        Text(text = "Todo calendar")
        PlantCardWidget(plant = samplePlant)
        PlantSchedule()
    }


}
@Composable
fun PlantCardWidget(plant: Plants) {
    Row {
        Image(painter = painterResource(id = R.drawable.default_plant), contentDescription = "Plant Image")
        Column {
            Text(text = "Plant Name: ${plant.plantName}")
            Text(text = "next watering: ${plant.wateringFrequency}")
        }


    }
}


@Preview(showBackground = true, apiLevel = 34)
@Composable
fun MainTodoScreenPreview() {
    PlantProject2024Theme {
        MainTodoScreen()
    }
}