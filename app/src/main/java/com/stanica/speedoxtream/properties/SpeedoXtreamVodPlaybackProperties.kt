package com.stanica.speedoxtream.properties

import android.content.SharedPreferences
import com.stanica.speedoxtream.util.Constants
import com.zaclimon.xipl.properties.VodProperties

/**
 * [VodProperties] concrete implementation for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamVodPlaybackProperties(sharedPreferences: SharedPreferences) : VodProperties {

    private val propertiesPreferences = sharedPreferences

    override fun isVideoFitToScreen(): Boolean {
        return propertiesPreferences.getBoolean(Constants.VIDEO_FIT_SCREEN_PREFERENCE, false)
    }

    override fun isExternalPlayerUsed(): Boolean {
        return propertiesPreferences.getBoolean(Constants.EXTERNAL_PLAYER_PREFERENCE, false)
    }

}