## Spring MVC 4.2.4 中文文档

---

### 2.3 控制器实现

控制器作为应用程序逻辑的处理入口，它会负责去调用你已经实现的一些服务。

Spring引入了基于注解的编程模型，可以在你的控制器实现上添加@RequestMapping、@RequestParam、@ModelAttribute等注解。

#### 2.3.1 使用@Controller注解定义一个控制器

`@Controller`注解表明了一个类是作为控制器的角色而存在的。Spring不要求你去继承任何控制器基类，也不要求你去实现Servlet的那套API。