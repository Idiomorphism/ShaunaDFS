package top.shauna.dfs.config;

import top.shauna.dfs.SoldierStarter;
import top.shauna.dfs.starter.Starter;
import top.shauna.rpc.bean.FoundBean;
import top.shauna.rpc.bean.RegisterBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author Shauna.Chou
 * @Date 2020/9/27 19:50
 * @E-Mail z1023778132@icloud.com
 */
public class SoldierConfig implements Starter {
    @Override
    public void onStart() throws Exception {
        prepareConfig();
    }

    private void prepareConfig() throws IOException {
        String propPath = System.getProperty("properties");
        Properties properties = new Properties();
        InputStream in = SoldierStarter.class.getClassLoader().getResourceAsStream(propPath);
        properties.load(new InputStreamReader(in,"UTF-8"));
        PubConfig pubConfig = PubConfig.getInstance();
        pubConfig.setRootDir(properties.getProperty("rootPath","/tmp/ShaunaDfs"));
        pubConfig.setPort(properties.getProperty("port","9001"));
        if (properties.getProperty("threadNums")!=null) {
            pubConfig.setThreadPoolNums(Integer.valueOf(properties.getProperty("threadNums")));
        }

        top.shauna.rpc.config.PubConfig rpcConfig = top.shauna.rpc.config.PubConfig.getInstance();
        if (properties.getProperty("shaunaRpc.applicationName")!=null) {
            rpcConfig.setApplicationName(properties.getProperty("shaunaRpc.applicationName"));
        }
        if (properties.getProperty("shaunaRpc.threadNum")!=null) {
            rpcConfig.setThreadPoolNums(Integer.valueOf(properties.getProperty("shaunaRpc.threadNum")));
        }
        if (properties.getProperty("shaunaRpc.timeout")!=null) {
            rpcConfig.setTimeout(Long.valueOf(properties.getProperty("shaunaRpc.timeout")));
        }

        RegisterBean registerBean = new RegisterBean();
        if (properties.getProperty("shaunaRpc.registerBean.potocol")!=null) {
            registerBean.setPotocol(properties.getProperty("shaunaRpc.registerBean.potocol"));
        }else{
            registerBean.setPotocol("zookeeper");
        }
        if (properties.getProperty("shaunaRpc.registerBean.url")!=null) {
            registerBean.setUrl(properties.getProperty("shaunaRpc.registerBean.url"));
        }else{
            registerBean.setUrl("127.0.0.1");
        }
        if (properties.getProperty("shaunaRpc.registerBean.loc")!=null){//&&!properties.getProperty("shaunaRpc.registerBean.loc").equals("")) {
            registerBean.setLoc(properties.getProperty("shaunaRpc.registerBean.loc"));
        }
        rpcConfig.setRegisterBean(registerBean);

        FoundBean foundBean = new FoundBean();
        if (properties.getProperty("shaunaRpc.foundBean.potocol")!=null) {
            foundBean.setPotocol(properties.getProperty("shaunaRpc.foundBean.potocol"));
        }else{
            foundBean.setPotocol("zookeeper");
        }
        if (properties.getProperty("shaunaRpc.foundBean.url")!=null) {
            foundBean.setUrl(properties.getProperty("shaunaRpc.foundBean.url"));
        }else{
            foundBean.setUrl("127.0.0.1");
        }
        if (properties.getProperty("shaunaRpc.foundBean.loc")!=null){//&&!properties.getProperty("shaunaRpc.foundBean.loc").equals("")) {
            foundBean.setLoc(properties.getProperty("shaunaRpc.foundBean.loc"));
        }
        rpcConfig.setFoundBean(foundBean);

        pubConfig.setRpcPubConfig(rpcConfig);
    }
}
