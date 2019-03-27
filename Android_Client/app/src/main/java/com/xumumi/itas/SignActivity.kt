package com.xumumi.itas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.xumumi.itas.animator.Sign
import com.xumumi.itas.sign.statusCode
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val viewMap: HashMap<String, View> = hashMapOf(
            "signButton" to signButton,
            "userNameInput" to userNameInput
        )

        val sign = Sign(this, viewMap)
        val status = intent.getIntExtra("status", statusCode.ERROR.code)
        val userName = intent.getStringExtra("userName")

        when(status){
            statusCode.SIGN_IN.code -> sign.inWith(userName)
            statusCode.SIGN_UP.code -> sign.up()
        }
    }
}
