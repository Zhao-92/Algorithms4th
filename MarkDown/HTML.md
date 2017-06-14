### *HTML*

-------------------------------------

#####  *HTML标签*

标签 = 元素 + 属性

- <html> xxxxxxxxxxxxxx </html>  定义了整个HTML文档
- <body> xxxxxxxxxxxxxx </body>  定义了HTML文档的主体
- <p> xxxxxxxx </p>  定义了HTML文档中的一个段落
- <a href="xxxxxx"> xxxxxxx </a  > 定义了链接：链接地址和链接名字
- <h1>  xxxxxxx </h1>  定义了标题，有<h1>到<h6>
- <hr />  水平线
- <br />  折行显示
- <!-- xxxxxxxxxxxxxx -->   注释
- <img src ="xxxxx.png"  width='100' height='140' />   插入图片
- <div>  xxxxxxx  </div>  对HTML进行分类，使得能够用CSS给类设置样式
- <form>  xxxxxxxxxxxxxx  </form>  用于定义表单



##### *HTML元素*

HTML 元素指的是从开始标签（start tag）到结束标签（end tag）的所有代码。



##### *HTML属性*

属性在**开始标签**中规定，总是采用**键值对**的形式出现，如<a href="www.alipay.com"> This is s link </a.>  (a后面的点为了防止mk识别成link，估计加上去的)



##### *HTML表单*

表单用来搜集不同类型的用户交互（如：输入框）

- **< input > 元素**

  input是最重要的表单元素，他有很多不同的type属性（如：text、radio、submit）

  ```HTML
  <!DOCTYPE html>
  <html>
  <head>
  	<meta charset="UTF-8">
  	<title>BOY-workhard</title>
  </head>
  <body>
      <form>
          First name :   
          <input type="text">
      </form>
  <body>
  </html>
  ```

  - Action属性

    定义在提交表单时执行的动作 ，如：<form  action = "action_page.php"> 制定了通过哪个服务器脚本来处理提交的表单

  - Method属性

    规定了提交表单时所用的HTTP方法（GET / POST） ，如：<form  action = "action_page.php" method = "POST">

  - Name属性

    每个输入字段必须设置name属性，不然无法正确提交

  - Value属性

    规定了输入字段的初始值

- **< select > 元素**

  定义了下拉列表



##### *HTML5*

HTML5是最新的HTML标准，新特性：新的语义元素、新的表单控件、强大的多媒体支持、新的API

- 向HTML添加新元素，如下添加了名为<myHero>新元素

  ```HTML
  <head>
    <title>Creating an HTML Element</title>
    <script>document.createElement("myHero")</script>
    <style>
    myHero {
      display: block;
      background-color: #ddd;
      padding: 50px;
      font-size: 30px;
    } 
    </style> 
  </head>
  ```

- HTML本地存储

  HTML5之前程序数据只能存储在cookie中。HTML本地存储更安全

  通过两个对象进行存储：windows.localStorage + windows.sessionStorage

- 应用缓存

  通过创建cache manifest文件进行缓存

- Web Worker

  当在 HTML 页面中执行脚本时，页面是不可响应的，直到脚本已完成。Web worker 是运行在后台的 JavaScript，独立于其他脚本，不会影响页面的性能。您可以继续做任何愿意做的事情：点击、选取内容等等，而此时 web worker 运行在后台。