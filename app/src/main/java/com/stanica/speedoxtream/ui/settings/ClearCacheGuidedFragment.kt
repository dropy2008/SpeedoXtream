package com.stanica.speedoxtream.ui.settings

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import android.widget.Toast
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.data.SpeedoXDatabase

/**
 * Guided Fragment responsible for clearing all cache inside the application.
 * @author stanica
 * @date  11/04/2021
 */
class ClearCacheGuidedFragment : GuidedStepSupportFragment() {

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.clear_cache_title)
        val description = getString(R.string.clear_cache_description)

        return GuidanceStylist.Guidance(title, description, null, null)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val yesAction = GuidedAction.Builder(context).apply {
            title(R.string.yes_text)
            id(GuidedAction.ACTION_ID_YES)
        }.build()

        val noAction = GuidedAction.Builder(context).apply {
            title(R.string.no_text)
            id(GuidedAction.ACTION_ID_NO)
        }.build()

        actions.add(yesAction)
        actions.add(noAction)
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        val id = action?.id

        if (id == GuidedAction.ACTION_ID_YES) {
            val contentDao = SpeedoXDatabase.getInstance(context!!).avContentDao()
            contentDao.deleteAll()
            Toast.makeText(context, R.string.clear_cache_toast, Toast.LENGTH_SHORT).show()
        }
        activity?.finish()
    }

}