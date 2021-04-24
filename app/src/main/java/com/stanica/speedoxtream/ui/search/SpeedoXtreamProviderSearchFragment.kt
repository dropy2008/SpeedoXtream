package com.stanica.speedoxtream.ui.search

import android.os.Bundle
import com.stanica.speedoxtream.data.RoomContentPersistence
import com.stanica.speedoxtream.ui.components.cardview.PicassoCardViewImageProcessor
import com.stanica.speedoxtream.ui.playback.SpeedoXtreamPlaybackActivity
import com.zaclimon.xipl.persistence.ContentPersistence
import com.zaclimon.xipl.ui.components.cardview.CardViewImageProcessor
import com.zaclimon.xipl.ui.search.ProviderSearchFragment
import com.zaclimon.xipl.ui.vod.VodPlaybackActivity

/**
 * Concrete implementation of a [ProviderSearchFragment] for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 * Creation date: 16/08/17
 */
class SpeedoXtreamProviderSearchFragment : ProviderSearchFragment() {

    private lateinit var searchContentPersistence: ContentPersistence
    private lateinit var searchCardViewImageProcessor: CardViewImageProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        searchContentPersistence = RoomContentPersistence(requireContext())
        searchCardViewImageProcessor = PicassoCardViewImageProcessor()
        super.onCreate(savedInstanceState)
    }

    override fun getCardViewImageProcessor(): CardViewImageProcessor {
        return searchCardViewImageProcessor
    }

    override fun getContentPersistence(): ContentPersistence {
        return searchContentPersistence
    }

    override fun getPlaybackActivity(): Class<out VodPlaybackActivity> {
        return SpeedoXtreamPlaybackActivity::class.java
    }
}