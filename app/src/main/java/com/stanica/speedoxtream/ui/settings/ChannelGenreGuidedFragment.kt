package com.stanica.speedoxtream.ui.settings

import android.content.ComponentName
import android.content.Context
import android.media.tv.TvContract
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.google.android.media.tv.companionlibrary.sync.EpgSyncJobService
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.service.SpeedoXtreamJobService
import com.stanica.speedoxtream.util.Constants

/**
 * Settings option that lets a user filter the channels he/she wants to watch based on
 * [android.media.tv.TvContract.Programs.Genres]. The channels are classified on a best
 * effort basis.
 *
 * @author stanica
 * @date  11/04/2021
 */
class ChannelGenreGuidedFragment : GuidedStepSupportFragment() {

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.channel_genre_title)
        val description = getString(R.string.channel_genre_description)

        return GuidanceStylist.Guidance(title, description, null, null)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val sharedPreferences = context?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val channelGenres = Constants.CHANNEL_GENRES

        for (i in channelGenres.indices) {
            val actionTitles = channelGenres[i].split("_")
            val actionTitle = if (actionTitles.size == 1) {
                actionTitles[0].toLowerCase().capitalize()
            } else {
                // So far there are only two parts in each section titles so let's define them statically.
                actionTitles[0].toLowerCase().capitalize() + "/" + actionTitles[1].toLowerCase().capitalize()
            }

            val action = GuidedAction.Builder(context).apply {
                id(i.toLong())
                checkSetId(GuidedAction.CHECKBOX_CHECK_SET_ID)
                title(actionTitle)
                checked(sharedPreferences!!.getBoolean(Constants.CHANNEL_GENRE_PREFERENCE + channelGenres[i], true))
            }.build()

            actions.add(action)
        }

        val okAction = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_OK)
        }.build()

        val cancelAction = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_CANCEL)
        }.build()

        actions.add(okAction)
        actions.add(cancelAction)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        if (action?.id == GuidedAction.ACTION_ID_OK) {
            val sharedPreferences = context?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            val channelGenres = Constants.CHANNEL_GENRES
            var hasAnyPreferenceChanged = false

            // Size of all the actions without "ok" and "cancel"
            val checkboxActionsSize = actions.size - 2

            for (i in 0 until checkboxActionsSize) {
                val currentPreference = sharedPreferences?.getBoolean(Constants.CHANNEL_GENRE_PREFERENCE + channelGenres[i], true)
                if (currentPreference != actions[i].isChecked) {
                    hasAnyPreferenceChanged = true
                }
                editor?.putBoolean(Constants.CHANNEL_GENRE_PREFERENCE + channelGenres[i], actions[i].isChecked)
            }

            if (hasAnyPreferenceChanged) {
                val inputId = TvContract.buildInputId(Constants.TV_INPUT_SERVICE_COMPONENT)
                EpgSyncJobService.requestImmediateSync(context, inputId, ComponentName(context, SpeedoXtreamJobService::class.java))
                editor?.apply()
                add(fragmentManager, EpgSyncLoadingGuidedFragment())
            } else {
                activity?.finish()
            }
        } else if (action?.id == GuidedAction.ACTION_ID_CANCEL) {
            activity?.finish()
        }
    }
}