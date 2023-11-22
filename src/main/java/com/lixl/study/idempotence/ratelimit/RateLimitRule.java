package com.lixl.study.idempotence.ratelimit;

public class RateLimitRule {

    public RateLimitRule(RuleConfig ruleConfig) {
        //...
    }

    public ApiLimit getLimit(String appId, String api) {
        //...
        return new ApiLimit();
    }
}
