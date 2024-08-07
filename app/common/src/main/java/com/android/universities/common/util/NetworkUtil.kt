package com.android.universities.common.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility class for network-related functions.
 *
 * @param connectivityManager The ConnectivityManager instance used to check the network capabilities.
 */
class NetworkUtil(private val connectivityManager: ConnectivityManager) {

    /**
     * Checks if the device is connected to the internet.
     *
     * This function uses the ConnectivityManager to retrieve the current network capabilities and
     * determines if the device has an active network connection with internet access.
     *
     * @return True if the device is connected to the internet and the connection is validated, false otherwise.
     */
    fun isConnectedToInternet(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities?.let {
            it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false
    }
}