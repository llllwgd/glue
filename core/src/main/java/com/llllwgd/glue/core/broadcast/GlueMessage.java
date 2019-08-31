package com.llllwgd.glue.core.broadcast;

import java.util.Set;

/**
 * Message for Glue broadcast
 *
 * @author xuxueli 2016-5-20 22:21:06
 */
public class GlueMessage {

    private String glueName;
    private Set<String> appnames;
    private long version;

    public String getGlueName() {
        return glueName;
    }

    public void setGlueName(String glueName) {
        this.glueName = glueName;
    }

    public Set<String> getAppnames() {
        return appnames;
    }

    public void setAppnames(Set<String> appnames) {
        this.appnames = appnames;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}
