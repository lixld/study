package com.lixl.study.designPattern.action.observer.gavaEventBus;

import com.google.common.eventbus.Subscribe;
import com.lixl.study.designPattern.action.observer.NotificationService;

public class RegNotificationObserver {
    private NotificationService notificationService;

    @Subscribe
    public void handleRegSuccess(Long userId) {
        notificationService.sendInboxMessage(userId, "...");
    }
}
