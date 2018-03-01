package org.cxytiandi.conf.client.init;

import org.cxytiandi.conf.client.util.CommonUtil;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
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
            //启动时需要配置来做连接，需要在spring启动前将一些配置信息加载到环境变量使用,多个包用逗号隔开
            SmconfInit.init(CommonUtil.getConfPackagePaths());
        }
    }
}
