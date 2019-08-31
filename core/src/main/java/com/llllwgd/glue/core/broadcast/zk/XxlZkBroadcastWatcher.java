package com.llllwgd.glue.core.broadcast.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * xxl zk broadcast watcher
 *
 * @author xuxueli 2015-10-29 14:43:46
 */
@Slf4j
public abstract class XxlZkBroadcastWatcher extends XxlZkClient implements Watcher {

    public XxlZkBroadcastWatcher(String zkserver) {
        super(zkserver);
    }

    @Override
    public void process(WatchedEvent event) {

        // reconnect on loss (session expired)
        if (event.getState() == Event.KeeperState.Expired) {
            super.close();
            super.getClient();
            log.info("重新连接zookeeper:{}", super.getClient());
        }

        // node data changed
        if (event.getType() == Event.EventType.NodeDataChanged) {
            String path = event.getPath();

            /**
             * add one-time watch
             *
             * 很有意义：因为我们需要的是最新的数据，并不是实时的。one-time可以保证【Watch事件，
             * 重新Watch】时间段内不会重复监听响应，但是可保证getData是最新的，会通过临时Watch校验中间是否有新数据。
             */
            try {
                super.getClient().exists(path, true);
            } catch (Exception e) {
            }

            String data = null;
            try {
                byte[] resultData = super.getClient().getData(path, true, null);
                data = new String(resultData);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            consume(path, data);
        }

    }

    /**
     * produce msg (set data for node)
     *
     * @param path
     * @param data
     * @return
     */
    protected boolean produce(String path, String data) {
        Stat stat = setData(path, data);
        boolean ret = stat != null ? true : false;
        log.info("生产消息:path:{}:data:{}", path, data);
        return ret;
    }

    /**
     * watch broadcast topic (watch node)
     *
     * @param path
     * @return
     */
    protected boolean watchTopic(String path) {
        try {
            Stat stat = super.getClient().exists(path, true);
            if (stat == null) {
                stat = super.existsOrCreat(path);
                stat = super.getClient().exists(path, true);
            }
            boolean ret = stat != null ? true : false;
            return ret;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * consume msg (watch node)
     *
     * @param path
     * @param data
     */
    protected void consume(String path, String data) {
        try {
            log.info("消费消息:path:{}:data:{}", path, data);
            consumeMsg(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费消息
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public abstract void consumeMsg(String path, String data) throws Exception;
}