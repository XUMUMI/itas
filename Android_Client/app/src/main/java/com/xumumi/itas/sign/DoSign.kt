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
    private val map: HashMap<String, View>
): HttpStatusLister {
    override fun onGetMacStatus(macStatus: MacStatus?) {
        val animator = Loading(context, map)
        if (macStatus != null) {
            if(macStatus.status == 0) animator.signIn()
            else if(macStatus.status == 1) animator.signUp()
        } else {
            animator.error()
        }
    }

    override fun onGetLoginStatus(loginStatus: LoginStatus?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetSignStatus(signStatus: SignStatus?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
