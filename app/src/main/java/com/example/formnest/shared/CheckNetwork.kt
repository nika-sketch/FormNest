package com.example.formnest.shared

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface CheckNetwork {

  fun isConnected(): Boolean

  class Base(private val applicationContext: Context) : CheckNetwork {

    override fun isConnected(): Boolean {
      val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
          ?: return false
      val network = connectivityManager.activeNetwork ?: return false
      val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

      return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
              capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
  }
}
