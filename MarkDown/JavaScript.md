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

 -  JS是动态类型语言，变量都是声明为var

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

