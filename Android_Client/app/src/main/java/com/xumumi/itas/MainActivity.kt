package com.xumumi.itas

import android.animation.Animator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xumumi.itas.sign.SignListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init(this)

    }

    private fun init(context: Context){

        val animatorMap = mapOf(
            "logo"           to logo,
            "animation_logo" to animation_logo,
            "startButton"     to startButton
        ) as HashMap<String, View>

        class lottieListener: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }
            override fun onAnimationEnd(p0: Animator?) {
                SignListener.viaMAC(context, animatorMap)
            }
            override fun onAnimationCancel(p0: Animator?) {
            }
            override fun onAnimationStart(p0: Animator?) {
            }
        }

        animation_logo.addAnimatorListener(lottieListener())
    }
}
