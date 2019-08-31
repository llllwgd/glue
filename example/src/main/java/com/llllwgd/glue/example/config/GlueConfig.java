package com.llllwgd.glue.example.config;

import com.llllwgd.glue.core.GlueFactory;
import com.llllwgd.glue.core.broadcast.XxlGlueBroadcaster;
import com.llllwgd.glue.core.loader.impl.DBGlueLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: llllwgd
 * Description:
 * Date: 2019-08-31
 * Time: 18:31
 */
@Configuration
public class GlueConfig {

    @Value("${xxl.glue.zkserver}")
    private String zkServer;

    @Bean(initMethod = "init", destroyMethod = "destory",value = "glueFactory")
    public GlueFactory getGlueFactory(DataSource dataSource) {
        GlueFactory glueFactory = new GlueFactory();
        glueFactory.setCacheTimeout(0L);
        glueFactory.setAppName("xxl_glue_core_exapml");
        DBGlueLoader glueLoader = new DBGlueLoader();
        glueLoader.setDataSource(dataSource);
        glueFactory.setGlueLoader(glueLoader);
        return glueFactory;
    }

    @Bean(initMethod = "getClient", destroyMethod = "close",value = "xxlGlueBroadcaster")
    public XxlGlueBroadcaster getXxlGlueBroadcaster() {
        XxlGlueBroadcaster xxlGlueBroadcaster = new XxlGlueBroadcaster(zkServer);
        return xxlGlueBroadcaster;
    }

}
