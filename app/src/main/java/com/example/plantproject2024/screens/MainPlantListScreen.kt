package com.example.plantproject2024.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantproject2024.R
import com.example.plantproject2024.ui.theme.PlantProject2024Theme
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.plantproject2024.db.model.CareLevel
import com.example.plantproject2024.db.model.Plants
import com.example.plantproject2024.db.model.SoilType
import com.example.plantproject2024.db.model.SunExposure
import com.example.plantproject2024.db.model.WaterFrequency

@Composable
fun MainPlantListScreen(navController: NavController) {
    val samplePlant = listOf(Plants(
        plantName = "Sample Plant",
        binomialName = "Sample Binomial Name",
        commonName = "Sample Common Name",
        description = "Sample Description",
        nativeArea = "Sample Native Area",
        toxicity = false,
        sunExposure = SunExposure.PARTIAL_SUN,
        soilType = SoilType.Sandy,
        careLevel = CareLevel.Easy,
        wateringFrequency = WaterFrequency.Daily,
        image = null,
        id = 0
    ),
        Plants(
            plantName = "Sample Plant2",
            binomialName = "Sample Binomial Name",
            commonName = "Sample Common Name",
            description = "Second Sample",
            nativeArea = "Sample Native Area",
            toxicity = false,
            sunExposure = SunExposure.PARTIAL_SUN,
            soilType = SoilType.Sandy,
            careLevel = CareLevel.Easy,
            wateringFrequency = WaterFrequency.Daily,
            image = null,
            id = 1
        ))
    //val plantList = listOf(samplePlant)

    Column (modifier = Modifier.padding(all = 18.dp),){

        var searchText by remember { mutableStateOf("") }
        //Add search bar here with function
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search from my plants") },
            modifier = Modifier.fillMaxWidth() // Search bar fill
        )
        Row (modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "All plants")
            //Add sort button here with function
            Spacer(modifier = Modifier.weight(1f)) // 佔用剩餘空間，將按鈕推到右側
            Button(onClick = {},
                enabled = true,
                modifier = Modifier
                    .wrapContentSize()  //Button size flex base on content
            ){ Text("Sort")}
        }
        LazyColumn{
            items(samplePlant){ plant ->
                PlantCard(plant, navController)
            }
        }

    }
}

//Map for example images
val plantImages = mapOf(
    "rose" to R.drawable.plant1,
    "tulip" to R.drawable.plant2,
    "lily" to R.drawable.plant3,
    "cactus" to R.drawable.plant4
)

@Composable
fun PlantCard(plant: Plants, navController: NavController) {
    val imageResource = plantImages[plant.plantName]?:R.drawable.default_plant
    Column (modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()) {
        Card(onClick = { /*TODO*/ }) {
            Row(){
                Image(painter = painterResource(imageResource),
                    contentDescription = "plant",
                    modifier = Modifier
                        //Set size
                        .size(40.dp)
                        //Set shape
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Plant name: ${plant.plantName}")
                    Text(text = "Description: ${plant.description}")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {navController.navigate("PlantCardScreen/${plant.id}")},
                    enabled = true,
                    modifier = Modifier
                        .wrapContentSize()  //Button size flex base on content
                        .clip(CircleShape)
                ){ Text("Edit", fontSize = 12.sp)}
            }

        }

    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun MainPlantListScreenPreview() {
    PlantProject2024Theme {
        MainPlantListScreen(navController = rememberNavController())
    }
}