package com.ailiwean.core.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.media.session.PlaybackStateCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation.RESTART
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.ailiwean.core.Utils
import com.google.android.cameraview.R

/**
 * @Package:        com.ailiwean.core.view
 * @ClassName:      ScanBarView
 * @Description:
 * @Author:         SWY
 * @CreateDate:     2020/4/26 9:50 AM
 */
class ScanBarView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, def: Int = 0) : FrameLayout(context, attributeSet, def) {

    private val ALPHA_LENGHT = 100f
    private val ALPHA_TIME = 300L


    val barView: ImageView by lazy {
        val view = ImageView(context)
        view.layoutParams = ViewGroup.LayoutParams(-1, Utils.dp2px(50f))
        view.setBackgroundResource(R.drawable.ic_scan_bar)
        view
    }

    init {
        post {
            addView(barView)
            startAnim()
        }
    }


    lateinit var animator: ValueAnimator

    fun startAnim() {
        visibility = View.VISIBLE
        animator = ValueAnimator.ofFloat(0f, measuredHeight.toFloat())
                .setDuration(2000)
        animator.addUpdateListener { it ->
            val values = it.animatedValue as Float
            if (values <= ALPHA_LENGHT) {
                alpha = (values / ALPHA_LENGHT).let {
                    if (it > 1f)
                        1f
                    else it
                }
            } else {
                alpha = ((measuredHeight - values) / ALPHA_LENGHT).let {
                    if (it < 0f)
                        0f
                    else it
                }
            }
            translationY = values
        }
        animator.repeatCount = Int.MAX_VALUE - 1
        animator.repeatMode = ValueAnimator.RESTART
        animator.start()
    }

    fun stopAnim() {
        visibility = View.INVISIBLE
        animator.cancel()
    }
}