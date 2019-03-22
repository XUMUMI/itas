package com.itas.itas

import com.itas.itas.Theme.theme1

object Theme{

    var theme1 = R.style.AppTheme
}

fun changeTheme(){
    theme1 = if (theme1 == R.style.AppTheme) R.style.AppTheme_Transparent else R.style.AppTheme
}