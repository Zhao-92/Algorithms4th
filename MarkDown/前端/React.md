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



## React 的核心思想

View 是 State 的输出。

```
view = f(state)
```

上式中，`f`表示函数关系。只要 State 发生变化，View 也要随之变化。

React 的本质是将图形界面（GUI）函数化。



#### **组件间通信**

组件间存在三种通信：

- 向子组件发消息
- 向父组件发消息
- 想其他组件发消息

React只提供一种通信手段：传参



#### **状态的同步**

通信的本质是状态的同步

同步的基本方法：找到通信双方最近的公共父组件，通过更新他的state使双方同步



#### **Reducer函数**

reducer是个纯函数，用来接收action，算出新的state



#### *JSX*

因为 React 独有的 JSX 语法，跟 JavaScript 不兼容。凡是使用 JSX 的地方，都要加上 `type="text/babel"` 



JSX 的基本语法规则：遇到 HTML 标签（以 `<` 开头），就用 HTML 规则解析；遇到代码块（以 `{` 开头），就用 JavaScript 规则解析。



#### *ReactDOM.render( )*

ReactDOM.render 是 React 的最基本方法，用于将模板转为 HTML 语言，并插入指定的 DOM 节点



#### **组件**

React 允许将代码封装成组件（component），然后像插入普通 HTML 标签一样，在网页中插入这个组件。**所有组件类都必须有自己的render方法，用于输出组件**

```jsx
// 声明组件类
var HelloMessage = React.createClass({
  render: function() {
    return <h1>Hello {this.props.name}</h1>;
  }
});

ReactDOM.render(
  // 生成组件类的实例
  <HelloMessage name="John" />,
 document.getElementById('example')
);

```

- 组件类的第一个字母必须大写
- 组件类只能包含一个顶层标签**（？）**
- 生成组件类实例时，可以增加属性，并通过this.props对象获取
- this.props.children属性表示该组件所有子节点



#### **PropTypes**

组件类的`PropTypes`属性，就是用来验证组件实例的属性是否符合要求。



#### **获取真实的DOM节点**

组件并不是真实的 DOM 节点，而是存在于内存之中的一种数据结构，叫做虚拟 DOM （virtual DOM）。只有当它插入文档以后，才会变成真实的 DOM 。



#### **组件状态：this.state**

组件免不了要与用户互动，React 的一大创新，就是将组件看成是一个状态机，一开始有一个初始状态，然后用户互动，导致状态变化，从而触发重新渲染 UI

由于 `this.props` 和 `this.state` 都用于描述组件的特性，可能会产生混淆。一个简单的区分方法是，`this.props` 表示那些一旦定义，就不再改变的特性，而 `this.state` 是会随着用户互动而产生变化的特性。



#### **组件生命周期**

生命周期一共有三个状态：

- Mounting：已插入真实DOM
- Updating：正在被重新渲染
- Unmountyng：已移除正式DOM

每个状态都提供两种处理函数，will函数在进入函数之前调用，did函数在进入状态之后调用，共计5重处理函数

- componentWillMount( )
- componentDidMount( )
- componentWillUpdate( )
- componentDidUpdate( )
- componentWillUnmount( )

React还提供两种特殊状态的处理函数

- componentWillReceiveProps（Object nextProps）已加载组件收到新的参数时调用
- shouldComponentUpdate(object nextProps, object nextState)：组件判断是否重新渲染时调用



#### **Ajax**

组件的数据来源，通常是通过 Ajax 请求从服务器获取，可以使用 `componentDidMount` 方法设置 Ajax 请求，等到请求成功，再用 `this.setState` 方法重新渲染 UI



#### **三种变量**

- const 定义的变量不可以修改，而且必须初始化。
- var 定义的变量可以修改，如果不初始化会输出undefined，不会报错。
- let是块级作用域，函数内部使用let定义后，对函数外部无影响。



















