package com.stanica.speedoxtream.ui.auth

import android.content.Context
import android.media.tv.TvContract
import android.os.AsyncTask
import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import android.widget.TextView
import android.widget.Toast
import com.google.android.media.tv.companionlibrary.model.ModelUtils
import com.stanica.speedoxtream.R
import com.stanica.speedoxtream.data.SpeedoXDatabase
import com.stanica.speedoxtream.repository.SharedPreferencesRepositoryImpl
import com.stanica.speedoxtream.util.Constants
import java.lang.ref.WeakReference

/**
 * [GuidedStepSupportFragment] verifying whether the user can use it's provider services.
 *
 * @author stanica
 * @date  11/04/2021
 */
class VerificationGuidedFragment : GuidedStepSupportFragment(), AuthView {

    override fun onStart() {
        super.onStart()

        val url = arguments?.getString(UrlInputGuidedFragment.ARGUMENT_URL)
        val username = arguments?.getString(UsernameInputGuidedFragment.ARGUMENT_USERNAME)?.trim()
        val password = arguments?.getString(PasswordInputGuidedFragment.ARGUMENT_PASSWORD)?.trim()
        val loadingTextView = activity?.findViewById<TextView>(R.id.loading_title)
        val authPresenter = AuthPresenterImpl(this)
        val serviceUrl = getString(R.string.provider_playlist_url, url, username, password, Constants.STREAM_TYPE_HLS)

        loadingTextView?.text = getString(R.string.wont_be_long_text)
        authPresenter.validateInfo(serviceUrl, SharedPreferencesRepositoryImpl(requireContext()))
    }

    override fun onCreateGuidanceStylist(): GuidanceStylist {
        return (object : GuidanceStylist() {
            override fun onProvideLayoutId(): Int {
                return (R.layout.fragment_guided_wizard_loading)
            }
        })
    }

    override fun onConnectionFailed() {
        Toast.makeText(context, R.string.wrong_credentials_toast, Toast.LENGTH_SHORT).show()
        popBackStackToGuidedStepSupportFragment(UrlInputGuidedFragment::class.java, 0)
    }

    override fun onConnectionSuccess(isAccountChanged: Boolean) {

        if (isAccountChanged) {
            activity?.callingActivity?.let {
                // Since the calling activity may be the settings, let's erase everything.
                AsyncDeleteChannels(requireContext()).execute()
                val contentDao = SpeedoXDatabase.getInstance(context!!).avContentDao()
                contentDao.deleteAll()
            }
        }

        val fragment = UserConnectedGuidedFragment()
        val fragmentArguments = Bundle()
        fragmentArguments.putString(UsernameInputGuidedFragment.ARGUMENT_USERNAME, arguments?.getString(UsernameInputGuidedFragment.ARGUMENT_USERNAME))
        fragment.arguments = fragmentArguments
        fragmentManager?.let {
            add(it, fragment)
        }
    }

    override fun onTimeoutReceived() {
        if (isAdded) {
            Toast.makeText(context, R.string.connection_timeout_toast, Toast.LENGTH_SHORT).show()
            popBackStackToGuidedStepSupportFragment(UrlInputGuidedFragment::class.java, 0)
        }
    }

    private class AsyncDeleteChannels(context: Context) : AsyncTask<Void, Void, Void?>() {

        private val asyncReference = WeakReference(context)

        override fun doInBackground(vararg params: Void?): Void? {
            val context = asyncReference.get()

            context?.let {
                val contentResolver = it.contentResolver
                val sharedPreferences = it.getSharedPreferences(Constants.SPEEDOXTREAM_PREFERENCES, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val keyMap = sharedPreferences.all
                val channels = ModelUtils.getChannels(contentResolver)

                // Remove all saved channels in the system
                for (channel in channels) {
                    val channelUri = TvContract.buildChannelUri(channel.id)
                    contentResolver.delete(channelUri, null, null)
                }

                // Remove all custom channel groups preferences
                for (entry in keyMap) {
                    if (entry.key.contains(Constants.CHANNEL_GROUP_PREFERENCE)) {
                        editor.remove(entry.key)
                    }
                }
                editor.apply()
            }
            return null
        }
    }
}