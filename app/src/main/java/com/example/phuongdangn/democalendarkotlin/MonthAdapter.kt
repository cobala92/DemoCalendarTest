package com.example.phuongdangn.democalendarkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.phuongdangn.democalendarkotlin.model.TypeText
import com.example.phuongdangn.democalendarkotlin.utils.DateUtils
import com.example.phuongdangn.democalendarkotlin.utils.ItemClickListener
import com.example.phuongdangn.democalendarkotlin.utils.TextViewCustom
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by phuongdn on 4/5/19.
 */
class MonthAdapter(
    private val context: Context,
    private val calendar: Calendar,
    private val days: List<Date>,
    private val itemClickListener: ItemClickListener?
) :
    RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    companion object {
        private const val NUMBER_DAY_OF_WEEK = 7
        private const val DAY_FORMAT = "dd"
    }

    private var mDisabledDays: List<Calendar> = ArrayList()
    private val selected: ArrayList<Int> = ArrayList()
    private var mPosition = 0
    private var mSelectedState = false

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MonthAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_view_day, parent, false)
        return ViewHolder(view, this.itemClickListener!!)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: MonthAdapter.ViewHolder, position: Int) {

        if (!selected.contains(position)) {
            holder.llDay.setBackgroundColor(Color.TRANSPARENT)
        } else {
            holder.llDay.setBackgroundColor(Color.GRAY)
        }

        addView(holder, position)
        holder.onBinData()
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun addView(holder: MonthAdapter.ViewHolder, position: Int) {
        val sdf = SimpleDateFormat()
        sdf.applyPattern(DAY_FORMAT)
        val formattedDate = sdf.format(days[position])
        holder.tvDay.text = formattedDate.toString()

        // background light
        if (position in 11..29) {
            holder.llDay.setBackgroundColor(Color.parseColor("#e8ffca7a"))
            holder.tvDay.setTextColor(Color.WHITE)
        }

        // background dark
        if (position in 15..22) {
            holder.llDay.setBackgroundColor(Color.parseColor("#eef4ab3d"))
            holder.tvDay as TextViewCustom

            holder.tvDay.setTextColor(Color.WHITE)
        }

        // set custom background
        if (position == 5 || position == 30) {
            holder.llDay.background = context.getDrawable(R.drawable.bg_rectangle_selected)
            holder.tvDay.setTextColor(Color.parseColor("#f5226f"))
        }

        // show day current
        if (isDayOfMonth(position)) {
            calendar.time = days[position]
            if (DateUtils.isToday(calendar)) {
                val text = holder.tvDay as TextViewCustom
                text.setState(TypeText.CURRENT)
                text.setTextColor(Color.WHITE)
            }
        }

        // show custom ranger
        if (position in 35..40) {
            when (position) {
                35 -> {
                    val text = holder.tvDay as TextViewCustom
                    text.setState(TypeText.START)
                    text.setTextColor(Color.WHITE)
                }
                40 -> {
                    val text = holder.tvDay as TextViewCustom
                    text.setState(TypeText.END)
                    text.setTextColor(Color.WHITE)
                }
                else -> {
                    val text = holder.tvDay as TextViewCustom
                    text.setState(TypeText.PROGRESS)
                    text.setTextColor(Color.WHITE)
                }
            }
        }
    }

    private fun isDayOfMonth(position: Int): Boolean {
        return position < TimeUtil.getDaysYearMonth(calendar) + TimeUtil.getDayPositionOffset(calendar) &&
                position >= TimeUtil.getDayPositionOffset(calendar)
    }

    private fun getDay(position: Int): Int {
        return position - TimeUtil.getDayPositionOffset(calendar) + 1
    }

    inner class ViewHolder(itemView: View, private val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        internal var tvDay: TextView = itemView.findViewById(R.id.tvDay)
        internal var llDay: RelativeLayout = itemView.findViewById(R.id.llDay)
        internal var llNote: LinearLayout = itemView.findViewById(R.id.llNote)

        fun onBinData() {
            llDay.setOnClickListener {
                itemClickListener.onItemClick(itemView, adapterPosition)
                llDay.setBackgroundColor(Color.TRANSPARENT)

                if (selected.isEmpty()) {
                    selected.add(position)
                } else {
                    val oldSelected = selected[0]
                    selected.clear()
                    selected.add(position)
                    // we do not notify that an item has been selected
                    // because that work is done here.  we instead send
                    // notifications for items to be deselected
                    notifyItemChanged(oldSelected)
                }
            }
            llDay.setOnLongClickListener {
                itemClickListener.onLongItemClick(it, adapterPosition)
                val iv = ImageView(itemView.context)
                iv.setImageResource(R.drawable.ic_arrow_left_calendar)
                val llp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT
                )
                iv.layoutParams = llp
                llNote.addView(iv)
                llNote.visibility = View.VISIBLE
                llNote.setBackgroundColor(Color.RED)
                notifyItemChanged(adapterPosition)
                true
            }
        }
    }
}