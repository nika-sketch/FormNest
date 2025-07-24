package com.example.formnest.shared

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface ConnectivityObserver {

  val isConnectedFlow: Flow<Boolean>

  fun isConnected(): Boolean

  class AndroidConnectivityObserver(context: Context) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!

    override val isConnectedFlow: Flow<Boolean>
      get() = callbackFlow {
        val callback = object : NetworkCallback() {
          override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
          ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val connected = networkCapabilities.hasCapability(
              NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
            trySend(connected)
          }

          override fun onUnavailable() {
            super.onUnavailable()
            trySend(false)
          }

          override fun onLost(network: Network) {
            super.onLost(network)
            trySend(false)
          }

          override fun onAvailable(network: Network) {
            super.onAvailable(network)
            trySend(true)
          }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
          connectivityManager.unregisterNetworkCallback(callback)
        }
      }

    override fun isConnected(): Boolean {
      val network = connectivityManager.activeNetwork ?: return false
      val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
      return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
  }
}
