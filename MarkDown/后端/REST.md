### REST

---

REST -- REpresentational State Transfer 直接翻译：表现层状态转移。

互联网通信协议HTTP协议，是一个无状态协议。这意味着，所有的状态都保存在服务器端。因此，**如果客户端想要操作服务器，必须通过某种手段，让服务器端发生"状态转化"（State Transfer）。而这种转化是建立在表现层之上的，所以就是"表现层状态转化"。**

**REST精髓**:URL定位资源，用HTTP动词（GET,POST,DELETE,DETC）描述操作

- REST描述的是在网络中client和server的一种交互形式；REST本身不实用，实用的是如何设计 RESTful API（REST风格的网络接口）

- 可以将web、IOS、Andord统一使用一套API




RESTful架构特点：

- 每一个URL代表一种资源
- 客户端和服务端之间，传递这种资源的某种表现层
- 客户端通过四个HTTP动词，对服务器资源进行操作，实现“表现层状态转化”