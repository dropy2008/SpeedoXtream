package com.stanica.speedoxtream.ui.settings.userinfo

import android.os.AsyncTask
import com.stanica.speedoxtream.util.NetworkUtils
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Async class that will retrieve a given user's account information.
 *
 * @author stanica
 * @date  11/04/2021
 */
class AsyncRetrieveUserInfo(userEndpoint: String, view: UserInfoView) : AsyncTask<Void, Void, Boolean>() {

    companion object {
        private const val USER_INFO_JSON_OBJECT = "user_info"
        private const val STATUS_JSON_OBJECT = "status"
        private const val EXPIRATION_DATE_JSON_OBJECT = "exp_date"
        private const val TRIAL_ACCOUNT_JSON_OBJECT = "is_trial"
        private const val MAX_CONNECTION_JSON_OBJECT = "max_connections"
    }

    private val apiEndpoint = userEndpoint
    private val userInfoView = view
    private lateinit var status: String
    private lateinit var expirationDate: Date
    private var isTrial: Boolean = false
    private var maxConnections: Int = 0

    override fun doInBackground(vararg p0: Void?): Boolean {

        val inputStream = NetworkUtils.getNetworkInputStream(apiEndpoint)
        val reader = inputStream.bufferedReader()
        val jsonString = reader.readLine()
        inputStream.close()

        if (!jsonString.isNullOrEmpty()) {
            val baseJsonObject = JSONObject(jsonString)
            val realJsonObject = baseJsonObject.getJSONObject(USER_INFO_JSON_OBJECT)
            val calendar = Calendar.getInstance()

            status = realJsonObject.getString(STATUS_JSON_OBJECT)

            /*
             The returned date is in unix time, which is in seconds since the January 1st 1970.

             The time zone as set in the original Ace TV application was set according to
             GMT +2 (Central european/CET as defined now) let's set it according to the user's
             actual time zone.
             */

            try {
                val expirationDateSeconds = realJsonObject.getLong(EXPIRATION_DATE_JSON_OBJECT)
                calendar.timeInMillis = (expirationDateSeconds * 1000)
                expirationDate = calendar.time

                isTrial = (realJsonObject.getInt(TRIAL_ACCOUNT_JSON_OBJECT) == 1)
                maxConnections = realJsonObject.getInt(MAX_CONNECTION_JSON_OBJECT)
                return true
            } catch (e: Exception) {
                // Do nothing since it will be returned outside the check.
            }
        }
        return false
    }

    override fun onPostExecute(result: Boolean) {
        when (result) {
            true -> userInfoView.onConnectionSuccess(status, expirationDate, isTrial, maxConnections)
            false -> userInfoView.onConnectionFailed()
        }
    }
}