package com.stanica.speedoxtream.util

import java.io.InputStream
import java.net.URL

/**
 * List of constants to be used throughout the application's lifecycle.
 * 
 * @author stanica
 * @date  11/04/2021
 */
class NetworkUtils {

    companion object {
        // Connection timeout is 3 seconds
        private const val URLCONNECTION_CONNECTION_TIMEOUT_MS = 3000
        // Read timeout is 10 seconds
        private const val URLCONNECTION_READ_TIMEOUT_MS = 10000

        fun getNetworkInputStream(url: String): InputStream {
            val urlConnection = URL(url).openConnection().apply {
                connectTimeout = URLCONNECTION_CONNECTION_TIMEOUT_MS
                readTimeout = URLCONNECTION_READ_TIMEOUT_MS
            }
            val inputStream = urlConnection.getInputStream()
            return inputStream.buffered()
        }
    }

}