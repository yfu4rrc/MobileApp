package com.example.plantproject2024.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantproject2024.R
import com.example.plantproject2024.db.model.Plants
import com.example.plantproject2024.db.model.samplePlant

@Composable
fun PlantCardScreen(plant: Plants) {
    LazyColumn {
        item {
            Image(
                painter = painterResource(id = R.drawable.default_plant),
                contentDescription = null
            )
        }
        item(2) {
            Text(text = plant.id.toString())
            Text(text = plant.toxicity.toString())
            Text(text = plant.sunExposure.toString())
            Text(text = plant.soilType.toString())
            Text(text = plant.careLevel.toString())
        }
        item(3) {
            Text(text = plant.plantName)
            Text(text = plant.binomialName)
            Text(text = plant.commonName ?: "")
            Text(text = plant.description ?: "")
            Text(text = plant.nativeArea)
        }
        item(4) {
            Button(onClick = { /* Handle edit button click */ }) {
                Text(text = "Edit")
            }
        }
    }
}
@Composable
fun PlantSchedule() {
    Card {

    }
}


@Preview(showBackground = true)
@Composable
fun PlantCardPreview() {
    PlantCardScreen(plant = samplePlant)
}