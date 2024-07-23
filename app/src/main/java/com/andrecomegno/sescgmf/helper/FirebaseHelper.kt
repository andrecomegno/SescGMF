package com.andrecomegno.sescgmf.helper

import com.andrecomegno.sescgmf.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    companion object {
        fun getDatabase() = FirebaseDatabase.getInstance().reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = getAuth().uid
        fun isAuthenticated() = getAuth().currentUser != null
        fun validError(error: String) : Int {
            return when{
                error.contains("An internal error has occurred") -> R.string.alert_invalid_email_password
                error.contains( "The email address is badly formatted") -> R.string.alert_invalid_email_register_fragment
                error.contains("We have blocked all requests from this device due to unusual activity") -> R.string.alert_we_have_blocked_account
                error.contains("The email address is already in use by another account") -> R.string.alert_email_in_use_register_fragment
                error.contains("Password should be at least 6 characters") -> R.string.alert_strong_password_register_fragment
                else -> R.string.alert_error_unknown
            }
        }
    }
}