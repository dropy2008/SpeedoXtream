package com.stanica.speedoxtream.ui.vod

import android.content.Context
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.util.Constants

/**
 * Fragment responsible for showing VOD content for SpeedoXtream providers.
 *
 * @author stanica
 * @date  11/04/2021
 */
class VodTvFragment : SpeedoXtreamVodTvSectionFragment() {

    override fun getVodContentApiLink(): String {
        val sharedPreferences = context?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val url = sharedPreferences?.getString(Constants.PROVIDER_URL_PREFERENCE, "")
        val username = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")
        val password = sharedPreferences?.getString(Constants.PASSWORD_PREFERENCE, "")
        return getString(R.string.provider_vod_url, url, username, password)
    }

}