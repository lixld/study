package com.lixl.study.designPattern.create.factory.parserFactoryExample.parseFactory.impl;

import com.lixl.study.designPattern.create.factory.parserFactoryExample.parseFactory.IRuleConfigParserFactory;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.IRuleConfigParser;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.impl.JsonRuleConfigParser;

public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new JsonRuleConfigParser();
    }
}
