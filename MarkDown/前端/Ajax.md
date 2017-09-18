## **Ajax**

---

Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）。

AJAX 不是新的编程语言，而是一种使用现有标准的新方法。

AJAX 是与服务器交换数据并更新部分网页的艺术，在不重新加载整个页面的情况下。



#### *XMLHttpRequest*

XMLHttpRequest 是 AJAX 的基础。XMLHttpRequest 用于在后台与服务器交换数据。这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。

**发送请求**

```javascript
//创建建XMLHttpRequest对象
variable=new XMLHttpRequest();
//向服务器发送请求
xmlhttp.open("GET","test.json",true);
xmlhttp.send();
```

| 方法                           | 描述                                       |
| :--------------------------- | ---------------------------------------- |
| open(*method*,*url*,*async*) | 规定请求的类型、URL 以及是否异步处理请求             *method*：请求的类型；GET 或 POST            *url*：文件在服务器上的位置       *async*：true（异步）或 false（同步） |
| send(*string*)               | 将请求发送到服务器。*string*：仅用于 POST 请求           |

**GET OR POST？**

与POST相比，GET更简单也更快，并且大部分情况下都能使用

然而，以下情况时使用POST：

- 无法缓存的文件
- 向服务器发送大量数据（POST没有数据量的限制）
- 发送包含位置字符的用户输入时，POST比GET更稳定更可靠

**获取服务器相应**

通过XMLHttpRequest对象的responseText和responseXML属性，分别获得字符串形式的响应数据和XML形式的响应数据



