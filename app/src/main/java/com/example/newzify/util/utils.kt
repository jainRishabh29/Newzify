package com.example.newzify.util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log

    @SuppressLint("debug")
    fun log(statement: String) {
        Log.d(TAG, statement)
    }