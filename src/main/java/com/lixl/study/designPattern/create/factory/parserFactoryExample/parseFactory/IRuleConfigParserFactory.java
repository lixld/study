package com.lixl.study.designPattern.create.factory.parserFactoryExample.parseFactory;

import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.IRuleConfigParser;

public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}
