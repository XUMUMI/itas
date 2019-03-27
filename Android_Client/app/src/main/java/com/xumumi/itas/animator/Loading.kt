package com.xumumi.itas.animator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.xumumi.itas.R
import com.xumumi.itas.jump.Jump2

class Loading(
    private val context: Context,
    viewMap: HashMap<String, View>,
    userName: String?
){
    private val logo = viewMap["logo"] as ImageView
    private val animation_logo = viewMap["animation_logo"] as LottieAnimationView
    private val startButton = viewMap["startButton"] as Button
    private val jump2 = Jump2(context, viewMap, userName)

    @SuppressLint("SetTextI18n")
    fun signIn(){
        change(context.getString(R.string.sign_in))
        jump2.signIn()
    }

    @SuppressLint("SetTextI18n")
    fun signUp(){
        change(context.getString(R.string.sign_up))
        jump2.signUp()
    }

    @SuppressLint("SetTextI18n")
    fun error(){
        startButton.setBackgroundColor(Color.RED)
        startButton.isClickable = false
        change(context.getString(R.string.error))
    }

    private fun change(text: String){
        startButton.text = text
        logo.visibility = VISIBLE
        animation_logo.visibility = INVISIBLE
        Move.vertical(logo, -100f, 1000)
        Move.vertical(startButton, dp2px(context, -100f), 1000)
    }

}
