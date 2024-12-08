package com.example.plantproject2024.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantproject2024.db.model.Plants

@Dao
interface PlantDAO {

    @Query("SELECT * FROM Plants")
    fun getAllPlants(): List<Plants>

    @Query(value = "SELECT * FROM Plants WHERE id = :id")
    fun getPlantById(id: Int): Plants?

    @Insert
    fun insertPlant(plant: Plants)

}