package org.cxytiandi.conf.client.init;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 配置初始化加载器
 *
 * @author yinjihuan
 * @create 2017-11-15 21:22
 **/
public class ConfApplicationContextInitializer implements ApplicationContextInitializer {
    private static AtomicBoolean acBoolean = new AtomicBoolean(false);
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (acBoolean.compareAndSet(false, true)) {
            //启动时需要配置来做连接，需要在spring启动前将一些配置信息加载到环境变量使用
            String pack = System.getProperty("smconf.conf.package");
            if (StringUtils.hasText(pack)) {
                SmconfInit.init(pack);
            }
        }
    }
}
