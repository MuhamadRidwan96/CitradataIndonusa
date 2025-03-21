package com.example.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Session")

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGGED_IN] = true

        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }.map { preferences ->
                UserModel(
                    email = preferences[EMAIL_KEY] ?: "",
                    token = preferences[TOKEN_KEY] ?: "",
                    isLogin = preferences[IS_LOGGED_IN] ?: false
                )

            }

    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()

        }
    }

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGGED_IN = booleanPreferencesKey("isLogin")
    }


}