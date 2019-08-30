import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;


/**
 * @author : mengmuzi
 * create at:  2019-08-30  01:02
 * @description:
 */
public class HelloZK {

    private static final Logger logger = Logger.getLogger(HelloZK.class);

    private final static String CONNECTSTRING = "172.16.208.203:2181";
    private final static String PATH = "/gumin";
    private final static int SESSION_TIMEOUT = 50*1000;

    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });

    }

    public void stopZK(ZooKeeper zk) throws InterruptedException {
        if(null != zk){
            zk.close();
        }
    }

    public void createZnode(ZooKeeper zk, String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        zk.create(nodePath,nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    public String getZnode(ZooKeeper zk, String nodePath) throws KeeperException, InterruptedException {
        String result = null;
        byte[] byteArray = zk.getData(nodePath,false,new Stat());
        result = new String(byteArray);
        return result;
    }
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        HelloZK helloZK = new HelloZK();

        ZooKeeper zooKeeper = helloZK.startZK();

        if(zooKeeper.exists(PATH,false) == null){
            helloZK.createZnode(zooKeeper,PATH,"mengmuzi");
            String retValue = helloZK.getZnode(zooKeeper,PATH);
            logger.info("*******retValue:" + retValue);
        }else{
            logger.info("i have this node...........");
        }
        helloZK.stopZK(zooKeeper);

    }

}
