package com.gyst.droplet.beans;

import io.realm.RealmObject;

public class Drop extends RealmObject {
    private String what;
    private long now;
    private long when;
    private boolean isComplete;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Drop() {

    }

    public Drop(String what, long now, long when, boolean isComplete) {
        this.what = what;
        this.now = now;
        this.when = when;
        this.isComplete = isComplete;
    }
}
