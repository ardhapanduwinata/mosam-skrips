package com.arwinata.am.skripsi.shared_preference

import android.content.Context
import com.arwinata.am.skripsi.model.PreferenceUserModel

class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USER_NIP = "user_nip"
        private const val NAME = "name"
        private const val JABATAN = "jabatan"
        private const val PHONE = "telp"
        private const val ADDRESS = "address"
        private const val EMAIL = "email"
        private const val USER_NAME = "user_name"
        private const val PROFILE_IMAGE = "profile_image"
        private const val HAS_BAWAHAN = "has_bawahan"
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUserFromAPI(value: PreferenceUserModel) {
        val editor = preferences.edit()
        editor.putString(USER_NIP, value.userNip)
        editor.putString(NAME, value.name)
        editor.putString(JABATAN, value.jabatan)
        editor.putString(EMAIL, value.email)
        editor.putString(PHONE, value.phone)
        editor.putString(USER_NAME, value.username)
        editor.putString(ADDRESS, value.address)
        editor.putString(TOKEN, value.token)
        editor.putBoolean(HAS_BAWAHAN, value.hasBawahan!!)
        editor.putString(PROFILE_IMAGE, value.profileImage)
        editor.apply()
    }

    fun setUserFromLogin(value: PreferenceUserModel) {
        val editor = preferences.edit()
        editor.putString(USER_NIP, value.userNip)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): PreferenceUserModel {
        val model = PreferenceUserModel()

        model.userNip = preferences.getString(USER_NIP, "")
        model.name = preferences.getString(NAME, "")
        model.jabatan = preferences.getString(JABATAN, "")
        model.phone = preferences.getString(PHONE, "")
        model.email = preferences.getString(EMAIL, "")
        model.address = preferences.getString(ADDRESS, "")
        model.username = preferences.getString(USER_NAME, "")
        model.token = preferences.getString(TOKEN, "")
        model.hasBawahan = preferences.getBoolean(HAS_BAWAHAN, false)
        model.profileImage = preferences.getString(PROFILE_IMAGE, "")

        return model
    }

    fun updateUserFromUpdateProfil(value: PreferenceUserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(JABATAN, value.jabatan)
        editor.putString(PHONE, value.phone)
        editor.putString(ADDRESS, value.address)
        editor.putString(EMAIL, value.email)
        editor.apply()
    }

    fun deleteUser() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}