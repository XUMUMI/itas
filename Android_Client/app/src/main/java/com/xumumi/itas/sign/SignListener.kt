package com.xumumi.itas.sign

import android.content.Context
import android.view.View
import com.itas.itas.httputil.HttpUtil

object SignListener{
    const val url = "http://192.168.123.90:8080/itas/"
    fun viaMAC(contText: Context, map: HashMap<String, View>){
        HttpUtil.judgeMAC(url, contText, DoSign(contText, map))
    }
}
