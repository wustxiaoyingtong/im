package com.jobs.im.core.config;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.util.StdDateFormat;

/**
 * @program: im
 * @ClassName: GlobalJsonDateConvert
 * @description:
 * @author: Author
 * @create: 2024-02-24 08:52
 * @Version 1.0
 **/
public class GlobalJsonDateConvert extends StdDateFormat {
    public static final GlobalJsonDateConvert INSTANCE = new GlobalJsonDateConvert();

    private GlobalJsonDateConvert() {}

    @Override
    public Date parse(String dateStr, ParsePosition pos) {
        return getDate(dateStr, pos);
    }

    @Override
    public Date parse(String dateStr) {
        ParsePosition pos = new ParsePosition(0);
        return getDate(dateStr, pos);
    }

    private Date getDate(String dateStr, ParsePosition pos) {
        SimpleDateFormat sdf = null;
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}$")) {
            sdf = new SimpleDateFormat("yyyy-MM");
            return sdf.parse(dateStr, pos);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr, pos);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(dateStr, pos);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateStr, pos);
        } else if (dateStr.length() == 23) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.parse(dateStr, pos);
        }
        return super.parse(dateStr, pos);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public GlobalJsonDateConvert clone() {
        return new GlobalJsonDateConvert();
    }
}
