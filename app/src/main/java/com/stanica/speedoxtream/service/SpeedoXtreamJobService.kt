package com.stanica.speedoxtream.service

import android.app.job.JobParameters
import android.content.Context
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.properties.SpeedoXtreamChannelProperties
import com.stanica.speedoxtream.util.ActivityUtil
import com.stanica.speedoxtream.util.Constants
import com.zaclimon.xipl.properties.ChannelProperties
import com.zaclimon.xipl.service.ProviderEpgService

/**
 * Custom [ProviderEpgService] adapted for syncing a SpeedoXtream provider content.
 * <p>
 *
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamJobService : ProviderEpgService() {

    private lateinit var serviceProperties: ChannelProperties

    override fun onStartJob(params: JobParameters?): Boolean {
        // Don't really start the job until we have all credentials
        if (!ActivityUtil.areCredentialsEmpty(this)) {
            serviceProperties = SpeedoXtreamChannelProperties(getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE))
            return super.onStartJob(params)
        }
        return false
    }

    override fun getPlaylistUrl(): String {
        val sharedPreferences = getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val url = sharedPreferences?.getString(Constants.PROVIDER_URL_PREFERENCE, "")
        val username = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")
        val password = sharedPreferences?.getString(Constants.PASSWORD_PREFERENCE, "")
        val streamType = sharedPreferences?.getString(Constants.STREAM_TYPE_PREFERENCE, Constants.STREAM_TYPE_HLS)
        return getString(R.string.provider_playlist_url, url, username, password, streamType)
    }

    override fun getEpgUrl(): String {
        val sharedPreferences = getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val url = sharedPreferences?.getString(Constants.PROVIDER_URL_PREFERENCE, "")
        val username = sharedPreferences?.getString(Constants.USERNAME_PREFERENCE, "")
        val password = sharedPreferences?.getString(Constants.PASSWORD_PREFERENCE, "")
        return getString(R.string.provider_epg_url, url, username, password)
    }

    override fun getChannelProperties(): ChannelProperties {
        return serviceProperties
    }

}