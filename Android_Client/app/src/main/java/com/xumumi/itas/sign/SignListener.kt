package com.xumumi.itas.sign

import android.content.Context
import android.view.View
import com.itas.itas.httputil.HttpUtil

enum class statusCode(var code: Int){
    SIGN_IN(0), SIGN_UP(1), ERROR(3)
}

object SignListener{
    private const val url = "http://192.168.123.90:8080/itas/"
    fun viaMAC(contText: Context, viewMap: HashMap<String, View>){
        HttpUtil.judgeMAC(url, contText, DoSign(contText, viewMap))
    }
}
