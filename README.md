Smconf专注于分布式环境下配置的统一管理

目前只支持java,其他语言如果需要支持可以使用Smconf提供的Rest API

![架构图](https://github.com/yinjihuan/smconf/blob/master/images/jiagou.png) 

# 目标
- 提供配置的统一管理
- 多个环境（生产环境：prod, 线上测试环境：online, 线下测试环境：test, 开发环境：dev）
- web后台配置管理
- 配置修改后实时同步到使用的客户端
- 无缝集成spring和spring boot项目
- web后台支持不同账号管理不同环境的配置
- 支持水平扩容，负载，部署多个server，client自动发现
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
	
	new SpringApplicationBuilder().sources(LdApplication.class).web(false).run(args);
        try {
        	new CountDownLatch(1).await();
	} catch (InterruptedException e) {
		LOGGER.error("项目启动异常", e);
	}
}
```
