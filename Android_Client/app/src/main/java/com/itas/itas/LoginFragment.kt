package com.itas.itas
import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_via_mac.*
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    var requestLoginStatus = true

    companion object {
        fun newInstance(width: Int,colorDrawable:Int): LoginFragment  {

            val bundle = Bundle()
            val login = LoginFragment ()

            bundle.putInt("width", width)
            bundle.putInt("colorDrawable",colorDrawable)
            login.arguments = bundle
            return login

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val loginView = inflater.inflate(R.layout.activity_login, container, false)
        val colorDrawable = arguments!!.getInt("colorDrawable")


        loginView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)

                ButtonShape.buttonShape(colorDrawable,login_button,login_progress)

            }
        })

        return loginView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var  userNameStatus = true
        var userPasswordStatus = true

        /* 监听所输入的用户名是否符合要求 */
        login_username.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val userName = login_username.text.toString().trim()
                        if (userName.isEmpty()) {
                            login_name?.error = ""
                            login_name?.isErrorEnabled = false
                        } else {
                            login_name?.error = resources.getString(R.string.sig_name_error)
                            userNameStatus = false
                            login_button.isClickable = false

                            if (userName.length in 4..16) {
                                login_name?.error = ""
                                login_name?.isErrorEnabled = false
                                userNameStatus = true
                                if (userPasswordStatus) {
                                    login_button.isClickable = true
                                }
                            }
                        }
                    }
                })


        login_user_password.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable){}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                        val userPassword = login_user_password.text.toString().trim()
                        val index =login_user_password.selectionStart
                        val regexName: Pattern = Pattern.compile("^[0-9a-zA-Z&@$()*.~,:?!_#=\\[+^;%/{}'\"<>\\]|]+$")
                        val name = regexName.matcher(userPassword)
                        if (userPassword.isEmpty()) {
                            user_password?.error = ""
                            user_password?.isErrorEnabled = false
                            userPasswordStatus = false
                        } else {
                            login_button.isClickable = false
                            userPasswordStatus = false
                            login_password?.error = resources.getString(R.string.log_pass_error)
                            if (name.matches()) {
                                if (userPassword.length in 6..32) {
                                    login_password?.error = ""
                                    login_password?.isErrorEnabled = false
                                    userPasswordStatus = true
                                    if(userNameStatus){
                                        login_button.isClickable = true
                                    }
                                }
                            } else {
                                login_user_password.text?.delete(index - 1, index)
                            }
                        }
                    }
                })


        /* login_button 登录按钮点击时将数据与服务器中数据进行比较 */
        login_button.setOnClickListener {

            val width = login_button.width
            val height = login_button.height
            val userName =  login_username.text.toString()
            val userPassword = login_user_password.text.toString()
            var requestStatus = true
            var timeOut = false

            when{
                userName.isEmpty() ->  login_name?.error = resources.getString(R.string.sig_name_error)
                userPassword.isEmpty() ->  login_password?.error = resources.getString(R.string.sig_pass_blank)

                else -> {

                    login_button.isClickable = false
                    focusAble(false)

                    /* 用户密码格式正确调用动画使按钮成加载状态 */
                    ShrikButtonAnimator.buttonShape(login_button, login_progress, width, height, ButtonShape.buttonDrawable)

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
                                    val revealX = (login_progress.x + login_progress.width / 2).toInt()
                                    val revealY = (login_progress.y + login_progress.height / 2).toInt()
                                    val activity = activity  as MainActivity?
                                    activity?.replaceFragment(MainFragment.newInstance(revealX, revealY), R.id.main_fragment)
                                    if (Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP){
                                        activity?.transToMainFrag()
                                    }
                                    focusAble(true)
                                    activity?.transToLoginAble = false
                                }else{
                                    requestLoginFail(width,height)
                                }
                            }

                        }
                    }

                    /* 在子线程发送一个消息。*/
                    Thread(Runnable {
                        val msg = Message()
                        Thread.sleep(4000)
                        timeOut = requestTimeOutAble()
                        if(timeOut){
                        handler.sendMessage(msg)
                        }
                        else{
                         requestStatus = requestSuccessAble()
                            handler.sendMessage(msg)
                        }
                    }).start()
                }
            }
        }

        /* 切换到注册页面 */
        create_account.setOnClickListener {

            val activity = activity as MainActivity?
            activity?.transToSignUp()


        }
    }

    private fun focusAble(able:Boolean){

        login_username.isEnabled = able
        login_user_password.isEnabled = able
        create_account.isClickable = able
        requestLoginStatus = able
    }

    private fun requestLoginFail(width:Int,height:Int){
        login_progress.visibility = View.INVISIBLE
        login_button.visibility = View.VISIBLE
        MagnifyButtonAnimator.buttonShape(login_button, width, height,ButtonShape.buttonDrawable,ButtonShape.buttonDrawableBat)
        focusAble(true)

    }

    fun requestTimeOutAble():Boolean{
        return true
    }

    fun requestSuccessAble():Boolean{
        return true
    }
}