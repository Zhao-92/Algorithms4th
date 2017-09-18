Java反射机制

----

http://blog.csdn.net/qq_32166627/article/details/53768451



### 创建Class对象

#### getClass( )

对于已知对象，使用Object类的getClass( )方法

```
String str = "Hello World";
Class c = str.getClass();
```



#### forName( )

调用Class的静态方法forName获得类名对应的Class对象

```
Class c = Class.forName(java.util.Date);
```



#### class属性

直接访问类的class属性

```
Class c = String.class;
```



### Class对象常用操作

- String  getName( )获取类名

  返回的类名(包含包名)

- T  newInstance( )创建类的实例

  调用默认的构造器初始化新对象，返回这个新对象的引用

- Field  getField(String) 、Field[ ]  getFields( )

  通过属性名返回类的指定属性，或者所有属性。只能获取到public类型的属性

- Method  getMethod(String)、Methods[ ]  getMethods( )

  通过方法名返回类的指定方法，或者所有方法。只能获取到public类型的方法

- Constructor  getConstructor(Class)、Constructor[ ]  getConstructors( )

  通过构造器传入参数类型的Class对象获取类的指定构造函数，或者所有构造函数

- getDeclaredField( )、getDeclaredFields( )、getDeclaredMethod( )、getDeclaredMethods( )

  可以获取非public属性、方法



### reflect包常用类

#### Field

返回class对象的公有域（类属性+实例属性），通过Class类的getDeclaredField( )方法获得

##### 

#### Method



#### Constructor

通过Class类的getConstructor( )获取构造器函数，无参时获取默认无参构造器，有参时按照参数获取有参构造器，参数必须是Class对象。

- newInstance( )方法

  通过Constructor对象的newInstance( )方法，创建对象，相当于调用带参数的构造函数