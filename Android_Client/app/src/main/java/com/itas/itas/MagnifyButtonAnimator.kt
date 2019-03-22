package com.itas.itas

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.view.View

object MagnifyButtonAnimator {

    fun buttonShape(view: View, width:Int, height:Int, backDrawable: GradientDrawable,backDrawablebat: GradientDrawable){

        val valueAnimator = ValueAnimator.ofInt(height, width)

        valueAnimator.addUpdateListener { animation ->

            val value = animation.animatedValue as Int
            val leftOffset = (width - value) / 2
            val rightOffset = width - leftOffset

            backDrawable.setBounds(leftOffset, 0, rightOffset, height)
            backDrawable.cornerRadius = value/2f
            view.background = backDrawable

            valueAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    view.background = backDrawablebat
                    view.isClickable = true


                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })

        }
        valueAnimator.duration = 300
        valueAnimator.start()
    }
}