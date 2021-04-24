package com.stanica.speedoxtream.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.leanback.widget.GuidedActionsStylist
import android.text.InputType
import android.widget.Toast
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.components.guidedactionstylist.SpeedoXtreamGuidedActionStylist
import com.stanica.speedoxtream.util.Constants

/**
 * Settings section used for setting up the EPG offset for TV Catchup content
 *
 * @author stanica
 * @date  11/04/2021
 */
class TvCatchupEpgOffsetGuidedFragment : GuidedStepSupportFragment() {

    companion object {
        private const val ACTION_OFFSET: Long = 0
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val sharedPreferences = context?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
        val epgOffset = sharedPreferences?.getInt(Constants.EPG_OFFSET_PREFERENCE, 0)

        val title = getString(R.string.epg_offset_title)
        val description = getString(R.string.epg_offset_description)
        val breadcrumb = getString(R.string.current_status_text, getString(R.string.hours_text, epgOffset))

        return GuidanceStylist.Guidance(title, description, breadcrumb, null)
    }

    override fun onCreateActionsStylist(): GuidedActionsStylist {
        return SpeedoXtreamGuidedActionStylist()
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val offsetAction = GuidedAction.Builder(context).apply {
            title(R.string.offset_text)
            editTitle("")
            editable(true)
            editInputType(InputType.TYPE_CLASS_NUMBER and InputType.TYPE_NUMBER_FLAG_DECIMAL and InputType.TYPE_NUMBER_FLAG_SIGNED)
            id(ACTION_OFFSET)
        }.build()

        actions.add(offsetAction)
    }

    override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
        val id = action?.id

        if (id == ACTION_OFFSET) {
            val offset = action.editTitle.toString()

            if (offset.isNotEmpty() && offset.isNotBlank()) {
                Toast.makeText(context, R.string.clear_cache_toast, Toast.LENGTH_SHORT).show()
                val editor = context?.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)?.edit()
                editor?.putInt(Constants.EPG_OFFSET_PREFERENCE, offset.toInt())
                editor?.apply()
                activity?.finish()
            } else {
                action.title = getString(R.string.offset_empty)
            }
        }
        return (GuidedAction.ACTION_ID_CURRENT)
    }
}