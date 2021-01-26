package com.tkcx.api.vo.query;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间段
 *
 * @author cuijh
 */
@Getter
@Setter
public class TimeSegment {

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;


    public TimeSegment() {
        this.startDate = getStartDateTime(new Date());
        this.endDate = getEndDateTime(DateUtil.offsetDay(new Date(), -1));
    }

    public TimeSegment(Date startDate, Date endDate) {
        this.startDate = getStartDateTime(startDate);
        this.endDate = getEndDateTime(endDate);
    }

    public TimeSegment(String startDate, String endDate) {
        if(StringUtils.isNotEmpty(startDate))
            this.startDate = getStartDateTime(DateUtil.parse(startDate));
        if(StringUtils.isNotEmpty(endDate))
            this.endDate = getEndDateTime(DateUtil.parse(endDate));
    }


    /**
     * 获取一天的开始时间
     *
     * @param date
     * @return
     */
    private Date getStartDateTime(Date date) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    /**
     * 获取一天的结束时间
     *
     * @param date
     * @return
     */
    private Date getEndDateTime(Date date) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);

        return calendar.getTime();
    }

}
