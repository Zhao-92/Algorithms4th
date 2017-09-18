### DVA

---

#### React没有解决的问题

React本身只是一个DOM的抽象层，使用组件构建虚拟DOM

- 通信：组件之间如何通信
- 数据流：数据如何和视图串联？路由和数据如何绑定？如何编写异步逻辑



#### 通信问题

组件间三种通信：

- 向子组件发消息
- 向父组件发消息
- 向其他组件发消息

React只提供一种通信手段：传参



#### 数据流

Flux，单项数据流方案，以Redux为代表

现在最流行的React应用架构：

路由：React-Router

架构：Redux

异步操作：Redux-saga

![React-数据流向](/Users/ethan/Study/Github/HZ-study/MarkDown/前端/React-数据流向.png)



#### dva是什么

dva = React-Router + Redux + Redux-sage



#### 核心概念

- State：一个对象，保存整个应用状态
- View：React组件构成的视图层
- Action：一个对象，描述事件
- connect方法：一个函数，绑定State到View
- dispatch方法：一个函数，发送Action到State