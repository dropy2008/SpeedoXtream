package com.stanica.speedoxtream.ui.main

import android.os.Bundle
import androidx.leanback.app.RowsSupportFragment
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.search.SpeedoXtreamProviderSearchActivity
import com.stanica.speedoxtream.ui.settings.SpeedoXtreamSettingsFragment
import com.stanica.speedoxtream.ui.vod.CatchupTvFragment
import com.stanica.speedoxtream.ui.vod.SeriesTvFragment
import com.stanica.speedoxtream.ui.vod.VodTvFragment
import com.zaclimon.xipl.ui.main.ProviderTvFragment
import com.zaclimon.xipl.ui.search.ProviderSearchActivity

/**
 * Main fragment used to display the contents inside the application
 *
 * @author stanica
 * @date  11/04/2021
 */
class MainTvFragment : ProviderTvFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        badgeDrawable = activity!!.getDrawable(R.drawable.speedoxtream_badge_white)

    }

    override fun getAppName(): String {
        return (getString(R.string.app_name))
    }

    override fun getFragmentMap(): MutableMap<String, RowsSupportFragment> {
        // A LinkedHashMap is used in this case since it retains the position of each inserted object.
        val sections = LinkedHashMap<String, RowsSupportFragment>()

        sections[getString(R.string.vod_text)] = VodTvFragment()
        sections[getString(R.string.series_title)] = SeriesTvFragment()
        sections[getString(R.string.catchup_title)] = CatchupTvFragment()
        sections[getString(R.string.settings_text)] = SpeedoXtreamSettingsFragment()
        return (sections)
    }

    override fun getSearchActivity(): Class<out ProviderSearchActivity> {
        return (SpeedoXtreamProviderSearchActivity::class.java)
    }
}