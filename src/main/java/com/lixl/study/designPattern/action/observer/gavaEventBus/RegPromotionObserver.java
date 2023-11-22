package com.lixl.study.designPattern.action.observer.gavaEventBus;

import com.google.common.eventbus.Subscribe;
import com.lixl.study.designPattern.action.observer.PromotionService;

public class RegPromotionObserver {
    private PromotionService promotionService;

    // 依赖注入
    @Subscribe
    public void handleRegSuccess(Long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}
