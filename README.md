Smconf专注于分布式环境下配置的统一管理

目前只支持java,其他语言如果需要支持可以使用Smconf提供的Rest API

![架构图](https://github.com/yinjihuan/smconf/blob/master/images/jiagou.png) 

# 目标
- 提供配置的统一管理
- 多个环境（生产环境：prod, 线上测试环境：online, 线下测试环境：test, 开发环境：dev）
- web后台配置管理
- 配置修改后实时同步到使用的客户端
- 无缝集成spring和spring boot项目
- 非spring项目中也可以使用
- web后台支持不同账号管理不同环境的配置
- 支持水平扩容，负载，部署多个server，client自动发现
- 支持用户自定义配置修改回调接口做扩展
# 文档
- http://cxytiandi.com/blog/user/1/tag/smconf/1

# 案列
- 普通spring项目 https://github.com/yinjihuan/smconf/tree/master/cxytiandi-conf/cxytiandi-conf-demo
- spring boot项目 https://github.com/yinjihuan/smconf/tree/master/cxytiandi-conf/cxytiandi-conf-springboot-demo

# 配置管理
![配置列表](https://github.com/yinjihuan/smconf/blob/master/images/home.png) 
 --
![配置修改](https://github.com/yinjihuan/smconf/blob/master/images/update.png) 
 --
![配置历史记录](https://github.com/yinjihuan/smconf/blob/master/images/history.png) 
 --
![配置推送](https://github.com/yinjihuan/smconf/blob/master/images/push.png) 
# 作者
- 尹吉欢 1304489315@qq.com
- 博客 http://cxytiandi.com/blogs/yinjihuan

# 历史版本下载
- 1.0 https://github.com/yinjihuan/smconf/tree/master/release/1.0

# FAQ

### 整合dubbox 2.8.4报错？
```
Caused by: java.lang.NoSuchMethodError: org.apache.curator.utils.PathUtils.validatePath(Ljava/lang/String;)Ljava/lang/String;
```
这个错主要是由于dubbo也带了zk的客户端，jar包冲突导致的，所以我们将dubbo旧的版本排除掉
```
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>dubbo</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.apache.curator</groupId>
			<artifactId>curator-client</artifactId>
		</exclusion>
		<exclusion>
			<groupId>org.apache.curator</groupId>
			<artifactId>curator-framework</artifactId>
		</exclusion>
	</exclusions>
</dependency>

```
### spring boot整合dubbox 2.8.4启动时需要dubbo配置信息怎么办？
由于Smconf的初始化机制是在spring bean实例化之后再对配置bean进行数据加载

spring boot中使用dubbo是用的@bean注解的初始化方式

所以在初始化dubbo的时候，这个时候Smconf的配置还没有初始化好，导致dubbo的配置不能被管理

为了解决这个问题，可以在启动spring前先将配置信息初始化z到环境变量中，这样后面dubbo就可以顺利的初始化了

只需要一行代码即可
```
public static void main(String[] args) {
	//启动时需要配置来做连接，需要在spring启动前将一些配置信息加载到环境变量使用
	//com.fangjia.ld.service.config是你的配置所在的包
	SmconfInit.init("com.fangjia.ld.service.config");
	//也可以使用 System.setProperty("smconf.conf.package", "com.fangjia.ld.service.config");来代替上面的init
	
	new SpringApplicationBuilder().sources(LdApplication.class).web(false).run(args);
        try {
        	new CountDownLatch(1).await();
	} catch (InterruptedException e) {
		LOGGER.error("项目启动异常", e);
	}
}

application.properties文件中使用$符号取值

spring.dubbo.application.name=${spring.dubbo.applicationName}
spring.dubbo.registry.address=${spring.dubbo.registryAddress}
spring.dubbo.protocol.name=${spring.dubbo.protocolName}
spring.dubbo.protocol.port=${spring.dubbo.protocolPort}

/**
 * dubbo配置信息
 * @author yinjihuan
 *
 */
@CxytianDiConf(system=Constant.SYSTEM,env=true, prefix="spring.dubbo")
public class DubboConf {
	
	@ConfField("zookeeper地址")
	private String registryAddress = "zookeeper://192.168.10.47:2181";
	
	@ConfField("dubbo服务名称")
	private String applicationName = Constant.SYSTEM;
	
	@ConfField("dubbo暴露协议")
	private String protocolName = "dubbo";
	
	@ConfField("dubbo暴露端口")
	private Integer protocolPort = 20881;
}
```
### 非spring环境的java项目中怎么使用？
只需要在程序启动前加载配置即可，但是如果在非spring的环境中使用，获取配置就只能从ConfApplication中获取，不能通过@Autowired注入来使用，因为你没有spring

```
import org.cxytiandi.conf.client.ConfApplication;
import org.cxytiandi.conf.client.init.SmconfInit;
import org.cxytiandi.conf.demo.conf.DbConf;
/**
 * 非Spring环境中使用
 * @author yinjihuan
 *
 */
public class NoSpringEnvDemo {
	public static void main(String[] args) {
		SmconfInit.init("org.cxytiandi.conf.demo.conf");
		System.out.println(ConfApplication.getBean(DbConf.class).getMaxTime());
	}
}

```
### 不想使用配置中心的配置怎么办？
有的时候我们在开发环境下，有多个开发人员，你改改配置我改改配置，导致每个人的都不一样，开发环境下需要调试的话你可以自己在本地搭建一套Smconf

还有一种办法就是只用本地配置文件里的默认值，这样就不用去配置中心加载配置了。
只需要在application.properties中添加smconf.data.status=local即可，默认为加载远程配置
也可以通过-Dsmconf.data.status=local来设置
