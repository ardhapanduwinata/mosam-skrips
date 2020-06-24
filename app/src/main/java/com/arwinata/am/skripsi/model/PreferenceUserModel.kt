package com.arwinata.am.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreferenceUserModel(
    var userNip: String? = null,
    var name: String? = null,
    var jabatan: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var username: String? = null,
    var aktif: Boolean? = null,
    var profileImage: String? = null,
    var hasBawahan:Boolean? = null,
    var token: String? = null
) : Parcelable