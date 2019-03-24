package com.itas.itas

import android.graphics.drawable.GradientDrawable
import android.view.View

class ButtonShape {

     val buttonDrawable= GradientDrawable()
     val buttonDrawableBat = GradientDrawable()
     val proGressDrawable= GradientDrawable()

    fun buttonShape(colorDrawable:Int,v1:View,v2:View){
        buttonDrawable.cornerRadius = 30.toFloat()
        buttonDrawableBat.cornerRadius = 30.toFloat()
        proGressDrawable.cornerRadius = 1000.toFloat()
        buttonDrawable.setColor(colorDrawable)
        buttonDrawableBat.setColor(colorDrawable)
        proGressDrawable.setColor(colorDrawable)
        v1.background = buttonDrawable
        v2.background = proGressDrawable
        v2.visibility = View.INVISIBLE

    }

}


