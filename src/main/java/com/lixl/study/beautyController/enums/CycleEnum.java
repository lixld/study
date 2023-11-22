package com.lixl.study.beautyController.enums;

import com.lixl.study.beautyController.dto.BaseRequestDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum CycleEnum {

    YEAR {
        @Override
        public void fillBaseRequest(BaseRequestDTO req) {
            Integer year = req.getYear();
            req.setPreStartDate((year - 1) + "-01-01");
            req.setPreEndDate((year - 1) + "-12-31");
            req.setStartDate(year + "-01-01");
            req.setEndDate(year + "-12-31");
        }
    },

    QUARTER {
        @Override
        public void fillBaseRequest(BaseRequestDTO req) {
            SimpleDateFormat sdf = threadLocal.get();
            Integer year = req.getYear();
            Integer cycleNum = req.getCycleNum();
            int month = (cycleNum - 1) * 3 + 1;
            String qStartDateStr = year + "-" + (month < 10 ? "0" + month : month) + "-" + "01";
            req.setStartDate(qStartDateStr);
            {
                Date d = stringToDate(qStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.MONTH, 3);
                c.add(Calendar.DAY_OF_YEAR, -1);
                d = c.getTime();
                String qEndDateStr = sdf.format(d);
                req.setEndDate(qEndDateStr);
            }
            {
                Date d = stringToDate(qStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.MONTH, -3);
                d = c.getTime();
                String qPreStartDateStr = sdf.format(d);
                req.setPreStartDate(qPreStartDateStr);
            }
            {
                Date d = stringToDate(qStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DAY_OF_YEAR, -1);
                d = c.getTime();
                String qPreEndDateStr = sdf.format(d);
                req.setPreEndDate(qPreEndDateStr);
            }
        }
    },

    MONTH {
        @Override
        public void fillBaseRequest(BaseRequestDTO req) {
            SimpleDateFormat sdf = threadLocal.get();
            Integer year = req.getYear();
            Integer cycleNum = req.getCycleNum();
            String mStartDateStr = year + "-" + (cycleNum < 10 ? "0" + cycleNum : cycleNum) + "-" + "01";
            req.setStartDate(mStartDateStr);
            {
                Date d = stringToDate(mStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.MONTH, 1);
                c.add(Calendar.DAY_OF_YEAR, -1);
                d = c.getTime();
                String mEndDateStr = sdf.format(d);
                req.setEndDate(mEndDateStr);
            }
            {
                Date d = stringToDate(mStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.MONTH, -1);
                d = c.getTime();
                String preMStartDateStr = sdf.format(d);
                req.setPreStartDate(preMStartDateStr);
            }
            {
                Date d = stringToDate(mStartDateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DAY_OF_YEAR, -1);
                d = c.getTime();
                String preMEndDateStr = sdf.format(d);
                req.setPreEndDate(preMEndDateStr);
            }
        }
    },

    WEEK {
        @Override
        public void fillBaseRequest(BaseRequestDTO req) {
            SimpleDateFormat sdf = threadLocal.get();
            Integer year = req.getYear();
            Integer cycleNum = req.getCycleNum();
            Calendar c = Calendar.getInstance();
            c.setWeekDate(year, cycleNum, Calendar.MONDAY);
            String wStartDateStr = sdf.format(c.getTime());
            req.setStartDate(wStartDateStr);
            {
                c = Calendar.getInstance();
                c.setWeekDate(year, cycleNum, Calendar.MONDAY);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                c.add(Calendar.DAY_OF_YEAR, -1);
                String wEndDateStr = sdf.format(c.getTime());
                req.setEndDate(wEndDateStr);
            }
            {
                c = Calendar.getInstance();
                c.setWeekDate(year, cycleNum, Calendar.MONDAY);
                c.add(Calendar.WEEK_OF_YEAR, -1);
                String preWStartDateStr = sdf.format(c.getTime());
                req.setPreStartDate(preWStartDateStr);
            }
            {
                c = Calendar.getInstance();
                c.setWeekDate(year, cycleNum, Calendar.MONDAY);
                c.add(Calendar.DAY_OF_YEAR, -1);
                String preWEndDateStr = sdf.format(c.getTime());
                req.setPreEndDate(preWEndDateStr);
            }
        }
    }

    ;

    public abstract void fillBaseRequest(BaseRequestDTO req);

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static Date stringToDate(String str) {
        try {
            SimpleDateFormat sdf = threadLocal.get();
            Date d = sdf.parse(str);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
