package com.example.plantproject2024.db

import android.content.Context
import com.example.plantproject2024.R
import com.example.plantproject2024.db.model.Plants
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PlantRepository(private val context: Context) {

    //Read text from resources
    fun getTextFromResource(resourceId: Int): String {
        return context.resources.openRawResource(resourceId)
            .bufferedReader().
            use { it.readText() }
    }
    //Instead of resources, read assets from file name
    fun getTextFromAssets(fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader().use { it.readText() }
    }
    //
    fun getPlants(filename: String): List<Plants>? {
        //Create moshi adapter
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        //
        val listType = Types.newParameterizedType(List::class.java, Plants::class.java)
        val adapter = moshi.adapter<List<Plants>>(listType)
        return adapter.fromJson(getTextFromResource(resourceId = R.raw.sampleplantdata))
    }
}

