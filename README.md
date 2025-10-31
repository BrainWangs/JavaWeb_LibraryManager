# 使用IDEA部署JavaWeb项目

参考教程 [https://www.bilibili.com/video/BV1yg411a7GN]

1. 新建项目

![](https://pic1.imgdb.cn/item/69049fae3203f7be00ba2fba.png) 

2. 打开项目结构

![](https://pic1.imgdb.cn/item/69049fe33203f7be00ba3208.png) 

3. 添加web模块

![](https://pic1.imgdb.cn/item/6904a02f3203f7be00ba3576.png)

此步如果不显示web模块, 一个原因是idea版本问题, 另一个原因是没有安装相关插件, 可以到idea的插件商店看看是否禁用了某个插件

4. 查看项目结构

![](https://pic1.imgdb.cn/item/6904a1553203f7be00ba476f.png) 

5. 配置tomcat

tomcat安装教程不再赘述 , 需要注意tomcat与jdk版本兼容问题 , `jdk1.8`使用`tomcat10`以下版本

![](https://pic1.imgdb.cn/item/6904a1da3203f7be00ba4e0d.png) 

如果你已经配置好了tomcat环境变量 , 这里idea会自动获取tomcat安装目录

6. 运行配置

![](https://pic1.imgdb.cn/item/6904a2b03203f7be00ba5566.png) 

![](https://pic1.imgdb.cn/item/6904a35b3203f7be00ba59cd.png) 

7. 部署配置

![](https://pic1.imgdb.cn/item/6904a3cf3203f7be00ba5c10.png) 

![](https://pic1.imgdb.cn/item/6904a3cf3203f7be00ba5c0e.png) 

选择项目文件下刚才导入模块时创建的根目录web

---

到此基本配置完成, 可以正常运行了



# 使用IDEA克隆项目

![](https://pic1.imgdb.cn/item/6904a5083203f7be00ba6462.png) 

![](https://pic1.imgdb.cn/item/6904a5083203f7be00ba6461.png) 
