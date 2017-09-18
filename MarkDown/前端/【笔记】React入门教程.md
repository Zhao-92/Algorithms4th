### 【笔记】React入门教程

---

#### JSX

利用 JSX 编写 DOM 结构，可以用原生的 HTML 标签，也可以直接像普通标签一样引用 React 组件。这两者约定通过大小写来区分，小写的**字符串**是 HTML 标签，大写开头的**变量**是 React 组件。



#### Reac组件

组件有两个核心概念：

- props
- state

一个组件就是通过这两个属性的值在 `render` 方法里面生成这个组件对应的 HTML 结构。

*注意：组件生成的 HTML 结构只能有一个单一的根节点。*

**props**

`props` 就是组件的属性，由外部通过 JSX 属性传入设置，一旦初始设置完成，就可以认为 `this.props` 是不可更改的，所以**不要**轻易更改设置 `this.props` 里面的值（虽然对于一个 JS 对象你可以做任何事）

在props中有三个默认的对象：dispatch、history、location

http://www.cnblogs.com/ppforever/p/5229045.html

- history 对象，它提供了很多有用的方法可以在路由系统中使用，比如刚刚用到的 `history.replaceState`，用于替换当前的 URL，并且会将被替换的 URL 在浏览器历史中删除。函数的第一个参数是 state 对象，第二个是路径；
- location 对象，它可以简单的认为是 URL 的对象形式表示，这里要提的是 `location.state`，这里 state 的含义与 HTML5 history.pushState API 中的 state 对象一样。每个 URL 都会对应一个 state 对象，你可以在对象里存储数据，但这个数据却不会出现在 URL 中。实际上，数据被存在了 sessionStorage 中；

**state**

`state` 是组件的当前状态，可以把组件简单看成一个“状态机”，根据状态 `state` 呈现不同的 UI 展示。一旦状态（数据）更改，组件就会自动调用 `render` 重新渲染 UI，这个更改的动作会通过`this.setState` 方法来触发。



#### Dva

Dva是对redux的一层浅封装，对Redux概念做了简化

1 https://lark.alipay.com/dva/docs/getting-started

2 https://lark.alipay.com/dva-training/guides/ihgore





https://lark.alipay.com/dva/docs/getting-started

https://github.com/dvajs/dva-knowledgemap

https://private-alipayobjects.alipay.com/alipay-rmsdeploy-image/skylark/pdf/254/eb0c99d2414e7425.pdf

https://lark.alipay.com/dva-training/guides/gmvksd