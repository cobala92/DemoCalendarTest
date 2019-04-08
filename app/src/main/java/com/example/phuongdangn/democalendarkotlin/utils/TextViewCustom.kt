package com.example.phuongdangn.democalendarkotlin.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import com.example.phuongdangn.democalendarkotlin.model.TypeText

/**
 * Created by phuongdn on 4/7/19.
 */
open class TextViewCustom(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    var currentState: TypeText? = null
    var circlePaint = Paint()
    val DEFAULT_PADDING = 20
    val MAX_PROGRESS = 100

    //Square view
    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
            //For making all day views same height (ex. screen width 1080 and we have days with width 154/154/155/154/154/155/154)
            super.onMeasure(widthMeasureSpec, getCircleWidth(context) + View.MeasureSpec.EXACTLY)
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        }
    }

    fun getCircleWidth(context: Context): Int {
        return getDisplayWidth(context) / 7
    }

    fun getDisplayWidth(context: Context): Int {
        return (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width
    }


    override fun onDraw(canvas: Canvas?) {
        when (currentState) {
            TypeText.CURRENT -> {
                canvas?.let { setCircleCurrent(it) }
            }
            TypeText.START -> {
                canvas?.let { setStartView(it) }
            }
            TypeText.PROGRESS -> {
                canvas?.let { setProgressView(it) }
            }
            TypeText.END -> {
                canvas?.let { setEndView(it) }
            }
        }
        super.onDraw(canvas)
    }

    open fun setCircleCurrent(canvas: Canvas) {
        setBackgroundColor(Color.TRANSPARENT)
        circlePaint.color = Color.parseColor("#f5226f")
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        canvas.drawCircle(
            (width / 2).toFloat(),
            (width / 2).toFloat(),
            50f,
            circlePaint
        )
    }

    open fun setStartView(canvas: Canvas) {
        setBackgroundColor(Color.TRANSPARENT)
        circlePaint.color = Color.parseColor("#f5226f")
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        canvas.drawCircle(
            (width / 2).toFloat(),
            (width / 2).toFloat(),
            39f,
            circlePaint
        )

        val rect = Rect(width / 2, width / 4, width, width - width / 4)
        canvas.drawRect(
            rect,
            circlePaint
        )
    }

    open fun setEndView(canvas: Canvas) {
        setBackgroundColor(Color.TRANSPARENT)
        circlePaint.color = Color.parseColor("#f5226f")
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG
        canvas.drawCircle(
            (width / 2).toFloat(),
            (width / 2).toFloat(),
            39f,
            circlePaint
        )

        val rect = Rect(0, width / 4, width / 2, width - width / 4)
        canvas.drawRect(rect, circlePaint)
    }

    open fun setProgressView(canvas: Canvas) {
        setBackgroundColor(Color.TRANSPARENT)
        circlePaint.color = Color.parseColor("#f5226f")
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        val rect = Rect(0, width / 4, width, width - width / 4)
        canvas.drawRect(rect,
            circlePaint
        )
    }

    open fun setState(state: TypeText) {
        this.currentState = state
    }
}