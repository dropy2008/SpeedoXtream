package com.stanica.speedoxtream.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.fragment.app.FragmentActivity
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.auth.AuthActivityTv
import com.zaclimon.xipl.ui.settings.ProviderSettingsObjectAdapter
import com.zaclimon.xipl.util.ActivityUtil

/**
 * Implementation of [ProviderSettingsObjectAdapter] for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
class SpeedoXtreamSettingsElementActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityUtil.isTvMode(this)) {
            setTheme(R.style.TvTheme)
        }

        val extras = intent.extras

        if (savedInstanceState == null && extras != null) {
            // Since the extras contain the id's, we shouldn't have any problems comparing them.
            val stringId = extras.getInt(ProviderSettingsObjectAdapter.BUNDLE_SETTINGS_NAME_ID)
            var fragment: GuidedStepSupportFragment? = null

            when(stringId) {
                R.string.stream_type -> fragment = StreamTypeGuidedFragment()
                R.string.channel_logo_title, R.string.channel_logo_title_short -> fragment = ChannelLogoGuidedFragment()
                R.string.force_epg_sync_title, R.string.force_epg_sync_title_short -> fragment = EpgForceSyncGuidedFragment()
                R.string.force_video_fit_title, R.string.force_video_fit_title_short -> fragment = ForceFitScreenGuidedFragment()
                R.string.channel_group_title -> fragment = ChannelGroupGuidedFragment()
                R.string.channel_genre_title -> fragment = ChannelGenreGuidedFragment()
                R.string.epg_offset_title, R.string.epg_offset_title_short -> fragment = TvCatchupEpgOffsetGuidedFragment()
                R.string.external_player_title, R.string.external_player_title_short -> fragment = VodExternalPlayerGuidedFragment()
                R.string.clear_cache_title, R.string.clear_cache_title_short -> fragment = ClearCacheGuidedFragment()
                R.string.about_text -> fragment = AboutGuidedFragment()
            }

            if (fragment != null) {
                GuidedStepSupportFragment.addAsRoot(this, fragment, android.R.id.content)
            } else {
                // We're in the change account fragment situation
                val intent = Intent(this, AuthActivityTv::class.java)
                startActivityForResult(intent, 0)
                finish()
            }
        }
    }
}