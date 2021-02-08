package com.example.landmarkapp.model.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Queue (
    @PrimaryKey(autoGenerate = true)
    val queueId: Int,
    val queueNum: String = "",
    val queueDate: String = ""
)