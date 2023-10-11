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
                    R.string.account_not_registered_register_fragment
                }

                error.contains( "The email address is badly formatted") -> {
                    R.string.invalid_email_register_fragment
                }
                else -> {
                    0
                }

            }
        }
    }
}