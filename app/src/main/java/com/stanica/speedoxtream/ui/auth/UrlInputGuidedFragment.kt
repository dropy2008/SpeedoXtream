package com.stanica.speedoxtream.ui.auth

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.leanback.widget.GuidedActionsStylist
import android.text.InputType
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.ui.components.guidedactionstylist.SpeedoXtreamGuidedActionStylist

/**
 * [GuidedStepSupportFragment] asking for the user it's provider playlist URL.
 *
 * @author stanica
 * @date  11/04/2021
 *
 */
class UrlInputGuidedFragment : GuidedStepSupportFragment() {

    companion object {
        internal const val ACTION_URL_PROVIDER : Long = 0
        internal const val ARGUMENT_URL = "url"
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.enter_provider_url_title)
        val description = getString(R.string.enter_provider_url_description)

        return (GuidanceStylist.Guidance(title, description, null, null))
    }

    override fun onCreateActionsStylist(): GuidedActionsStylist {
        return SpeedoXtreamGuidedActionStylist()
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val urlAction = GuidedAction.Builder(context).apply {
            title(R.string.provider_url_text)
            editTitle("Please provide at here : Provider URL")
            editable(true)
            inputType(InputType.TYPE_CLASS_TEXT)
            id(ACTION_URL_PROVIDER)
        }.build()

        actions.add(urlAction)
    }

    override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
        val actionId = action?.id

        if (actionId == ACTION_URL_PROVIDER) {
            val url = action.editTitle.toString()
            if (url.isNotEmpty()) {
                val fragment = UsernameInputGuidedFragment()
                val arguments = Bundle()
                arguments.putString(ARGUMENT_URL, url)
                fragment.arguments = arguments
                add(fragmentManager, fragment)
            }
        }
        return (GuidedAction.ACTION_ID_CURRENT)
    }
}