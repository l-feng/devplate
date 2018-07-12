package com.example.core.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.core.R;
import com.example.core.utils.Tools;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class DatePicker extends LinearLayout implements
        SelectorView.SelectionChangedListener {
    private DateListener dateListener;

    private SelectorView yearSelector;
    private SelectorView monthSelector;
    private SelectorView daySelector;

    private int month = 1;
    private int day = 1;
    private final int startYear = 1900;
    private int year = startYear;

    private final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DatePicker(Context context) {
        super(context);
        init();
    }

    /**
     * @param dateListener the dateListener to set
     */
    public void setDateListener(DateListener dateListener) {
        this.dateListener = dateListener;
    }

    private void init() {
        LayoutParams params;
        yearSelector = new SelectorView(getContext());
        params = new LayoutParams(Tools.getPixelByDip(getContext(), 66),
                LayoutParams.WRAP_CONTENT);
        params.leftMargin = Tools.getPixelByDip(getContext(), 33);
        params.rightMargin = Tools.getPixelByDip(getContext(), 11);
        yearSelector.setSelectionListener(this);
        yearSelector.setLayoutParams(params);
        yearSelector.setItems(getYearItems());
        yearSelector.setPadding(0, 0, 0, 0);
        addView(yearSelector);
        monthSelector = new SelectorView(getContext());
        params = new LayoutParams(Tools.getPixelByDip(getContext(), 66),
                LayoutParams.WRAP_CONTENT);
        params.leftMargin = Tools.getPixelByDip(getContext(), 11);
        params.rightMargin = Tools.getPixelByDip(getContext(), 11);
        monthSelector.setSelectionListener(this);
        monthSelector.setLayoutParams(params);
        monthSelector.setItems(getMonthItems());
        monthSelector.setPadding(0, 0, 0, 0);
        addView(monthSelector);
        daySelector = new SelectorView(getContext());
        daySelector.setSelectionListener(this);
        params = new LayoutParams(Tools.getPixelByDip(getContext(), 66),
                LayoutParams.WRAP_CONTENT);
        params.leftMargin = Tools.getPixelByDip(getContext(), 11);
        params.rightMargin = Tools.getPixelByDip(getContext(), 33);
        daySelector.setLayoutParams(params);
        daySelector.setItems(getDayItems(month - 1));
        daySelector.setPadding(0, 0, 0, 0);
        addView(daySelector);
        year = 1980;
        month = 1;
        day = 1;
        yearSelector.setSelection(year - startYear);
        monthSelector.setSelection(month - 1);
        daySelector.setSelection(day - 1);
    }

    private void changeDate(int year, int month, int day) {
        if (this.year != year || this.month != month || this.day != day) {
            this.year = year;
            this.month = month;
            this.day = day;
            if (dateListener != null) {
                dateListener.onDateChanged(this, year, month, day);
            }
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setDate(int year, int month, int day) {
        yearSelector.setSelection(year - startYear);
        monthSelector.setSelection(month - 1);
        daySelector.setSelection(day - 1);
    }

    private CharSequence[] getDayItems(int month) {
        int maxDay = days[month];
        CharSequence[] items = new CharSequence[maxDay];
        for (int i = 0; i < maxDay; i++) {
            items[i] = (1 + i) + getResources().getString(R.string.date_day);
        }
        return items;
    }

    private CharSequence[] getMonthItems() {
        CharSequence[] items = new CharSequence[12];
        for (int i = 0; i < 12; i++) {
            items[i] = (1 + i) + getResources().getString(R.string.month);
        }
        return items;
    }

    private CharSequence[] getYearItems() {
        int maxYears = 300;
        CharSequence[] items = new CharSequence[maxYears];
        for (int i = 0; i < maxYears; i++) {
            items[i] = (startYear + i) + getResources().getString(R.string.year);
        }
        return items;
    }

    private void adjustDay(int month) {

        int day = days[month];
        // beyond the max days of this month
        List<CharSequence> items = daySelector.getItems();
        if (items.size() > day) {
            while (items.size() != day) {
                items.remove(items.size() - 1);
            }
        } else if (items.size() < day) {
            while (items.size() != day) {
                items.add(items.size() + 1 + "");
            }
        }
        if (daySelector.getSelection() >= day) {
            daySelector.setSelection(day - 1);
        } else {
            daySelector.setSelection(daySelector.getSelection());
        }
    }

    @Override
    public void onSelectionChanged(SelectorView view, int selection) {
        if (yearSelector == null || monthSelector == null || daySelector == null) {
            return;
        }
        if (view == yearSelector) {
            int year = selection + startYear;
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                days[1] = 29; // February has 29 days
            } else {
                days[1] = 28;
            }
            if (monthSelector != null && monthSelector.getSelection() == 1) {
                adjustDay(1); // adjust February
            }
        } else if (view == monthSelector) {
            adjustDay(selection);
        } else if (view == daySelector) {
        }
        changeDate(yearSelector.getSelection() + startYear,
                monthSelector.getSelection() + 1, daySelector.getSelection() + 1);
    }

    public void hideDayView() {
        yearSelector.getLayoutParams().width = Tools.getPixelByDip(getContext(), 99);
        monthSelector.getLayoutParams().width = Tools.getPixelByDip(getContext(), 99);
        daySelector.setVisibility(GONE);
    }

    public static interface DateListener {
        public void onDateChanged(DatePicker target, int year, int month, int day);
    }

}

