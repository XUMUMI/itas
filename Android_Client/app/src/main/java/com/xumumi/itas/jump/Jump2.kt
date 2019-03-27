package com.xumumi.itas.jump

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import com.xumumi.itas.R
import com.xumumi.itas.SignActivity
import com.xumumi.itas.sign.statusCode

class Jump2(
    private val context: Context,
    map: HashMap<String, View>,
    private val userName: String?
){
    private val startButton = map["startButton"] as Button
    private val logo = map["logo"] as ImageView

    fun signIn(){
        sign(statusCode.SIGN_IN.code)
    }

    fun signUp(){
        sign(statusCode.SIGN_UP.code)
    }

    private fun sign(status: Int){
        startButton.setOnClickListener{
            val intent = Intent(context, SignActivity::class.java)
            intent.putExtra("status", status)
            intent.putExtra("userName", userName)

            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                logo,
                context.getString(R.string.logo)
            ).toBundle()

            context.startActivity(intent, bundle)
            context.overridePendingTransition(0, 0)
        }
    }
}