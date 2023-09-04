package com.chow.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private var listener: OnDrawListener? = null
    private var color = ContextCompat.getColor(context, R.color.black)
    private var bgColor = ContextCompat.getColor(context, R.color.white)
    private val paint = Paint().apply {
        color = color
        strokeWidth = 20f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
    }
    private val lines = mutableListOf<Line>()
    private val lineCounts = mutableListOf<Int>()
    private var count = 0
    private lateinit var lastPoint: Point
    private var isEraser = false
    private var lastLinePosition = 0

    init {
        setBackgroundResource(R.color.white)
    }

    fun setOnDrawListener(listener: OnDrawListener) {
        this.listener = listener
    }

    fun changeColor(color: Int) {
        this.color = color
    }

    fun switchMode(isEraser: Boolean) {
        this.isEraser = isEraser
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint = Point(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isEraser && lines.size == 0) return true
                addNewLine(
                    Line(
                        lastPoint,
                        Point(event.x, event.y).also { lastPoint = it },
                        isEraser,
                        color
                    )
                )
                listener?.onDraw(lines.lastOrNull())
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (count > 0) {
                    lineCounts.add(count)
                    count = 0
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lines.forEach {
            canvas.drawLine(it.start.x, it.start.y, it.end.x, it.end.y, paint.apply {
                color = if (it.isFromEraser) bgColor else it.color
            })
        }
    }

    fun clearDrawing(isDrawer: Boolean = false) {
        lines.clear()
        invalidate()
        if (isDrawer) listener?.onDraw(null)
    }

    fun addNewLine(line: Line) {
        lines.add(line)
        count++
        invalidate()
    }

    fun undoLastDraw() {
        if (lineCounts.isEmpty() || lines.isEmpty()) return
        var count = lineCounts.removeLast()
        while (count-- > 0) {
            lines.removeLast()
        }
        invalidate()
    }
}

interface OnDrawListener {
    fun onDraw(line: Line?)
}