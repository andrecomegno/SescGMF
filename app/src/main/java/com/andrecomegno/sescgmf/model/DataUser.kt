package com.andrecomegno.sescgmf.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataUser (
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var token: String = ""
): Parcelable