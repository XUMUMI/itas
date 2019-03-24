package com.itas.itas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.view.animation.DecelerateInterpolator
import android.view.ViewAnimationUtils
import android.os.Build
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance(centerX: Int, centerY: Int):  MainFragment  {

            val bundle = Bundle()
            val MainFragment = MainFragment ()
            bundle.putInt("cx", centerX)
            bundle.putInt("cy", centerY)
            MainFragment.arguments = bundle
            return MainFragment

        }
    }

    //LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rootView = inflater.inflate(R.layout.main_fragment, container, false)
            rootView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)

                    val cx = arguments!!.getInt("cx")
                    val cy = arguments!!.getInt("cy")
                    val finalRadius = (Math.max(rootView.width, rootView.height) * 1.1).toFloat()
                    val reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, finalRadius)

                    reveal.interpolator = DecelerateInterpolator(2f)
                    reveal.duration = 1000
                    reveal.start()
                }
            })
            return rootView
        }

        else{
            return inflater.inflate(R.layout.main_fragment, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}


