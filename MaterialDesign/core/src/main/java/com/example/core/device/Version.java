package com.example.core.device;

public class Version {
    private int major = 0;
    private int minor = 0;
    private int build = 0;
    private int fix = 0;

    public Version() {

    }

    public Version(String ver) {
        setVersion(ver);

    }

    public void setVersion(String ver) {
        String[] t = null;
        try {
            t = ver.split("\\.");
        } catch (Exception e) {

        }

        if (t == null) {
            major = 0;
            minor = 0;
            build = 0;
            fix = 0;
            return;
        }

        try {
            if (t.length > 0) {
                major = Integer.parseInt(t[0]);
            }
            if (t.length > 1) {
                minor = Integer.parseInt(t[1]);
            }
            if (t.length > 2) {
                build = Integer.parseInt(t[2]);
            }
            if (t.length > 3) {
                fix = Integer.parseInt(t[3]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public int getFix() {
        return fix;
    }

    public void setFix(int fix) {
        this.fix = fix;
    }

    public String getString() {
        return major + "." + minor + "." + build;
    }

    public static int CompareVersion(Version v1, Version v2, boolean checkBuild) {
        if (v1 == null || v2 == null)
            return 1;

        if (v1.getMajor() > v2.getMajor())
            return 1;
        else if (v1.getMajor() < v2.getMajor())
            return -1;

        if (v1.getMinor() > v2.getMinor())
            return 1;
        else if (v1.getMinor() < v2.getMinor())
            return -1;

        if (v1.getBuild() > v2.getBuild())
            return 1;
        else if (v1.getBuild() < v2.getBuild())
            return -1;

        if (checkBuild) {
            if (v1.getFix() > v2.getFix())
                return 1;
            else if (v1.getFix() < v2.getFix())
                return -1;
        }

        return 0;
    }

}
