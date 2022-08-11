package com.matdongsan.domain.report;

public enum ReportCategory {
    TYPE1("허위 사실 유포"),
    TYPE2("욕설 및 혐오발언"),
    TYPE3("홍보성 글");

    private final String value;
    ReportCategory(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
