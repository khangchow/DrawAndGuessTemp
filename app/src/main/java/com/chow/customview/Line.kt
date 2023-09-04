package com.chow.customview

data class Line(
    val start: Point,
    val end: Point,
    val isFromEraser: Boolean,
    val color: Int
)

data class Point(
    val x: Float,
    val y: Float
)