## *React简介*

---
React 是目前最热门的前端框架。

- Facebook 公司2013年推出
- 现在最好的社区支持和生态圈
- 大量的第三方工具

![react-logo.png](https://zos.alipayobjects.com/basement/skylark/0ad680ae14794571073162164d39cd/attach/5715/1353/react-logo.png)

---

## React 的优点

- 组件模式：代码复用和团队分工
- 虚拟 DOM：性能优势
- 移动端支持：跨终端

---

## React 的缺点

- 学习曲线较陡峭
- 全新的一套概念，与其他所有框架截然不同
- 只有采用它的整个技术栈，才能发挥最大威力

总结：React 非常先进和强大，但是学习和实现成本都不低

---

## JSX 语法

React 使用 JSX 语法，JavaScript 代码中可以写 HTML 代码。

```javascript
let myTitle = <h1>Hello, world!</h1>;
```

---

## JSX 语法解释

（1）JSX 语法的最外层，只能有一个节点。

```javascript
// 错误
let myTitle = <p>Hello</p><p>World</p>;
```

（2）JSX 语法中可以插入 JavaScript 代码，使用大括号。

```javascript
let myTitle = <p>{'Hello ' + 'World'}</p>
```

---

## Babel 转码器

JavaScript 引擎（包括浏览器和 Node）都不认识 JSX，需要首先使用 Babel 转码，然后才能运行。

```html
<script src="react.js"></script>
<script src="react-dom.js"></script>
<script src="babel.min.js"></script>
<script type="text/babel">
  // ** Our code goes here! **
</script>
```

React 需要加载两个库：React 和 React-DOM，前者是 React 的核心库，后者是 React 的 DOM 适配库。

Babel 用来在浏览器转换 JSX 语法，如果服务器已经转好了，浏览器就不需要加载这个库。

---

### 课堂练习：JSX 语法

浏览器打开 https://riddle.alibaba-inc.com/riddles/new 新建一个 riddle 代码片段，将下列代码复制粘贴至 riddle 中：
```jsx
let dom = <p>Hello World!</p>;

ReactDOM.render(dom, document.getElementById('mountNode'));
```
修改以上代码，看看运行效果，尝试体会 JSX 不同的写法。
[语法解释]

ReactDOM.render方法接受两个参数：一个虚拟 DOM 节点和一个真实 DOM 节点，作用是将虚拟 DOM 挂载到真实 DOM。

---

## React 组件

React 允许用户定义自己的组件，插入网页。

### 课堂练习：组件

浏览器打开 https://riddle.alibaba-inc.com/riddles/new 新建一个 riddle 代码片段，将下列代码复制粘贴至 riddle 中：
```jsx
class MyTitle extends React.Component {
  render() {
    return <h1>Hello World</h1>;
  }
};

ReactDOM.render(
  <MyTitle/>,
  document.getElementById('mountNode')
);
```
修改以上代码，看看运行效果，尝试体会 JSX 不同的写法。

---

## React 组件的语法解释

- `class MyTitle extends React.Component`是 ES6 语法，表示自定义一个`MyTitle`类，该类继承了基类`React.Component的所有`属性和方法。
- React 规定，自定义组件的第一个字母必须大写，比如`MyTitle`不能写成`myTitle`，以便与内置的原生类相区分。
- 每个组件都必须有`render`方法，定义输出的样式。
- `<MyTitle/>`表示生成一个组件类的实例，每个实例一定要有闭合标签，写成`<MyTilte></MyTitle>`也可。

---

## 组件的参数

组件可以从外部传入参数，内部使用`this.props`获取参数。

### 课堂练习：向组件传参数

浏览器打开 https://riddle.alibaba-inc.com/riddles/new 新建一个 riddle 代码片段，将下列代码复制粘贴至 riddle 中：
```jsx
class MyTitle extends React.Component {
  render() {
    return <h1 style={{color: this.props.color}}>
      Hello {this.props.message}
    </h1>;
  }
};

ReactDOM.render(
  <MyTitle color='red' message='world'/>,
  document.getElementById('mountNode')
);

```
修改以上代码，看看运行效果，尝试向组件传递不同的参数，修改组件逻辑等。

---

[语法解释]

- 组件内部通过this.props对象获取参数。

[练习]

- 将组件的颜色，从红色（red）换成黄色（yellow）。

---



## 组件的状态

组件往往会有内部状态，使用`this.state`表示。

### 课堂练习：组件实战

浏览器打开 https://riddle.alibaba-inc.com/riddles/new 新建一个 riddle 代码片段，将下列代码复制粘贴至 riddle 中：
```jsx
class MyTitle extends React.Component {
  constructor(...args) {
    super(...args);
    this.state = {
      text: 'Hello State',
      isClicked: false
    };
  }

  handleClick() {
    let isClicked = !this.state.isClicked;
    this.setState({
      isClicked : isClicked,
      text: isClicked ? 'Hello State' : Date()
    });
  }

  render() {
    return <h1 onClick={this.handleClick.bind(this)}>
      Click to: {this.state.text}
    </h1>;
  }
}

ReactDOM.render(
  <MyTitle />,
  document.getElementById('mountNode')
);
```
修改以上代码，看看运行效果，尝试向组件各种状态并修改处理逻辑等。

---

## 语法解释

```javascript
  class MyTitle extends React.Component {
    constructor(...args) {
      super(...args);
      this.state = {
        name: '访问者'
      };
    }
    // ...
```

`constructor`是组件的构造函数，会在创建实例时自动调用。`...args`表示组件参数，`super(...args)`是 ES6 规定的写法。`this.state`对象用来存放内部状态，这里是定义初始状态。

```html
<div>
  <input
    type="text"
    onChange={this.handleChange.bind(this)}
  />
  <p>你好，{this.state.name}</p>
</div>;
```

`this.state.name`表示读取`this.state`的`name`属性。每当输入框有变动，就会自动调用`onChange`指定的监听函数，这里是`this.handleChange`，`.bind(this)`表示该方法内部的`this`，绑定当前组件。

```javascript
handleChange(e) {
  let name = e.target.value;
  this.setState({
    name: name
  });
}
```

`this.setState`方法用来重置`this.state`，每次调用这个方法，就会引发组件的重新渲染。

---

## 组件的生命周期

React 为组件的不同生命阶段，提供了近十个钩子方法。

- `componentWillMount()`：组件加载前调用
- `componentDidMount()`：组件加载后调用
- `componentWillUpdate()`: 组件更新前调用
- `componentDidUpdate()`: 组件更新后调用
- `componentWillUnmount()`：组件卸载前调用
- `componentWillReceiveProps()`：组件接受新的参数时调用

我们可以利用这些钩子，自动完成一些操作。

---

### 课堂练习：组件的生命周期

浏览器打开 https://riddle.alibaba-inc.com/riddles/new 新建一个 riddle 代码片段，将下列代码复制粘贴至 riddle 中：
```jsx
import $ from 'jquery';

class RepoList extends React.Component {
  constructor(...args) {
    super(...args);
    this.state = {
      loading: true,
      error: null,
      data: null,
    };
  }

  componentDidMount() {
    const url = 'https://os.alipayobjects.com/rmsportal/nnUJCKmLCyQHCex.json';
    $.getJSON(
        url
      ).done(
        value => this.setState({
          loading: false,
          data: value,
        })
      ).fail(
        (jqXHR, textStatus) => this.setState({
          loading: false,
          error: jqXHR.status,
        })
      );
  }

  render() {
    if (this.state.loading) {
      return <span>Loading...</span>;
    } else if (this.state.error !== null) {
      return <span>Error: {this.state.error}</span>;
    } else {
      /* 你的代码填入这里 */
      return (
        <div>
          请在这里显示加载的结果。
        </div>
      );
    }
  }
}

ReactDOM.render(<RepoList />, document.getElementById('mountNode'));
```

这个作业要求完成如何从服务器获取数据，并展示在前端。组件的大框架已经写好，请在注释处完成必要的代码，实现作业要求的功能。


[注意事项]

```javascript
componentDidMount() {
  const url = '...';
  $.getJSON(url)
    .done()
    .fail();
}
```

- `componentDidMount`方法在组件加载后执行，只执行一次。本例在这个方法里向服务器请求数据，操作结束前，组件都显示`Loading`。
- `$.getJSON`方法用于向服务器请求 JSON 数据。本例的数据从 Github API 获取，可以打开源码里面的链接，看看原始的数据结构。

[练习]

1. 本例的 JSON 数据是 Github 上面最受欢迎的 JavaScript 项目。请在网页上显示一个列表，列出这些项目。

[提示]

（1） `this.state.loading`记录数据加载是否结束。只要数据请求没有结束，`this.state.loading`就一直是`true`，网页上显示`loading`。

（2） `this.state.error`保存数据请求失败时的错误信息。如果请求失败，`this.state.error`就是返回的错误对象，网页上显示报错信息。

（3） `this.state.data`保存从服务器获取的数据。如果请求成功，可以先用`console.log`方法，将它在控制台里打印出来，看看数据结构。

```javascript
render() {
  // 加一行打印命令，看看数据结构
  console.log(this.state.data);
  return {
  // ...
```

（4） `this.state.data`里面的`this.state.data.items`应该是一个数组，保存着每个项目的具体信息。可以使用`forEach`方法进行遍历处理。

```javascript
var projects = this.state.data.items;
var results = [];
projects.forEach(p => {
  var item = <li>{p.name}</li>;
    results.push(item);
});
```

（5）然后，将上一步的`results`插入网页即可。

```javascript
<div>
  <ul>{results}</ul>
</div>
```
