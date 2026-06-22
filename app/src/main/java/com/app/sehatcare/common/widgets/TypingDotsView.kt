package com.app.sehatcare.common.widgets




import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.app.sehatcare.R

class TypingDotsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val dotRadius = dp(5f)
    private val dotSpacing = dp(8f)
    private val totalWidth = (dotRadius * 2 * 3) + (dotSpacing * 2)
    private val totalHeight = dotRadius * 2

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_primary)
    }

    // Alpha for each dot (0f–1f), animated independently
    private val alphas = floatArrayOf(1f, 1f, 1f)

    private val animators = Array(3) { i ->
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 600
            startDelay = (i * 200).toLong()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { anim ->
                alphas[i] = anim.animatedValue as Float
                invalidate()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(totalWidth.toInt(), totalHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        val cy = height / 2f
        for (i in 0..2) {
            paint.alpha = (alphas[i] * 255).toInt().coerceIn(60, 255)
            val cx = dotRadius + i * (dotRadius * 2 + dotSpacing)
            canvas.drawCircle(cx, cy, dotRadius, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animators.forEach { it.start() }
    }

    override fun onDetachedFromWindow() {
        animators.forEach { it.cancel() }
        super.onDetachedFromWindow()
    }

    private fun dp(value: Float) = value * resources.displayMetrics.density
}