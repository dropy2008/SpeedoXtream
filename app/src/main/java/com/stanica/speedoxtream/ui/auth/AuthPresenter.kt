package com.stanica.speedoxtream.ui.auth

import com.stanica.speedoxtream.repository.SharedPreferencesRepository

/**
 * Interface that defines a valid Presenter used for the authentication process.
 *
 * @author stanica
 * @date  11/04/2021
 */
interface AuthPresenter {

    /**
     * Validates the credentials sent by a user and calls, if found, required callback methods.
     *
     * @param url                         the user's provider url
     * @param sharedPreferencesRepository the repository of SharedPreferences.
     */
    fun validateInfo(url: String, sharedPreferencesRepository: SharedPreferencesRepository)

}