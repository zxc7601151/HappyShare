//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.happy.share.network.logger;

import okhttp3.internal.platform.Platform;

public interface Logger {
    Logger DEFAULT = new Logger() {
        public void log(int level, String tag, String message) {
            Platform.get().log(level, message, (Throwable)null);
        }
    };

    void log(int var1, String var2, String var3);
}
