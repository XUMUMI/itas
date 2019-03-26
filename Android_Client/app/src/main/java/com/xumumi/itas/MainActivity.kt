package com.xumumi.itas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xumumi.itas.sign.SignListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animatorMap = mapOf(
            "logo"       to logo,
            "signButton" to signButton
        ) as HashMap<String, View>

        SignListener.viaMAC(this, animatorMap)
    }
}
