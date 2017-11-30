一、	作品运行环境

使用软件/操作平台

体系结构	B/S

开发平台	ArcGIS Runtime SDK for Android  Version 100.0.0、ksoap2-android-assembly-3.5.0-jar-with-dependencies

开发工具	ArcGIS 10.1+系列软件  VS2012  Android Studio

开发语言	C# 、java

运行环境	Windows7\8\10  Arcgis for Server 10.1+

数据库	SQL Server2012

其他（可扩充）


二、作品部署说明

⑴ 关系数据库导入

    将“相关数据”文件夹下“SQLServer数据”文件内“Android”数据库附加到SQL Server2012数据库中。
	
    ⑵ 地图服务发布（接口为http://localhost:6080）
	
    将“相关数据”文件夹下“NetWork Analyst”文件内QDnet.mxd文档发布功能（Capabilities）包含“Network Analysis”，命名为“QD_ND”的地图服务。
	
⑶ 源代码相关设置

 将“开发源代码” 文件夹下“StockManageWebservice”文件内StockManageWebservice.sln用VS2012打开，将项目“StockManageWebservice”内“Web.config”内容修改为电脑SQL Server2012相应账号、密码。如下：
 
 	 <connectionStrings>
	 
    		<add name="MyDB" connectionString="Data Source=zx-PC;Initial Catalog=android;User ID=sde;Password=10086"/>
			
 </connectionStrings>
 
然后发布服务。


用AndroidStudio将“开发源代码” 文件夹下“TestExtent21”文件打开，在Android模式下，将项目“res/values”内“strings”内容修改为电脑相应IP地址。下面黄色字符为修改部分，如下：

	<string name="routing_service">
	
	http://192.168.72.247:6080/arcgis/rest/services/QD_ND/NAServer/Route
	
	</string>
	
将项目“java/ testextent”内“DoWork”内容修改为电脑相应IP地址。下面黄色字符为修改部分，如下：private final String Planurl = "http://192.168.72.247:801/Service1.asmx";

private final String Positionurl = "http://192.168.72.247:801/Service1.asmx";


⑷ 运行程序、操作

   由于地图底图为ArcGISOnline在线地图，ArcGIS Runtime SDK for Android引用为官方地址，所以程序运行环境需要连接网络。                 
   
安装App，点击路径button，出现查询页面，通过修改不同的属性可以进行查询，通过点击列表，可以查看景点和酒店位置，并可查询出最短路线；通过点击景点、酒店图标可以查看景点介绍；
