package com.example.phuongdangn.democalendarkotlin

import com.example.phuongdangn.democalendarkotlin.model.EventDay
import com.example.phuongdangn.democalendarkotlin.model.SelectedDay
import java.util.*

/**
 * Created by phuongdn on 4/7/19.
 */
class CalendarProperties{
    val CALENDAR_SIZE = 2401
    val FIRST_VISIBLE_PAGE = CALENDAR_SIZE / 2
    private var mCalendarType: Int = 0
    private var mSelectionColor: Int = 0
    private var mTodayLabelColor: Int = 0
    private var mItemLayoutResource: Int = 0
    private var mDisabledDaysLabelsColor: Int = 0

    private var mDisabledDays: List<Calendar> = ArrayList()
    private var mEventDays: List<EventDay> = ArrayList<EventDay>()
    private var mSelectedDays: MutableList<SelectedDay> = ArrayList<SelectedDay>()

}