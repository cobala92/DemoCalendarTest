package com.example.phuongdangn.democalendarkotlin

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.phuongdangn.democalendarkotlin.utils.DateUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendar: Calendar
    private lateinit var adapter: MonthPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        calendar = Calendar.getInstance()
        //set previous view calendar
//        calendar.add(Calendar.YEAR, -1000)
        initCalendar()

        imgNextMonth.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem + 1
        }

        imgPrevMonth.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun initCalendar() {
        adapter = MonthPageAdapter(supportFragmentManager, calendar)
        tvMonthOfYear.text = TimeUtil.getFormattedYearMonth(Calendar.getInstance())
        viewPager.adapter = adapter
        viewPager.currentItem = TimeUtil.getNumberMonthBetWeenTwoDays(calendar, Calendar.getInstance())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                tvMonthOfYear.text = adapter.getPageTitle(position)
            }
        })

    }
}
