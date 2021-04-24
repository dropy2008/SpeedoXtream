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
 * [GuidedStepSupportFragment] asking for the user it's provider username.
 *
 * @author stanica
 * @date  11/04/2021
 */
class UsernameInputGuidedFragment : GuidedStepSupportFragment() {

    companion object {
        internal const val ACTION_USERNAME : Long = 0
        internal const val ARGUMENT_USERNAME = "username"
    }

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val title = getString(R.string.enter_username_title)
        return (GuidanceStylist.Guidance(title, null, null, null))
    }

    override fun onCreateActionsStylist(): GuidedActionsStylist {
        return SpeedoXtreamGuidedActionStylist()
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        val usernameAction = GuidedAction.Builder(context).apply {
            title(R.string.username_text)
            editTitle("")
            editable(true)
            inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            id(ACTION_USERNAME)
        }.build()

        actions.add(usernameAction)
    }

    override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
        val actionId = action?.id

        if (actionId == ACTION_USERNAME) {
            val username = action.editTitle.toString()
            if (username.isNotEmpty()) {
                val fragment = PasswordInputGuidedFragment()
                val currentArguments = Bundle()
                currentArguments.putString(UrlInputGuidedFragment.ARGUMENT_URL, arguments?.getString(UrlInputGuidedFragment.ARGUMENT_URL))
                currentArguments.putString(ARGUMENT_USERNAME, username)
                fragment.arguments = currentArguments
                add(fragmentManager, fragment)
            }
        }
        return (GuidedAction.ACTION_ID_CURRENT)
    }

}