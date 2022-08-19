package com.matdongsan.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum MemberAge {
    MEMBER_AGE_10S("10s", "10대"),
    MEMBER_AGE_20S("20s", "20대"),
    MEMBER_AGE_30S("30s", "30대"),
    MEMBER_AGE_40S("40s", "40대"),
    MEMBER_AGE_50_UP("50s", "50대 이상");

    private final String key;
    private final String value;
}
