package com.lixl.study.designPattern.create.factory;

import com.lixl.study.designPattern.create.factory.parserFactoryExample.parseFactory.IRuleConfigParserFactory;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parseFactory.RuleConfigParserFactoryMap;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.IRuleConfigParser;

public class RuleConfigSource {

    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

        /**
         * 比如 Java 中的 Calendar、DateFormat 类
         * 封装对象的创建过程---使用工厂模式
         * 使用条件：
         * 封装变化：创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。
         * 代码复用：创建代码抽离到独立的工厂类之后可以复用。
         * 隔离复杂性：封装复杂的创建逻辑，调用者无需了解如何创建对象。
         * 控制复杂度：将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁
         */

        /**
         * 当对象的创建逻辑比较复杂，不只是简单的 new 一下就可以，
         * 而是要组合其他类对象，做各种初始化操作的时候，
         * 我们推荐使用工厂方法模式
         * 将复杂的创建逻辑拆分到多个工厂类中，让每个工厂类都不至于过于复杂
         */
        //工厂方法模式
        IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
        IRuleConfigParser parser = parserFactory.createParser();

        /**
         * 将所有的创建逻辑都放到一个工厂类中
         */
        //简单工厂模式(静态工厂方法模式)
//        IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);

        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }


}




