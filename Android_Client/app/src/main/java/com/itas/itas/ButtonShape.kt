package com.itas.itas

import android.graphics.drawable.GradientDrawable
import android.view.View

object ButtonShape {

    val buttonDrawable= GradientDrawable()
    val buttonDrawableBat = GradientDrawable()
    val progressDrawable= GradientDrawable()

    fun buttonShape(colorDrawable:Int,v1:View,v2:View){
        buttonDrawable.cornerRadius = 30.toFloat()
        buttonDrawableBat.cornerRadius = 30.toFloat()
        progressDrawable.cornerRadius = 70.toFloat()
        buttonDrawable.setColor(colorDrawable)
        buttonDrawableBat.setColor(colorDrawable)
        progressDrawable.setColor(colorDrawable)
        v1.background = buttonDrawable
        v2.background = progressDrawable
        v2.visibility = View.INVISIBLE

    }

}