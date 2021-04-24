package com.stanica.speedoxtream.ui.settings

import android.app.Activity
import androidx.leanback.widget.ArrayObjectAdapter
import com.zaclimon.xipl.ui.settings.ProviderSettingsTvFragment

/**
 * Fragment handling the Settings from a TV standpoint. Concrete implementation of a
 * [ProviderSettingsTvFragment] for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamSettingsFragment : ProviderSettingsTvFragment() {

    override fun getSettingsElementActivity(): Class<out Activity> {
        return (SpeedoXtreamSettingsElementActivity::class.java)
    }

    override fun getSettingsObjectAdapter(): ArrayObjectAdapter {
        return (SpeedoXtreamSettingsObjectAdapter())
    }

}