### **SOFA学习笔记**

------------------

SOFA 源自于 Service Oriented Fabric Architecture，即面向服务的架构。SOFA 运行在 Cloudengine 分布式应用容器之上，该应用容器提供了应用基础的运行环境，负责加载应用使用的基础组件，Msgbroker、RPC 、MVC 等均是应用所需的基础组件。

#### *RPC是什么*   

远程过程调用协议RPC（Remote Procedure Call Protocol)是指远程过程调用，也就是说两台服务器A、B，一个应用部署在A服务器上，想要调用B服务器上应用提供的函数/方法，由于不在一个内存空间，不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。



#### *为什么RPC*

就是无法在一个进程内，甚至一个计算机内通过本地调用的方式完成需求，比如不同系统间的通信，甚至不同组织间的通信。由于计算能力需要横向扩展，需要在多台机器组成集群上部署应用。



与Spring相比SOFA的优势在于将模块化更进一步，在一个SOFA应用中，应用中的每个模块都含有各自的Spring上下文，模块之间的调用通过本地服务的方式完成。



### SOFA模块



SOFA基于OSGI实现bundle

https://lark.alipay.com/yutong.zyx/note/sofa-discover	





