package com.xumumi.itas.sign

import android.content.Context
import android.view.View
import com.itas.itas.httputil.HttpStatusLister
import com.itas.itas.itas_gson.LoginStatus
import com.itas.itas.itas_gson.MacStatus
import com.itas.itas.itas_gson.SignStatus
import com.xumumi.itas.animator.Loading

class DoSign(
    private val context: Context,
    private val viewMap: HashMap<String, View>
): HttpStatusLister {
    override fun onGetMacStatus(macStatus: MacStatus?) {
        val animator = Loading(context, viewMap, macStatus!!.userName)
        when(macStatus.status){
            statusCode.SIGN_IN.code -> animator.signIn()
            statusCode.SIGN_UP.code -> animator.signUp()
        }
    }
    override fun onGetLoginStatus(loginStatus: LoginStatus?) = TODO("not implemented")
    override fun onGetSignStatus (signStatus : SignStatus ?) = TODO("not implemented")
}
