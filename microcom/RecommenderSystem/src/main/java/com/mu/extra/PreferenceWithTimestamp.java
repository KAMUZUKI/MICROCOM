package com.mu.extra;

import org.apache.mahout.cf.taste.model.Preference;
/**
 * @author MUZUKI
 * @Classname PreferenceWithTimestamp
 * @Description TODO
 * @Date 2023/5/27 13:26
 */


public class PreferenceWithTimestamp implements Preference {
    private long userID;
    private long itemID;
    private float value;
    private long timestamp;

    public PreferenceWithTimestamp(long userID, long itemID, float value, long timestamp) {
        this.userID = userID;
        this.itemID = itemID;
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public long getItemID() {
        return itemID;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float v) {

    }

    public long getTimestamp() {
        return timestamp;
    }
}
