package com.lixl.study.designPattern.struct.Adapter.sensitiveword;

public class BSensitiveWordsFilterAdaptor implements ISensitiveWordsFilter {
    private BSensitiveWordsFilter bFilter;

    public String filter(String text) {
        String maskedText = bFilter.filter(text);
        return maskedText;
    }
}
