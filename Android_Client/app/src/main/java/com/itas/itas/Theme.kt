package com.itas.itas

object Theme {

    var theme1 = R.style.AppTheme


    fun changeTheme() {
        theme1 = if (theme1 == R.style.AppTheme) R.style.AppTheme_Transparent else R.style.AppTheme
    }

}