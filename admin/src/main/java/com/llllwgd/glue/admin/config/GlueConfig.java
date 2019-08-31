package com.llllwgd.glue.admin.config;

import com.llllwgd.glue.core.broadcast.XxlGlueBroadcaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: llllwgd
 * Description:
 * Date: 2019-08-31
 * Time: 14:53
 */
@Configuration
public class GlueConfig {

    @Value("${xxl.glue.zkserver}")
    private String zkServer;

    @Bean(initMethod = "getClient", destroyMethod = "close")
    public XxlGlueBroadcaster getXxlGlueBroadcaster() {
        XxlGlueBroadcaster xxlGlueBroadcaster = new XxlGlueBroadcaster(zkServer);
        return xxlGlueBroadcaster;
    }

}
