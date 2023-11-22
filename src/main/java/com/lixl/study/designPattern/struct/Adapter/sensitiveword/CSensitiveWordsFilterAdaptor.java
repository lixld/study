package com.lixl.study.designPattern.struct.Adapter.sensitiveword;

public class CSensitiveWordsFilterAdaptor implements ISensitiveWordsFilter {
    private CSensitiveWordsFilter cFilter;

    public String filter(String text) {
        String maskedText = cFilter.filter(text,"***");
        return maskedText;
    }
}
