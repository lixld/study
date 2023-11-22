package com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule;

import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.impl.JsonRuleConfigParser;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.impl.PropertiesRuleConfigParser;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.impl.XmlRuleConfigParser;
import com.lixl.study.designPattern.create.factory.parserFactoryExample.parserRule.impl.YamlRuleConfigParser;

import java.util.HashMap;
import java.util.Map;

public class RuleConfigParserFactory {
  private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

  /**
   * 利用多态，把if分支逻辑去掉
   */
  static {
    cachedParsers.put("json", new JsonRuleConfigParser());
    cachedParsers.put("xml", new XmlRuleConfigParser());
    cachedParsers.put("yaml", new YamlRuleConfigParser());
    cachedParsers.put("properties", new PropertiesRuleConfigParser());
  }

  public static IRuleConfigParser createParser(String configFormat) {

    if (configFormat == null || configFormat.isEmpty()) {
      return null;//返回null还是IllegalArgumentException全凭你自己说了算
    }
    IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
    return parser;
  }
}
