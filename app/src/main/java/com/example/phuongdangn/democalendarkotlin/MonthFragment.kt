package com.example.phuongdangn.democalendarkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phuongdangn.democalendarkotlin.model.MyDate
import com.example.phuongdangn.democalendarkotlin.utils.DateUtils
import com.example.phuongdangn.democalendarkotlin.utils.OnDateSelectListener
import kotlinx.android.synthetic.main.fragment_month.*
import java.util.*

/**
 * Created by phuongdn on 4/5/19.
 */
class MonthFragment : Fragment(), OnDateSelectListener {

    companion object {
        private const val NUMBER_DAY_OF_WEEK = 7
        private const val KEY_START_CALENDAR = "key_start_calendar"
        private const val KEY_START_CALENDAR_POSITION = "key_start_calendar_position"

        fun newInstance(calendar: Calendar, position: Int) = MonthFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(KEY_START_CALENDAR, calendar)
            bundle.putSerializable(KEY_START_CALENDAR_POSITION, position)
            arguments = bundle
        }
    }

    private lateinit var adapter: MonthAdapter
    private val days = ArrayList<MyDate>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = arguments?.getSerializable(KEY_START_CALENDAR) as? Calendar
        val position = arguments?.getSerializable(KEY_START_CALENDAR_POSITION)as?Int
        adapter = MonthAdapter(context!!, calendar!!, loadMonth(position!!))
        recyclerView.layoutManager = activity?.let { GridLayoutManager(it, NUMBER_DAY_OF_WEEK) }
        recyclerView.adapter = adapter
        adapter.itemOnClicked = this::setRangeDate
    }

    private fun setRangeDate(myDate: MyDate, position: Int) {
        val mainActivity = (activity as MainActivity)
        if (mainActivity.isEndDate) {
            mainActivity.startDate = myDate.date
            mainActivity.endDate = myDate.date
            mainActivity.clearDateRange(position)
            days[position].isInRange = true
            adapter.notifyItemChanged(position)
            mainActivity.isEndDate = false
        } else {
            mainActivity.endDate = myDate.date
            mainActivity.notify()
            mainActivity.isEndDate = true
        }
    }

    fun loadMonth(position: Int): List<MyDate> {
        // Get Calendar object instance set calendar to current
        val calendar = DateUtils.getCalendar()
//        calendar.add(Calendar.YEAR, +1000)

        // Add months to Calendar (a number of months depends on ViewPager position)
        calendar.add(Calendar.MONTH, position)

        // Set day of month as 1s
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Get a number of the first day of the week
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Count when month is beginning
        val firstDayOfWeek = calendar.firstDayOfWeek
        val monthBeginningCell = (if (dayOfWeek < firstDayOfWeek) 7 else 0) + dayOfWeek - firstDayOfWeek

        // Subtract a number of beginning days, it will let to load a part of a previous month
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        /*
        Get all days of one page (42 is a number of all possible cells in one page
        (a part of previous month, current month and a part of next month))
         */
        while (days.size < 42) {
            days.add(MyDate(calendar.time,  (activity as? MainActivity)?.isDateInRange(calendar.time) ?: false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return days
    }

    override fun onDateSelect() {
        days.forEach { it.isInRange = (activity as? MainActivity)?.isDateInRange(it.date) ?: false }
        adapter.notifyDataSetChanged()
    }

    override fun onClearDateRange(position: Int) {
        days.forEach { it.isInRange = false }
        adapter.notifyDataSetChanged()
    }
}