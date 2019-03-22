package com.itas.itas

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import android.util.TypedValue
import androidx.annotation.AttrRes


class MainActivity : AppCompatActivity() {

    private var gIntScreenWidth = 0
    private var gFragmentSignBat: SignUpFragment? = null
    private var gFragmentLoginBat: LoginViaMACFragment? = null
    var transToLoginAble = false



    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Theme.theme1)
        setContentView(R.layout.activity_main)

        //获取Button颜色值
        val colorDrawable = getColorByAttributeId(R.attr.colorAccent)

        //获取手机屏幕宽度
        val display = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        gIntScreenWidth  = display.widthPixels

        //设置碎片相对手机实际位置
        second_check_fragment.x = gIntScreenWidth .toFloat()
        third_check_fragment.x = gIntScreenWidth .toFloat()


        //碎片实例化
        val signFragment = SignUpFragment.newInstance(gIntScreenWidth , colorDrawable)
        val loginFragment = LoginViaMACFragment.newInstance(gIntScreenWidth , colorDrawable)

        //碎片备份
        gFragmentSignBat = signFragment
        gFragmentLoginBat = loginFragment

        //动态添加碎片
        replaceFragment(signFragment, R.id.first_check_fragment)

//        replaceFragment(loginFragment, R.id.first_check_fragment)



    }


    //复写返回键
    override fun onBackPressed() {
        if (gFragmentSignBat!!.pagePosition && gFragmentSignBat!!.requestLoginStatus) {
            gFragmentSignBat?.swap2SignInPageA()
        } else if (gFragmentSignBat!!.pagePosition && !gFragmentSignBat!!.requestLoginStatus) {
        } else if (!gFragmentLoginBat!!.requestLoginStatus) {

        }
        else if(transToLoginAble){
            if(!gFragmentSignBat!!.loginFragmentBat!!. requestLoginStatus){}
            else {
                transToSignUp()
                transToLoginAble = false
            }
        }
        else{
            super.onBackPressed()
        }
    }


    //动态替换碎片
    fun replaceFragment(fragment: Fragment, viewId: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(viewId, fragment)
        transaction.commit()
    }

    //设置控件的位置
    private fun move(view: View, offset:Float){
        val animation = ObjectAnimator.ofFloat(view, "translationX", offset)
        animation.duration = 500
        animation.start()
    }

    //获取系统color资源
    private fun getColorByAttributeId(@AttrRes attrIdForColor: Int): Int {
        val typedValue = TypedValue()
        val theme = theme
        theme.resolveAttribute(attrIdForColor, typedValue, true)
        return typedValue.data
    }

    fun toast(text:String){

        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }

    fun comPlain(){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:xumumi@xumumi.com")
        startActivity(intent)
    }



     fun transToLogin(){
         move(first_check_fragment,-gIntScreenWidth .toFloat())
         move( second_check_fragment,0f)
         transToLoginAble = true
    }

    fun loginViaMacTransToLogin(){
        main_fragment.x = gIntScreenWidth.toFloat()
        move(first_check_fragment,-gIntScreenWidth .toFloat())
        move(main_fragment,0f)
    }


    fun transToSignUp(){
        move(first_check_fragment,0f)
        move( second_check_fragment,gIntScreenWidth .toFloat())
        transToLoginAble = false
    }

    fun transToMainFrag(){
        main_fragment.x =gIntScreenWidth.toFloat()
        move(first_check_fragment,-gIntScreenWidth .toFloat())
        move(second_check_fragment,-gIntScreenWidth.toFloat())
        move( main_fragment,0f)
    }



}




