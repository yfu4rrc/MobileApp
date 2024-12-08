package com.example.plantproject2024.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val plants: String?,
    val messages: String?

) {

}
//Create sample user
val adminUser = User(1,"admin", "admin@admin", "admin", samplePlant.plantName, sampleMessage.text)