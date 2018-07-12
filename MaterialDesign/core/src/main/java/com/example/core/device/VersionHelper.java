/**
 * VersionHelper.java
 *
 * @author tianli
 * @date 2011-4-21
 * <p/>
 * Copyright 2011 NetEase. All rights reserved.
 */
package com.example.core.device;


/**
 * @author tianli
 */
public class VersionHelper {
    private Version lastVersion;
    private boolean remindMe = true;

    public Version getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(Version lastVersion) {
        this.lastVersion = lastVersion;
    }

    public boolean isRemindMe() {
        return remindMe;
    }

    public void setRemindMe(boolean remindMe) {
        this.remindMe = remindMe;
    }

}
