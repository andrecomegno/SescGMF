package com.andrecomegno.sescgmf.model

import android.os.Parcelable
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataVideo(
    var id: String = "",
    var title: String = "",
    val image: String = "",
    val number: String = "",
    val reps: String = "",
): Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}