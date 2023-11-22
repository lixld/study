package com.lixl.study.designPattern.create.factory.DI.MyDI;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanConfigParser implements BeanConfigParser {
    @Override
    public List parse(InputStream inputStream) {
        String content = null;
        return parse(content);
    }

    @Override
    public List parse(String configContent) {
        List beanDefinitions = new ArrayList<>(); // TODO:...
        return beanDefinitions;
    }
}
