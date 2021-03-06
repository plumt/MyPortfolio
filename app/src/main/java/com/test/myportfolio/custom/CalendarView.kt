package com.test.myportfolio.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.test.myportfolio.R
import com.test.myportfolio.custom.CalendarUtils.Companion.WEEKS_PER_MONTH
import com.test.myportfolio.custom.CalendarUtils.Companion.isSameDay
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import kotlin.math.max


class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle,
) : ViewGroup(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private var _height: Float = 0f

    var listener: OnCalendarListener? = null

    var current: DateTime? = null

    interface OnCalendarListener {
        fun onClick(dateTime: DateTime)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
        }
    }

    /**
     * Measure
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = paddingTop + paddingBottom + max(
            suggestedMinimumHeight,
            (_height * WEEKS_PER_MONTH).toInt()
        )
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }

    /**
     * Layout
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val iWidth = (width / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val iHeight = (height / WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = (index / DateTimeConstants.DAYS_PER_WEEK) * iHeight

            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }
    }

    /**
     * ?????? ????????? ????????????.
     * @param firstDayOfMonth   ??? ?????? ?????? ??????
     * @param list              ????????? ????????? ?????? ????????? ????????? ?????? (??? 42???)
     */
    fun initCalendar(
        firstDayOfMonth: DateTime,
        list: List<DateTime>,
        selectDayOfMonth: DateTime? = null,
        eventDate: List<DateTime>? = null
    ) {
        current = firstDayOfMonth
        removeAllViews()
        var index = 0
        list.forEachIndexed { i, it ->
            addView(DayItemView(
                context = context,
                date = it,
                firstDayOfMonth = firstDayOfMonth,
                select = isSameDay(it, selectDayOfMonth),
                current = isSameDay(it, DateTime.now()),
                event = if (index < eventDate?.size ?: -1) {
                    isSameDay(it, eventDate!![index])
                } else {
                    false
                }
            ) {
                listener?.onClick(it)
            })
            if (index < eventDate?.size?: -1 && it == eventDate?.get(index)) {
                index++
            }
        }
        invalidate()
    }
}