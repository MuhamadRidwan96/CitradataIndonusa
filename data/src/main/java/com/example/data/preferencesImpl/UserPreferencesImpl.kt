package com.example.data.preferencesImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.domain.model.UserModel
import com.example.domain.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    override suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->

            Timber.tag("SESSION_DEBUG")
                .d(
                    "Saving session: idUser='${user.idUser}', isLogin=true"
                )

            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGGED_IN] = true
            preferences[USER_ID_KEY] = user.idUser
        }
    }

    override fun getSession(): Flow<UserModel> {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->

                UserModel(
                    token = preferences[TOKEN_KEY] ?: "",
                    isLogin = preferences[IS_LOGGED_IN] == true,
                    idUser = preferences[USER_ID_KEY] ?: ""
                )
            }
    }

    override suspend fun logout() {
        Timber.tag("SESSION_DEBUG")
            .d("Logout: clearing session")

        dataStore.edit { preferences ->
            preferences.clear()
        }

        Timber.tag("SESSION_DEBUG")
            .d("Logout: session cleared")
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[FCM_TOKEN_KEY] = token
        }
    }

    companion object {

        private val TOKEN_KEY =
            stringPreferencesKey("auth_token")

        private val IS_LOGGED_IN =
            booleanPreferencesKey("isLogin")

        private val USER_ID_KEY =
            stringPreferencesKey("idUser")

        private val FCM_TOKEN_KEY =
            stringPreferencesKey("fcm_token")
    }
}