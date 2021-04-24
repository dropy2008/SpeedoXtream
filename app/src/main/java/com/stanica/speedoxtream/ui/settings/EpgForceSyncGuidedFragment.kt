package com.stanica.speedoxtream.ui.settings

import android.content.ComponentName
import android.content.Context
import android.media.tv.TvContract
import android.os.AsyncTask
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.google.android.media.tv.companionlibrary.model.ModelUtils
import com.google.android.media.tv.companionlibrary.sync.EpgSyncJobService
import com.google.android.media.tv.companionlibrary.utils.TvContractUtils
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.service.SpeedoXtreamJobService
import com.stanica.speedoxtream.util.Constants
import java.lang.ref.WeakReference

/**
 * Setting option that forces an EPG sync for the next hour.
 * 
 * @author stanica
 * @date  11/04/2021
 */
class EpgForceSyncGuidedFragment : GuidedStepSupportFragment() {

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.force_epg_sync_title)
        val description = getString(R.string.force_epg_sync_description)

        return GuidanceStylist.Guidance(title, description, null, null)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val yesAction = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_YES)
        }.build()

        val noAction = GuidedAction.Builder(context).apply {
            clickAction(GuidedAction.ACTION_ID_NO)
        }.build()

        actions.add(yesAction)
        actions.add(noAction)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        val id = action?.id

        if (id == GuidedAction.ACTION_ID_YES) {
            AsyncResyncPrograms(requireContext()).execute()
            add(fragmentManager, EpgSyncLoadingGuidedFragment())
        } else {
            activity?.finish()
        }
    }

    private class AsyncResyncPrograms(context: Context) : AsyncTask<Void, Void, Void?>() {

        private val asyncReference = WeakReference<Context>(context)

        override fun doInBackground(vararg p0: Void?): Void? {
            val context = asyncReference.get()
            val contentResolver = context?.contentResolver
            val channels = ModelUtils.getChannels(contentResolver)

            for (channel in channels) {
                val programsUri = TvContract.buildProgramsUriForChannel(channel.id)
                contentResolver?.delete(programsUri, null, null)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            val context = asyncReference.get()
            context?.let {
                val inputId = TvContract.buildInputId(Constants.TV_INPUT_SERVICE_COMPONENT)
                EpgSyncJobService.requestImmediateSync(it, inputId, ComponentName(it, SpeedoXtreamJobService::class.java))
            }
        }
    }

}