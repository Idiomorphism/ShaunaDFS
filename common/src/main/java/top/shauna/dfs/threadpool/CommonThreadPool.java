package top.shauna.dfs.threadpool;

import top.shauna.dfs.config.PubConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Shauna.Chou
 * @Date 2020/9/27 14:58
 * @E-Mail z1023778132@icloud.com
 */
public class CommonThreadPool {

    public static ExecutorService threadPool;
    static {
        int cores = (PubConfig.getInstance().getThreadPoolNums()==null||PubConfig.getInstance().getThreadPoolNums().equals(0))
                ?Runtime.getRuntime().availableProcessors()*2
                :PubConfig.getInstance().getThreadPoolNums();
        threadPool = new ThreadPoolExecutor(
                cores,
                cores,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(cores/4+1),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
