SSM

组织结构
lxb<br/>
├── lxb-common -- SSM框架公共模块<br/>
├── lxb-admin -- 后台管理模板<br/>
├── lxb-ui -- 前台thymeleaf模板[端口:1000]<br/>
|    ├── zheng-upms-common -- upms系统公共模块<br/>
|    ├── zheng-upms-dao -- 代码生成模块，无需开发<br/>
|    ├── zheng-upms-client -- 集成upms依赖包，提供单点认证、授权、统一会话管理<br/>
|    ├── zheng-upms-rpc-api -- rpc接口包<br/>
|    ├── zheng-upms-rpc-service -- rpc服务提供者<br/>
|    └── zheng-upms-server -- 用户权限系统及SSO服务端[端口:1111]<br/>
├── zheng-cms -- 内容管理系统<br/>