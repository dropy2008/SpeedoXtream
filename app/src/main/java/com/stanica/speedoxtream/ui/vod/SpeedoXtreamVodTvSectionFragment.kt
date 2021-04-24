package com.stanica.speedoxtream.ui.vod

import android.os.Bundle
import com.stanica.speedoxtream.data.RoomContentPersistence
import com.stanica.speedoxtream.ui.components.cardview.PicassoCardViewImageProcessor
import com.stanica.speedoxtream.ui.playback.SpeedoXtreamPlaybackActivity
import com.zaclimon.xipl.persistence.ContentPersistence
import com.zaclimon.xipl.ui.components.cardview.CardViewImageProcessor
import com.zaclimon.xipl.ui.vod.VodPlaybackActivity
import com.zaclimon.xipl.ui.vod.VodTvSectionFragment

/**
 * Base class in which VOD-like (Video on demand) fragments can base off in order to have a complete
 * list of content based on their provider's catalog.
 *
 * @author stanica
 * @date  11/04/2021
 */
abstract class SpeedoXtreamVodTvSectionFragment : VodTvSectionFragment() {

    private lateinit var fragmentContentPersistence: ContentPersistence

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentContentPersistence = RoomContentPersistence(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun getContentPersistence(): ContentPersistence {
        return fragmentContentPersistence
    }

    override fun getImageProcessor(): CardViewImageProcessor {
        return PicassoCardViewImageProcessor()
    }

    override fun getPlaybackActivity(): Class<out VodPlaybackActivity> {
        return SpeedoXtreamPlaybackActivity::class.java
    }


}