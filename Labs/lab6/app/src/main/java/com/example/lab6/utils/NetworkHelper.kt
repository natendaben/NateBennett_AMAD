package com.example.lab6.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class NetworkHelper {
    companion object{
        @Suppress("DEPRECATION")
        fun networkConnected(app: Application): Boolean{
            val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
    }
}