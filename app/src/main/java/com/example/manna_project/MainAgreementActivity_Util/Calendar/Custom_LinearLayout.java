package com.example.manna_project.MainAgreementActivity_Util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Custom_LinearLayout extends LinearLayout {
    Custom_Calendar.CalendarType calendarType;
    ListView listView;

    public Custom_LinearLayout(Context context) {
        super(context);
    }

    public Custom_LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float dragY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        Log.d("manna_js", "onInterceptTouchEvent: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            dragY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && Math.abs(event.getY() - dragY) >= 20.0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("manna_js", "onTouch: " + event.getAction() + " : " + event.getY());
        if (event.getAction() == MotionEvent.ACTION_UP && event.getY() - dragY >= 50.0) {
            moveCalendarType(Custom_Calendar.TouchType.DOWN);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP && event.getY() - dragY <= -50.0) {
            moveCalendarType(Custom_Calendar.TouchType.UP);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void moveCalendarType(Custom_Calendar.TouchType touchType) {
        if (touchType == Custom_Calendar.TouchType.UP) {
            switch (calendarType) {
                case FULL_CALENDAR:
                    calendarType = Custom_Calendar.CalendarType.HALF_CALENDAR;
                    break;
                case HALF_CALENDAR:
                    calendarType = Custom_Calendar.CalendarType.WEEK_CALENDAR;
                    break;
                case WEEK_CALENDAR:
                    break;
            }
        } else if (touchType == Custom_Calendar.TouchType.DOWN) {
            switch (calendarType) {
                case FULL_CALENDAR:
                    break;
                case HALF_CALENDAR:
                    calendarType = Custom_Calendar.CalendarType.FULL_CALENDAR;
                    break;
                case WEEK_CALENDAR:
                    calendarType = Custom_Calendar.CalendarType.HALF_CALENDAR;
                    break;
            }
        }

        this.setCalendarType(calendarType);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setCalendarType(Custom_Calendar.CalendarType calendarType) {
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) listView.getLayoutParams();
        param.width = 0;

        switch (calendarType) {
            case FULL_CALENDAR:
                param.weight = 0f;
                break;
            case HALF_CALENDAR:
                param.weight = 2f;
                break;
            case WEEK_CALENDAR:
                // 한 주만 나타나게 레이아웃 재설정 필요
                break;
        }

        listView.setLayoutParams(param);
    }
}
