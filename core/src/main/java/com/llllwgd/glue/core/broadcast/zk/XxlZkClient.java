package com.llllwgd.glue.core.broadcast.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * xxl zk client
 * <p>
 * 1. reconnect on loss
 * 2. heartbeat detection
 *
 * @author xuxueli 2015-8-28 10:37:43
 */
@Slf4j
public abstract class XxlZkClient implements Watcher {

    private String zkserver;

    public XxlZkClient(String zkserver) {
        this.zkserver = zkserver;
    }

    private ZooKeeper zookeeper;
    private ReentrantLock INSTANCE_INIT_LOCK = new ReentrantLock(true);

    /**
     * get or make zookeeper conn
     *
     * @return
     */
    protected ZooKeeper getClient() {
        if (zookeeper != null) {
            return zookeeper;
        } else {
            try {
                if (INSTANCE_INIT_LOCK.tryLock(2, TimeUnit.SECONDS)) {
                    if (zookeeper != null) {
                        return zookeeper;
                    }
                    zookeeper = new ZooKeeper(zkserver, 10000, this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                INSTANCE_INIT_LOCK.unlock();
            }
        }
        log.info("zookeeper初始化成功：{}", zookeeper);
        return zookeeper;
    }

    /**
     * close zookeeper conn
     */
    protected void close() {
        if (zookeeper != null) {
            try {
                zookeeper.close();
                zookeeper = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("zookeeper关闭:{}", zookeeper);
    }

    /**
     * exists or creat node
     *
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    protected Stat existsOrCreat(String path) throws KeeperException, InterruptedException {
        // valid path
        if (path == null || path.length() < 1) {
            return null;
        }

        // exist or create path
        Stat stat = zookeeper.exists(path, false);
        if (stat != null) {
            return stat;
        } else {
            // make parent (/../../..)
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            if (parentPath.length() < path.length()) {
                existsOrCreat(parentPath);
            }
            // make path
            zookeeper.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return null;
        }

    }

    /**
     * set data for node
     *
     * @param path
     * @param data
     * @return
     */
    protected Stat setData(String path, String data) {
        try {
            Stat stat = existsOrCreat(path);
            Stat ret = getClient().setData(path, data.getBytes(), stat != null ? stat.getVersion() : -1);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

