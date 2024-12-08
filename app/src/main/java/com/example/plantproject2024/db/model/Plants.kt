package com.example.plantproject2024.db.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
data class Plants(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val plantName: String,
    val binomialName: String,
    val commonName: String?,
    val toxicity: Boolean,
    val image: String?,
    val sunExposure: SunExposure,
    val soilType: SoilType,
    val nativeArea: String,
    val description: String?,
    val careLevel: CareLevel,
    val wateringFrequency: WaterFrequency,
)
enum class CareLevel {
    //care levels
    Easy,
    Medium,
    Hard
}
enum class WaterFrequency {
    //water frequency
    Daily,
    Weekly,
    BiWeekly,
    Monthly
}
enum class SunExposure  {
    //sun exposure types
    FULL_SUN,
    PARTIAL_SUN,
    PARTIAL_SHADOW,
    FULL_SHADOW
}

enum class SoilType  {
    //soil types
    Sandy,
    Normal
}

//Sample plant data
val samplePlant = Plants(
    // Sample plant data
    plantName = "Sample Plant",
    binomialName = "Sample Binomial Name",
    commonName = "Sample Common Name",
    toxicity = true,
    image = null,
    sunExposure = SunExposure.FULL_SUN,
    soilType = SoilType.Sandy,
    nativeArea = "Sample Native Area",
    description = "Sample Description",
    careLevel = CareLevel.Easy,
    wateringFrequency = WaterFrequency.Weekly
)