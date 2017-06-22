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
