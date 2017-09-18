## **ES6**

---

ECMAScript 6（以下简称ES6）是JavaScript语言的下一代标准。因为当前版本的ES6是在2015年发布的，所以又称ECMAScript 2015。

#### **核心内容**

[ES6核心内容](http://www.jianshu.com/p/ebfeb687eb70)

ES6模块主要有两个功能：export和import

export用于对外输出本模块（一个文件可以理解为一个模块）变量的接口

import用于在一个模块中加载另一个含有export接口的模块

使用import命令的时候，用户需要知道a.js所暴露出的变量标识符，否则无法加载。可以使用export default命令，为模块指定默认输出，这样就不需要知道所要加载模块的变量名。如：

```javascript
//a.js
var sex="boy";
export default sex

// b.js
import any from "./a.js"
import any12 from "./a.js"
console.log(any,any12) // boy,boy
```





