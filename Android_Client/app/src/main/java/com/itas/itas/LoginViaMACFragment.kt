package com.itas.itas

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_via_mac.*
import java.util.regex.Pattern

/***LoginViaMACFragment  ： 带头像的登录界面，此页面只需输入密码**********/
/*注释中的 start-end 表示“块” 即范围的开始与结束*/
/*没有 start-end 的注释即为单行注册*/

class LoginViaMACFragment : Fragment(){

    var requestLoginStatus = true

    companion object {
        fun newInstance(width:Int,colorDrawable:Int): LoginViaMACFragment {

            // Bundle主要用于传递数据；它保存的数据，是以key-value(键值对)的形式存在的
            val bundle = Bundle()
            val loginViaMac = LoginViaMACFragment ()

            bundle.putInt("colorDrawable",colorDrawable)
            bundle.putInt("width",width)
            loginViaMac.arguments = bundle

            return loginViaMac

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val loginViaView = inflater.inflate(R.layout.activity_login_via_mac, container, false)


        loginViaView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)

                val colorDrawable = arguments!!.getInt("colorDrawable")
                ButtonShape.buttonShape(colorDrawable,user_button,user_progress)
            }
        })
        return loginViaView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //监听用户密码
        user_userPassword.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable) {
                        val userPassword = user_userPassword.text.toString().trim()
                        val index = user_userPassword.selectionStart
                        val regexName: Pattern = Pattern.compile("^[0-9a-zA-Z&@$()*.~,:?!_#=\\[+^;%/{}'\"<>\\]|]+$")
                        val name = regexName.matcher(userPassword)
                        if (userPassword.isEmpty()) {
                            user_password?.error = ""
                            user_password?.isErrorEnabled = false
                        } else {
                            if (name.matches()) {
                                if (userPassword.length !in 6..32) {
                                    user_button.isClickable = false
                                    user_password?.error = resources.getString(R.string.log_pass_error)
                                } else {
                                    user_button.isClickable = true
                                    user_password?.error = ""
                                    user_password?.isErrorEnabled = false
                                }
                            } else {
                                user_userPassword.text?.delete(index - 1, index)
                            }
                        }
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}
                }
        )

        /*************  user_button 登录按钮点击时将数据与服务器中数据进行比较***************************/
        user_button.setOnClickListener {

            /********************* 需比较的数据 userPassword **********************/
            val userPassword = user_userPassword.text.toString().trim() //用户密码


            val width = user_button.width
            val height = user_button.height

            if (userPassword.isEmpty()) {
                user_password?.error = resources.getString(R.string.log_pass_error)
            } else {

                //用户密码格式正确调用动画使按钮成加载状态
                user_button.isClickable = false
                ShrikButtonAnimator.buttonShape(user_button, user_progress, width, height, ButtonShape.buttonDrawable)
                focusAble(false)


            /*****************start 接受子线程的信号，判断与服务器的通讯结果:成功或失败******************/
                val handler = @SuppressLint("HandlerLeak")
                object : Handler() {
                    override fun handleMessage(msg: Message) {
                        when (msg.what) {

                        /**********msg.what 为 0 时表示成功，成功后执行的代码为布局相关（大佬可不必理会）**************************/
                            0 -> {
                                //成功
                                val revealX = (user_progress.x + user_progress.width / 2).toInt()
                                val revealY = (user_progress.y + user_progress.height / 2).toInt()
                                val activity = activity  as MainActivity?
                                activity?.replaceFragment(MainFragment.newInstance(revealX, revealY), R.id.main_fragment)
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    activity?.loginViaMacTransToLogin()
                                }
                                focusAble(true)

                            }

                        /*************msg.what 为 1 时表示失败，失败后执行的代码为布局相关（大佬可不必理会）************************************/
                            1->{
                                //失败
                                requestLoginFail(width,height)
                            }


                        /*****************msg.what 为 2 时表示连接超时，执行的代码为布局相关（大佬可不必理会）************************************/
                            2->{
                                //连接超时
                                requestLoginFail(width,height)
                                val activity = activity  as MainActivity?
                                activity?.toast("连接超时")

                            }

                        }

                    }
                }
                /**************************** end *******************************************/

                Thread(Runnable {
                    /********  在子线程发送一个消息。**********/
                    Thread.sleep(4000) //模拟与服务器的交互时间
                    val msg = Message()

                    /**************start 将密码 （userPassword）与服务器比较********************/
                    /*如果服务器中存在此mac地址的密码 msg.what =0 */
                    msg.what = 0

                    /*如果服务器中不存在密码 msg.what =1 */

                    /*如果服务器连接超时 msg.what =2 */
                    /*********************** end ****************************************/


                    /*******通讯完毕将消息发送到主线程 handler.sendMessage(msg)*************/
                    handler.sendMessage(msg)
                }).start()


            }
        }

        user_hint.setOnClickListener {
            val activity = activity as MainActivity?
            activity?.comPlain()

        }
    }

    private fun focusAble(able:Boolean){

        user_userPassword.isEnabled = able
        user_hint.isClickable = able
        requestLoginStatus = able
    }

    private fun requestLoginFail(width:Int,height:Int){
        user_progress.visibility = View.INVISIBLE
        user_button.visibility = View.VISIBLE
        MagnifyButtonAnimator.buttonShape(user_button,width, height,ButtonShape.buttonDrawable,ButtonShape.buttonDrawableBat)
        focusAble(true)

    }
}