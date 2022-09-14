package com.matdongsan.util.time;

import com.matdongsan.domain.reply.ReplyTime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeConverter {

    public static String convert(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = time.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        if (diffTime < ReplyTime.SEC){
            return diffTime + "초전";
        }
        diffTime = diffTime / ReplyTime.SEC;
        if (diffTime < ReplyTime.MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / ReplyTime.MIN;
        if (diffTime < ReplyTime.HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / ReplyTime.HOUR;
        if (diffTime < ReplyTime.DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / ReplyTime.DAY;
        if (diffTime < ReplyTime.MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / ReplyTime.MONTH;
        return diffTime + "년 전";
    }
}
