package com.stanica.speedoxtream.ui.playback

import com.stanica.speedoxtream.R
import com.zaclimon.xipl.ui.vod.VodPlaybackActivity
import com.zaclimon.xipl.ui.vod.VodPlaybackFragment

/**
 * Activity responsible of playing a given VOD content. It is a concrete implementation of
 * [VodPlaybackActivity] for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamPlaybackActivity : VodPlaybackActivity() {

    override fun getThemeId(): Int {
        return R.style.TvTheme
    }

    override fun getVodPlaybackFragment(): VodPlaybackFragment {
        return SpeedoXtreamPlaybackFragment()
    }
}