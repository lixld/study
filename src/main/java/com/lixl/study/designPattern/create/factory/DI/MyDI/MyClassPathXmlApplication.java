package com.lixl.study.designPattern.create.factory.DI.MyDI;

import java.io.InputStream;
import java.util.List;

public class MyClassPathXmlApplication implements MyApplicationContext {
    private BeansFactory beansFactory;
    private BeanConfigParser beanConfigParser;


    public MyClassPathXmlApplication(String configLocation) {
        this.beansFactory = new BeansFactory();
        this.beanConfigParser = new XmlBeanConfigParser();
        loadBeanDefinitions(configLocation);
    }

    private void loadBeanDefinitions(String configLocation) {
        InputStream inputStream = this.getClass().getResourceAsStream("/" + configLocation);//1.从 classpath 中加载 XML 格式的配置文件
        if (inputStream == null) {
            throw new RuntimeException("Can not find config file: " + configLocation);
        }
        List beanDefinitions = beanConfigParser.parse(inputStream);//2.通过 BeanConfigParser 解析为统一的 BeanDefinition 格式

        beansFactory.addBeanDefinitions(beanDefinitions);//3.BeansFactory 根据 BeanDefinition 来创建对象

    }

    @Override
    public Object getBean(String beanId) {
        return beansFactory.getBean(beanId);
    }
}
