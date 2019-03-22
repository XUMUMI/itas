package com.itas.itas

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern
class SignUpFragment : Fragment() {

    var pagePosition = false
    var requestLoginStatus = true
    var loginFragmentBat : LoginFragment?= null


    /* kotlin 中修饰静态方法，可用类名.方法名调用，newInstance 可传递数据 */
    companion object {
        fun newInstance(width: Int,colorDrawable:Int):SignUpFragment {

           /* Bundle 主要用于传递数据；它保存的数据，是以 key-value(键值对) 的形式存在的 */
            val bundle = Bundle()
            val signUpFragment =SignUpFragment ()

            bundle.putInt("width", width)
            bundle.putInt("colorDrawable",colorDrawable)
            signUpFragment.arguments = bundle

            return signUpFragment

        }
    }


    /* onCreateView() 为碎片和活动建立关联的时候调用(设置按钮形状及注册页的下一步页面相对于手机的实际位置) */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView=inflater.inflate(R.layout.activity_sign_up, container, false)


        val colorDrawable = arguments!!.getInt("colorDrawable")
        val width = arguments!!.getInt("width")

        /* 当视图的布局边界由于视图处理而发生更改时调用 */
        rootView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int){
                v.removeOnLayoutChangeListener(this)

                ButtonShape.buttonShape(colorDrawable, sign_up_step1_button,progress)
                ButtonShape.buttonShape(colorDrawable, sign_up_step2_button,progress)
                /* 设置注册页的下一步页面相对于手机的实际位置 */
                sign_up_box2.x = width.toFloat()
                sign_up_step2_button.x = width.toFloat()
                back_to_sign_up.x = width.toFloat()

            }
        })
        return rootView
    }


    /* 确保与碎片相关的活动已加载完时调用(监听所输入的是否符合要求) */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var userNameStatus = true
        var userEmailStatus = true
        var userPasswordStatus = true
        var confirmPasswordStatus = true

        /* 监听所输入的用户名是否符合要求 */
        sign_up_username.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable) {}

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val userName = sign_up_username.text.toString().trim()

                        if (userName.isEmpty()) {
                            sign_up_name?.error = ""
                            sign_up_name?.isErrorEnabled = false
                            userNameStatus = true
                        } else {
                            sign_up_name?.error = resources.getString(R.string.sig_name_error)
                            userNameStatus = false
                            sign_up_step1_button.isClickable = false

                            if (userName.length in 4..16) {
                                sign_up_name?.error = ""
                                sign_up_name?.isErrorEnabled = false
                                userNameStatus = true

                                if (userEmailStatus) {
                                    sign_up_step1_button.isClickable = true
                                }
                            }
                        }
                    }
                })


        /* 监听所输入的邮箱格式是否符合要求 */
        sign_up_user_email.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val userEmail = sign_up_user_email.text.toString().trim()
                        val regexEmail: Pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
                        val email = regexEmail.matcher(userEmail)
                        if (userEmail.isEmpty()) {
                            sign_up_email?.error = ""
                            sign_up_email?.isErrorEnabled = false
                        } else {
                            if (email.matches()) {
                                sign_up_email?.error = ""
                                sign_up_email?.isErrorEnabled = false
                                userEmailStatus = true
                                if (userNameStatus) {
                                    sign_up_step1_button.isClickable = true
                                }
                            } else {
                                userEmailStatus = false
                                sign_up_step1_button.isClickable = false
                                sign_up_email?.error = resources.getString(R.string.sig_email_error)
                            }
                        }
                    }
                })


        /* 监听“下一步”按钮，实现页面切换动画效果 */
        sign_up_step1_button.setOnClickListener {
            val userName = sign_up_username.text.toString()
            val userEmail = sign_up_user_email.text.toString()
            val width = arguments!!.getInt("width")

            when {
                userName.isEmpty() -> sign_up_name?.error = resources.getString(R.string.sig_name_blank)
                userEmail.isEmpty() -> sign_up_email?.error = resources.getString(R.string.sig_email_blank)

                else -> {
                    move(sign_up_box1, -width.toFloat())
                    move(sign_up_step1_button, -width.toFloat())
                    move(transition_to_login, -width.toFloat())
                    move(sign_up_box2, 0f)
                    move(sign_up_step2_button, 0f)
                    move(back_to_sign_up, 0f)

                    /* 使移出手机屏幕可视区的部分控件失焦 */
                    signInPageA(false)
                    signInPageB(true)
                    pagePosition = true

                }
            }
        }

        /* 监听输入的密码是否符合要求 */
        sign_up_user_password.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val password = sign_up_user_password.text.toString()
                        val index = sign_up_user_password.selectionStart
                        val regexName: Pattern = Pattern.compile("^[0-9a-zA-Z&@$()*.~,:?!_#=\\[+^;%/{}'\"<>\\]|]+$")
                        val name = regexName.matcher(password)

                        if(password.isEmpty()){
                            sign_up_password?.error = ""
                            sign_up_password?.isErrorEnabled = false
                        }
                        else {
                            if (name.matches()) {
                                sign_up_password?.error = resources.getString(R.string.sig_pass_error)
                                userPasswordStatus = false
                                sign_up_step2_button.isClickable = false

                                if (password.length in 6..32) {
                                    sign_up_password?.error = ""
                                    sign_up_password?.isErrorEnabled = false
                                    confirm_password?.error = ""
                                    confirm_password?.isErrorEnabled = false
                                    userPasswordStatus = true
                                    if (confirmPasswordStatus) {
                                        sign_up_step2_button.isClickable = true
                                    }
                                }
                            } else {
                                sign_up_user_password.text?.delete(index - 1, index)
                            }
                        }
                    }
                })


        confirm_user_password.addTextChangedListener(
                object : TextWatcher{
                    override fun afterTextChanged(p0: Editable?) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val confirmPassword =confirm_user_password.text.toString().trim()
                        val index = sign_up_user_password.selectionStart
                        val regexName: Pattern = Pattern.compile("^[0-9a-zA-Z&@$()*.~,:?!_#=\\[+^;%/{}'\"<>\\]|]+$")
                        val name = regexName.matcher(confirmPassword)

                        if(confirmPassword.isEmpty()){
                            confirm_password?.error = ""
                            confirm_password?.isErrorEnabled = false
                        }else {
                            if (name.matches()) {
                                confirmPasswordStatus = false
                                confirm_password?.error = ""
                                sign_up_step2_button.isClickable = false

                                if (confirmPassword.length in 6..32) {
                                    confirmPasswordStatus = true
                                    if (userPasswordStatus) {
                                        sign_up_step2_button.isClickable = true
                                    }
                                }
                            } else {
                                confirm_user_password.text?.delete(index - 1, index)

                            }
                        }
                    }
                })


        /* sign_up_step2_button 注册按钮点击时将数据提交到服务器 */
        sign_up_step2_button.setOnClickListener {
            val width = sign_up_step2_button.width
            val height = sign_up_step2_button.height
            val confirmPassword = confirm_user_password.text.toString().trim()
            var requestStatus = true
            var timeOut = false

            val userPassword = sign_up_user_password.text.toString().trim()
            val userName = sign_up_username.text.toString().trim()
            val userEmail = sign_up_user_email.text.toString().trim()

            if (userPassword.isEmpty()) {
                sign_up_password?.error = resources.getString(R.string.sig_pass_blank)
            }


            if (userPassword == confirmPassword && confirmPassword != "") {
                sign_up_step2_button.isClickable = false
                focusAble(false)
                ShrikButtonAnimator.buttonShape(sign_up_step2_button, progress, width, height, ButtonShape.buttonDrawable)

                /* 接受子线程的信号，判断与服务器的通讯结果:成功或失败 */
                val handler = @SuppressLint("HandlerLeak")
                object : Handler() {
                    override fun handleMessage(msg: Message) {
                        if(timeOut){
                            requestLoginFail(width,height)
                            val activity = activity  as MainActivity?
                            activity?.toast("连接超时")
                        }else{
                            if(requestStatus){
                                val revealX = (progress.x + progress.width / 2).toInt()
                                val revealY = (progress.y + progress.height / 2).toInt()
                                val activity = activity as MainActivity?
                                activity?.replaceFragment(MainFragment.newInstance(revealX, revealY), R.id.main_fragment)
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                                    activity?.transToMainFrag()
                                }
                                pagePosition = false
                                focusAble(true)
                            }else{
                                requestLoginFail(width,height)
                            }
                        }
                    }
                }

                /* 在子线程发送一个消息。*/
                Thread(Runnable {
                    Thread.sleep(4000) //模拟与服务器的交互时间
                    val msg = Message()
                    timeOut = requestTimeOutAble()
                    if(timeOut){
                        handler.sendMessage(msg)
                    }
                    else{
                        requestStatus = requestSuccessAble()
                        handler.sendMessage(msg)
                    }
                }).start()

            }else{
                confirm_password?.error = resources.getString(R.string.sig_pass_confirm_error)
            }
        }


        transition_to_login.setOnClickListener {
            val activity = activity as MainActivity?
            val loginFragment = LoginFragment.newInstance(arguments!!.getInt("width"),arguments!!.getInt("colorDrawable"))
            loginFragmentBat = loginFragment
            activity?.replaceFragment(loginFragment, R.id.second_check_fragment)
            activity?.transToLogin()
        }


        /* 监听“上一步”按钮，实现页面切换动画效果 */
        back_to_sign_up.setOnClickListener {
            swap2SignInPageA()
        }

    }


    private fun move(view: View, offset:Float){
        val animation = ObjectAnimator.ofFloat(view, "translationX", offset)
        animation.duration = 500
        animation.start()
    }
     fun swap2SignInPageA(){
        val width = arguments!!.getInt("width")
        move(sign_up_step1_button, 0f)
        move(sign_up_step2_button, width.toFloat())
        move(sign_up_box1, 0f)
        move(sign_up_box2, width.toFloat())
        move(transition_to_login, 0f)
        move(back_to_sign_up, width.toFloat())

        signInPageA(true)
        signInPageB(false)
         pagePosition = false
    }
    /*使控件失焦具体实现*/
    private fun signInPageA(able:Boolean){
        sign_up_username.isEnabled = able
        sign_up_user_email.isEnabled = able
        sign_up_step1_button.isClickable = able
        transition_to_login.isClickable = able
    }

    private fun signInPageB(able:Boolean){
        sign_up_user_password.isEnabled = able
        confirm_user_password.isEnabled = able
        sign_up_step2_button.isClickable = able
        back_to_sign_up.isClickable = able
    }

    private fun focusAble(able:Boolean){

        sign_up_user_password.isEnabled = able
        confirm_user_password.isEnabled = able
        back_to_sign_up.isClickable = able
        requestLoginStatus = able
    }

    private fun requestLoginFail(width:Int,height:Int){
        progress.visibility = View.INVISIBLE
        sign_up_step2_button.visibility = View.VISIBLE
        MagnifyButtonAnimator.buttonShape(sign_up_step2_button, width, height, ButtonShape.buttonDrawable,ButtonShape.buttonDrawableBat)
        focusAble(true)

    }


    fun requestTimeOutAble():Boolean{
        return true
    }

    fun requestSuccessAble():Boolean{
        return true
    }

}