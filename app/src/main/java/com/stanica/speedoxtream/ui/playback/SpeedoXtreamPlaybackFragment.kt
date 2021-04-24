package com.stanica.speedoxtream.ui.playback

import android.content.Context
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.properties.SpeedoXtreamVodPlaybackProperties
import com.stanica.speedoxtream.util.Constants
import com.zaclimon.xipl.properties.VodProperties
import com.zaclimon.xipl.ui.vod.VodPlaybackFragment

/**
 * Fragment responsible for playing a given VOD content.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamPlaybackFragment : VodPlaybackFragment() {

    override fun getProviderName(): String {
        return (getString(R.string.app_name))
    }

    override fun getVodProperties(): VodProperties {
        return (SpeedoXtreamVodPlaybackProperties(requireContext().getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)))
    }
}