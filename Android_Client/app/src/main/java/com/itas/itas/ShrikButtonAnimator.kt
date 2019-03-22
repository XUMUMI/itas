package com.itas.itas

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.view.View


object ShrikButtonAnimator{
    fun buttonShape(view: View, view2:View, width:Int, height:Int,backDrawable: GradientDrawable){

        val valueAnimator = ValueAnimator.ofInt(width, height)
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
                    view.visibility = View.INVISIBLE
                    view2.visibility = View.VISIBLE

                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })

        }
        valueAnimator.duration = 500
        valueAnimator.start()
    }

}