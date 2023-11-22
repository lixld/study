package com.lixl.study.designPattern.action.observer;

import com.google.common.eventbus.Subscribe;

public class RegPromotionObserver {
    private PromotionService promotionService; // 依赖注入

    @Subscribe
    public void handleRegSuccess(long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}
