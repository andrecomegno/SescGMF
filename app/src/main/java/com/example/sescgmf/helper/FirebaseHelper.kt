package com.example.sescgmf.helper

import com.example.sescgmf.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object
    {
        fun getDatabase() = FirebaseDatabase.getInstance().reference
        private fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = getAuth().uid
        fun isAuthenticated() = getAuth().currentUser != null
        fun validError(error: String) : Int
        {
            return when{
                error.contains("An internal error has occurred") -> {
                    R.string.Invalid_email_password
                }

                error.contains( "The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }

                error.contains("We have blocked all requests from this device due to unusual activity") -> {
                    R.string.We_have_blocked_account
                }

                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use_register_fragment
                }

                error.contains("Password should be at least 6 characters") -> {
                    R.string.strong_password_register_fragment
                }

                else -> {
                    R.string.error_generic
                }
            }
        }
    }
}