package com.xumumi.itas.animator

import android.content.Context
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.xumumi.itas.R

class Sign(
    private val context: Context,
    viewMap: HashMap<String, View>){

    private val signButton = viewMap["signButton"] as Button
    private val userNameInput = viewMap["userNameInput"] as TextInputEditText

    fun inWith(userName: String?){
        userNameInput.isEnabled = false
        userNameInput.setText(userName)

        signButton.text = context.getString(R.string.sign_in)
    }
    fun up(){
        signButton.text = context.getString(R.string.sign_up)
    }
}
