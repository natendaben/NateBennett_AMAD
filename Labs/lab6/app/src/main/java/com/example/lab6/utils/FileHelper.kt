package com.example.lab6.utils

import android.content.Context
import android.util.Log

class FileHelper {
    companion object {
        //companion objects are similar to static methods in Java
        //belongs to and is called on the class, not its instances
        fun readText(context: Context, filename: String): String {
            return context.assets.open(filename).use { inputStream ->
                inputStream.bufferedReader().use {
                    it.readText()
                }
            } //using the "use" method opens and closes our data streams for us automatically
        }
    }
}