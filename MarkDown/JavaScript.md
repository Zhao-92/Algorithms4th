### **JavaScript**

----------------------

#### *简介*

- 写入HTML输出
- 对事件作出反应
- 改变HTML内容  -> <a href="http://www.w3school.com.cn/tiy/t.asp?f=js_intro_inner_html"> demo1 </a>  <a href="http://www.w3school.com.cn/tiy/t.asp?f=js_lightbulb"> demo2 </a>
- 改变HTML样式
- 验证输入



#### *实现*

- 脚本必须位于<script> 与 </script> 标签之间
- 可以放在<head> 或<body>中；也可以放到外部文件中，通过HTML中src调用<script  src="myScript.js"></script>，这种情况适用于多个页面调用的js



#### *变量*

-   JS是动态类型语言，变量都是声明为var

    var  x = 1.23;

    var  x = 123;

    var  x = "Hello";

    var  x = true;

- 数组

  var  cars  =  new  Array( ); cars[0] = "Audi"; cars[1] = "Bmls";

  var  boys =  new  Array("Bob","Lee","Jack");

- 对象

  通过花括号分隔，花括号内部采用键值对的方式。JS中所有事物都是对象

  var  person = { firstname : "Bill" , lastname : "Gates" , id : 5566 };


#### *变量的数据类型*

虽然JS是动态语言，但他的变量是有数据类型：数值型、字符型、布尔型、undefined、null、array、object、function


#### *查找HTML元素*

- id

  var  x = document.getElementById("intro");

- 标签名

  var x=document.getElementById("main");
  var y=x.getElementsByTagName("p");

- 类名

  ​







## JavaScript 代码块

JavaScript 语句通过代码块的形式进行组合。

块由左花括号开始，由右花括号结束。

块的作用是使语句序列一起执行



===  全等：值和类型都相等



反斜杠用来换行，下面的例子可以正常运行

```
document.write("Hello \
World!");
```





JavaScript是一种小型的、轻量级的、面向对象的、跨平台的客户端脚本语言，可以跨平台运行（js运行在node.js上，node相当于JVM），浏览器就是一个翻译器，可以翻译Html、CSS、JavaScript



Js功能：表单验证、动态HTML、交互



通过<script> </script>来引入JS程序代码，like：

```HTML
<script type="text/javascript">
    //在<body>中输出一句话
    document.write("我是通过js写出来的");
</script>
```



#### *函数和方法*

```JavaScript
Boolean(a)    //函数是可以独立使用的。
document.write(a)    //方法不能独立使用，方法是必须要属于哪一个对象。
```

##### **函数定义**

关键字：function

如果调用函数后本身的值就是undefined

```javascript
var a = function(){
            window.alert("OK");
        } //将函数赋给变量a，那么a就是函数型变量。
a();    //调用函数
```



#### *自定义对象*

##### **new创建**

```javascript
// 1. 使用new关键字结合构造函数Object()来穿件一个空的对象。
var obj = new Object();
// 2. 增加属性
obj.name = "张三"
obj.age = 24;
obj.sex = "男";

// 3.增加方法: 将一个函数定义赋给对象属性，那么该属性变成方法。
obj. showInfo = function(){
                    window.alert();
                }
// 4.调用对象方法
obj.showInfo();
```

##### **大括号{ }创建**

```javascript
var obj = {
    name : "张三";
    sex  : "男";
    age  : 24;
    showInfo : function(){
                    window.alert();
    }
}
```

#### *JS内置对象*

- String

- Date

- Array

- Boolean

- Number

- Math

#### *form对象*

一个<form>标记，就是一个<form>对象

-   form对象属性

  - name：表单名称，让js来控制表单
  - action：表单的数据处理程序
  - method：表单的提交方式（GET/POST）
  - enctype：表单数据的编码方式
-   form对象的方法

    -   submit( ) ：提交表单
    -   reset( )：重置表单


- form对象的事件
    - onSubmit：点击提交按钮，且数据发往服务器之前，用来表单验证
    - onreset：点击重置时

#### *input对象*

一个<input>标记，就是一个input对象



#### *select对象*



#### *BOM-Brower Object Model*

浏览器对象模型：主要是提供了访问和操作浏览器各组件的方式

window   浏览器窗口

- location   地址栏
- history   浏览历史
- screen   显示器屏幕
- navigator   浏览器软件
- document   网页

其中window组件是最顶层组件，是其他组件的父组件

```javascript
window.document.write("OK"); //window是document的父组件
document.write("ok");  //window作为最顶层对象，可以省略
```



#### *DOM-Document Object Model*

文档对象模型：主要提供了访问和操作HTML标记的方式

HTML标记：图片标记、表格标记、表单标记、body等



#### *变量提升*

JavaScript引擎的工作方式是，先解析代码，获取所有被声明的变量，然后再一行一行地运行。这造成的结果，就是所有的变量的声明语句，都会被提升到代码的头部，这就叫做变量提升



$() 方法是在DOM中使用过于频繁的 document.getElementById() 方法的一个便利的简写，就像这个DOM方法一样，这个方法返回参数传入的id的那个元素。





















