package com.example.plantproject2024.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message (
    val text: String,
    val isMe: Boolean,
)

//Sample message
val sampleMessage = Message(
    text = "Hello",
    isMe = true
)