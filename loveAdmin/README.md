部署是需要注意的文件:
1.applicationContext.xml的数据源配置
2.applicationContext.xml中全局bean的上传路径和URL路径
3.spring-security.xml的验证码验证注意要开启
4.urls.properties文件中的域名
5.错误页面的地址
6.js/commons/url.js


源码改动:
jquery.easyui.min.js的3334行注释掉_273(0);---->首页刷新时默认菜单关闭.