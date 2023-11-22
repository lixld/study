package com.lixl.study.designPattern.action.templete.demo2_callback.hook;

import java.util.Collection;
import java.util.IdentityHashMap;

class ApplicationShutdownHooks { /* The set of registered hooks */
    private static IdentityHashMap hooks;

    static {
        try {
            hooks = new IdentityHashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            hooks = null;
        }
    }


    static synchronized void add(Thread hook) {
        if (hooks == null) throw new IllegalStateException("Shutdown in progress");
        if (hook.isAlive()) throw new IllegalArgumentException("Hook already running");
        if (hooks.containsKey(hook)) throw new IllegalArgumentException("Hook previously registered");
        hooks.put(hook, hook);
    }

    static void runHooks() {
        Collection<Thread> threads;
        synchronized (ApplicationShutdownHooks.class) {
            threads = hooks.keySet();
            hooks = null;
        }
        for (Thread hook : threads) {
            hook.start();
        }
        for (Thread hook : threads) {
            while (true) {
                try {
                    hook.join();
                    break;
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
