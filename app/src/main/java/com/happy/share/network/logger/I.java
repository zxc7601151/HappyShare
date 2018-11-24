//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.happy.share.network.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

class I {
    protected I() {
        throw new UnsupportedOperationException();
    }

    static void log(int type, String tag, String msg) {
        Logger logger = Logger.getLogger(tag);
        switch(type) {
            case 4:
                logger.log(Level.INFO, msg);
                break;
            default:
                logger.log(Level.WARNING, msg);
        }

    }
}
