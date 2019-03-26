package com.xumumi.itas.animator

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import com.xumumi.itas.R

class Loading(
    private val context: Context,
    map: HashMap<String, View>
){
    private val logo = map["logo"] as ImageView
    private val signButton = map["signButton"] as Button

    @SuppressLint("SetTextI18n")
    fun signIn(){
        change(context.getString(R.string.sign_in))
    }

    @SuppressLint("SetTextI18n")
    fun signUp(){
        change(context.getString(R.string.sign_up))
    }

    @SuppressLint("SetTextI18n")
    fun error(){
        signButton.isClickable = false
        change(context.getString(R.string.error))
    }

    private fun change(text: String){
        signButton.text = text
        Move.vertical(logo, -100f, 1000)
        Move.vertical(signButton, dp2px(context, -100f), 1000)
    }

}
