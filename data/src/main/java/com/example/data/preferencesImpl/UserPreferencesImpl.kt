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
import javax.inject.Singleton


@Singleton
class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {
    override suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGGED_IN] = true

        }
    }

    override fun getSession(): Flow<UserModel> {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                UserModel(
                    token = preferences[TOKEN_KEY] ?: "",
                    isLogin = preferences[IS_LOGGED_IN] ?: false
                )
            }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("password")
        private val IS_LOGGED_IN = booleanPreferencesKey("isLogin")
    }
}