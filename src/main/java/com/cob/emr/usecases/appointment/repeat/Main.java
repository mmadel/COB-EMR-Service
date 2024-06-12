package com.cob.emr.usecases.appointment.repeat;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(1717189200000L));
        cal.add(Calendar.HOUR,8);
        cal.add(Calendar.MINUTE,0);
        System.out.println(cal.getTime().getTime());
    }
}
