## CSS

----

- css指层叠样式表（Cascading  Style  Sheets）
- 样式定义如何显示HTML元素

#### 语法

CSS规则由两部分组成：选择器 + 声明

选择器：用来定位HTML文档中的具体元素

声明：键值对，规定了具体的样式

```css
body {
  color: #000;
  background: #fff;
  margin: 0;
}
```

body就是选择器，花括号里面的内容就是声明

#### 选择器

- 元素选择器

  通过html的元素中标签名来定位

  ```css
  p {color : red;}
  <p>cssTest</p>
  ```

- 选择器分组

  一次定义多个元素

  ```css
  body, h1, h2 {color : yellow;}
  ```

- 类选择器

  最常用的选择器，需要事先在HTML元素的标签属性中定义标记（通过class属性，react中要使用className属性）

  ```css
  .cssTest {color : red;}
  <p class="cssTest">hello world</p>
  ```

- 元素+类选择器

  同时使用元素和类进行选择

  ```css
  h1.cssTest {color : red;}
  h2.cssTest {colre : yellow;}
  <h1 class="cssTest">this is red</h1>
  <h2 class="cssTest">this is yellow</h1>
  ```

- id选择器

  通过html元素的id来定位html元素，在CSS通过 # 来定义id选择器

  ```css
  #red {clolr : red}
  <p id="red">这段文字是红色的</p>
  ```

  ​

