### **React**

--------

<a href="https://hulufei.gitbooks.io/react-tutorial/"> React入门教程-Gitbooks</a>

##### *React概览*

React 的核心思想是：封装组件。
各个组件维护自己的状态和 UI，当状态变更，自动重新渲染整个组件。



React 大体包含下面这些概念：

- 组件
- JSX
- Virtual DOM
- Data Flow



props 是组件包含的两个核心概念之一，另一个是 state（这个组件没用到）。可以把 props 看作是组件的配置属性。

当组件状态 state 有更改的时候，React 会自动调用组件的 render 方法重新渲染整个组件的 UI。



##### *React组件*

可以说一个React应用就是构建在React组件之上的。组件有两个核心概念：props和state，一个组件就是通过这两个属性在render方法里面生成这个组件对应的HTML结构。

- props

  props就是组件的属性，由外部通过JSX属性传入设置，一旦初始化设置完成，就可以认为this.props是不可更改的。

- state

  state是组件的当前状态，可以把组件简单看成一个“状态机”，根据状态state呈现不同的UI展示。

一条原则：组件要尽可能的少，这样组件逻辑易维护。



##### *组件的生命周期*

一个组件类由 extends Component 创建，并且提供一个 render 方法以及其他可选的生命周期函数、组件相关的事件或方法来定义

```
class Counter extends Component {
  constructor(props) {
    super(props);
    this.state = { count: props.initialCount };
  }

  render() {
    // ...
  }
}
```





