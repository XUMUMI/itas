package com.xumumi.itas.animator

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View

object Move{
    @SuppressLint("ObjectAnimatorBinding")
    fun vertical(view: View, distance: Float, time: Long){
        ObjectAnimator.ofFloat(view, "translationY", distance)
            .apply {
            duration = time
            start()
        }
    }
}

