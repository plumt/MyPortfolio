package com.test.myportfolio.ui.main.calendar

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.test.myportfolio.R
import com.test.myportfolio.BR
import com.test.myportfolio.base.BaseBindingFragment
import com.test.myportfolio.base.BaseRecyclerAdapter
import com.test.myportfolio.custom.CalendarUtils
import com.test.myportfolio.custom.CalendarUtils.Companion.getFormatString
import com.test.myportfolio.custom.CalendarView
import com.test.myportfolio.data.model.CalendarModels
import com.test.myportfolio.databinding.FragmentCalendarBinding
import com.test.myportfolio.databinding.ItemCalendarBinding
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CalendarFragment :
    BaseBindingFragment<FragmentCalendarBinding, CalendarViewModel>(CalendarViewModel::class.java) {
    override val viewModel: CalendarViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_calendar
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.calendar
    override fun onBackEvent() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvCalendar.listener = object : CalendarView.OnCalendarListener {
            override fun onClick(dateTime: DateTime) {
                viewModel.setCalDate(dateTime.getFormatString("yyyy-MM-dd"))
                setCalendar()
            }
        }

        binding.btnNext.setOnClickListener {
            binding.cvCalendar.run {
                viewModel.getSelectMonth(
                    DateTime(current).plusMonths(1).getFormatString("yyyy-MM-dd")
                )
            }
            viewModel.choice = 1
        }

        binding.btnPre.setOnClickListener {
            binding.cvCalendar.run {
                viewModel.getSelectMonth(
                    DateTime(current).minusMonths(1).getFormatString("yyyy-MM-dd")
                )
            }
            viewModel.choice = -1
        }

        binding.rvCalendar.apply {
            adapter = object : BaseRecyclerAdapter.Create<CalendarModels, ItemCalendarBinding>(
                R.layout.item_calendar,
                holdLayoutResId = R.layout.item_calendar,
                bindingVariableId = BR.itemCalendar,
                bindingListener = BR.calendarItemListener,
                bindingViewModel = viewModel
            ) {
                override fun onItemClick(item: CalendarModels, view: View) {
                    when (view.tag) {
                        "ADD" -> dialogAdd()
                        "DEL" -> dialogDelete(item.date, item.event)
                    }
                }
            }
        }

        viewModel.finish.observe(viewLifecycleOwner, {
            if (it) {
                setCalendar(viewModel.choice)
                viewModel.finish.value = false
            }
        })
    }

    private fun dialogDelete(date: String, event: String) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.popup_calendar_del, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.apply {
            findViewById<Button>(R.id.btn_ok).setOnClickListener {
                viewModel.setDeleteEvent(date, event)
                mAlertDialog.dismiss()
                Toast.makeText(context, "삭제하였습니다", Toast.LENGTH_SHORT).show()

            }

            findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    private fun dialogAdd() {
        viewModel.addEventText.value = ""
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.popup_calendar_add, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val mAlertDialog = mBuilder.show()

        mDialogView.apply {
            findViewById<EditText>(R.id.et_calendar).addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.addEventText.value = s.toString()
                }
            })


            findViewById<Button>(R.id.btn_ok).setOnClickListener {
                if (viewModel.addEventText.value!!.isNotEmpty()) {
                    viewModel.setInsertEvent()
                    mAlertDialog.dismiss()
                } else {
                    Toast.makeText(context, "텍스트를 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }

            findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                mAlertDialog.dismiss()
            }

            findViewById<TextView>(R.id.tv_title).text =
                "${DateTime(viewModel.selectDate.value).getFormatString("yyyy년 MM월 dd일의 일정을 등록해 주세요")}"
        }
    }

    private fun setCalendar(value: Int = 0) {
        binding.cvCalendar.let {
            viewModel.run {
                choice = 0
                val dateTime = arrayListOf<DateTime>()
                calendarDB.value!!.forEach {
                    if (it.date != "") {
                        dateTime.add(DateTime.parse(it.date))
                    }
                }
                if (selectDate.value == "") {
                    setCalTitle(DateTime(nowDate).getFormatString("yyyy년 MM월"))
                    setCalDate(DateTime(nowDate).getFormatString("yyyy-MM-dd"))
                    it.initCalendar(
                        DateTime(nowDate),
                        CalendarUtils.getMonthList(DateTime(nowDate)),
                        DateTime.now(),
                        eventDate = dateTime
                    )
                } else {
                    it.current!!.plusMonths(value).run {
                        setCalTitle(this.getFormatString("yyyy년 MM월"))
                        it.initCalendar(
                            this,
                            CalendarUtils.getMonthList(this),
                            DateTime.parse(selectDate.value),
                            eventDate = dateTime
                        )
                    }
                }
            }
        }
    }
}