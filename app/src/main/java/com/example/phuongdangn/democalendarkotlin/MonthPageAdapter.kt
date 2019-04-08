package com.example.phuongdangn.democalendarkotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.example.phuongdangn.democalendarkotlin.utils.OnDateSelectListener
import java.util.*

/**
 * Created by phuongdn on 4/5/19.
 */
class MonthPageAdapter(fm: FragmentManager, calendar: Calendar?) : FragmentStatePagerAdapter(fm) {

    private val instances = mutableListOf<OnDateSelectListener>()

    companion object {
        private const val MAX_MONTH_LIMIT = 12
        private const val MAX_YEAR_LIMIT = 2030
    }

    private val mCalendar: Calendar = calendar ?: Calendar.getInstance()

    override fun getItem(position: Int): Fragment {
        val calendar = Calendar.getInstance()
        calendar.add(
            Calendar.MONTH,
            position - TimeUtil.getNumberMonthBetWeenTwoDays(mCalendar, Calendar.getInstance())
        )
        return MonthFragment.newInstance(calendar, position).apply {
            instances.add(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        (`object` as? MonthFragment)?.run {
            instances.remove(this)
        }
    }

    override fun getCount(): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, MAX_MONTH_LIMIT)
        calendar.set(Calendar.YEAR, MAX_YEAR_LIMIT)
        return TimeUtil.getNumberMonthBetWeenTwoDays(mCalendar, calendar)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val calendar = Calendar.getInstance()
        calendar.add(
            Calendar.MONTH,
            position - TimeUtil.getNumberMonthBetWeenTwoDays(mCalendar, Calendar.getInstance())
        )
        return TimeUtil.getFormattedYearMonth(calendar)
    }

    fun notifyChilden() {
        instances.forEach { it.onDateSelect() }
    }

    fun clearDateRange(position: Int) {
        instances.forEach { it.onClearDateRange(position) }
    }
}