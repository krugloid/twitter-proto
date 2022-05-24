package com.example.proto.utils

import android.graphics.Color

interface UserActivityEvaluator {
    fun numberOfPostsToRange(value: Int): ActivityRange
}

class DefaultUserActivityEvaluator : UserActivityEvaluator {
    override fun numberOfPostsToRange(value: Int): ActivityRange =
        when (value) {
            in ActivityRange.HIGHEST.min..ActivityRange.HIGHEST.max -> ActivityRange.HIGHEST
            in ActivityRange.HIGH.min..ActivityRange.HIGH.max -> ActivityRange.HIGH
            in ActivityRange.NORMAL.min..ActivityRange.NORMAL.max -> ActivityRange.NORMAL
            in ActivityRange.LOW.min..ActivityRange.LOW.max -> ActivityRange.LOW
            in ActivityRange.LOWEST.min..ActivityRange.LOWEST.max -> ActivityRange.LOWEST
            else -> ActivityRange.LOWEST
        }
}

private val MIN_COLOR_VALUE = Color.parseColor("#FF00FF00")
private val MAX_COLOR_VALUE = Color.parseColor("#FFFF0000")
private const val RANGE_SIZE = 40

// Identifies the min and max number of posts for each level of activity
enum class ActivityRange(val min: Int, val max: Int, val color: Int) {
    LOWEST(
        min = 0,
        max = RANGE_SIZE - 1,
        color = MIN_COLOR_VALUE
    ),
    LOW(
        min = RANGE_SIZE,
        max = 2 * RANGE_SIZE - 1,
        color = ((MAX_COLOR_VALUE - MIN_COLOR_VALUE) * 0.25f).toInt() + MIN_COLOR_VALUE
    ),
    NORMAL(
        min = 2 * RANGE_SIZE,
        max = 3 * RANGE_SIZE - 1,
        color = ((MAX_COLOR_VALUE - MIN_COLOR_VALUE) * 0.5f).toInt() + MIN_COLOR_VALUE
    ),
    HIGH(
        min = 3 * RANGE_SIZE,
        max = 4 * RANGE_SIZE - 1,
        color = ((MAX_COLOR_VALUE - MIN_COLOR_VALUE) * 0.75f).toInt() + MIN_COLOR_VALUE
    ),
    HIGHEST(
        min = 4 * RANGE_SIZE,
        max = Int.MAX_VALUE,
        color = MAX_COLOR_VALUE
    );
}