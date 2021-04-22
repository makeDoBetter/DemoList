# Spring注解

## pojo类

### @Data

提供对简单pojo类歌变量的get，set，以及toString方法

### @NoArgsConstructor 

提供一个无参构造方法

### @AllArgsContructor

提供一个包含所有参数的构造方法

### @JsonInclude

@JsonInclude(Include.NON_NULL) 这个注解放在类头上就可以解决。 实体类与json互转的时候 属性值为null的不参与序列化。

此注解写于类前

实际效果：

![img](https://images2017.cnblogs.com/blog/959364/201712/959364-20171213174854160-1961102623.png)

![img](https://images2017.cnblogs.com/blog/959364/201712/959364-20171213174651754-390588378.png)

### @JsonFormat与@DateTimeFormat注解

从数据库获取时间传到前端进行展示的时候，我们有时候可能无法得到一个满意的时间格式的时间日期，在数据库中显示的是正确的时间格式，获取出来却变成了很丑的时间戳，@JsonFormat注解很好的解决了这个问题，我们通过使用@JsonFormat可以很好的解决：`后台到前台时间格式保持一致`的问题。

我们在使用WEB服务的时，可能会需要用到，传入时间给后台，比如注册新用户需要填入出生日期等，这个时候前台传递给后台的时间格式同样是不一致的，而我们的与之对应的便有了另一个注解，@DataTimeFormat便很好的解决了这个问题

这两个属性写于成员变量之前

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
private Date symstarttime;
```



## Controller类

### @RestController

相当于@Controller+@ResponseBody两个注解结合到一起。

当只使用@RestController注解时，对应的controller类中的方法是无法跳转到对应的页面的，所配置的视图解析器将不起作用，返回的内容就是return里的内容。

### @Controller

@Controller用于标记在一个类上，使用它标记的类就是一个SpringMvc Controller对象，分发处理器会扫描使用该注解的类的方法，并检测该方法是否使用了@RequestMapping注解。
@Controller只是定义了一个控制器类，而使用@RequestMapping注解的方法才是处理请求的处理器。 
@Controller标记在一个类上还不能真正意义上说它就是SpringMvc的控制器，应为这个时候Spring还不认识它，这个时候需要把这个控制器交给Spring来管理。有两种方式可以管理：

```xml
<!--基于注解的装配-->
<!--方式一-->
<bean class="com.ray.web.controller.StudentController"/>
<!--方式二-->
<!--路径写到controller的上一层-->
<context:component-scan base-package="com.ray.web"/>
```

component-scan默认扫描的注解类型是@Component,不过，在@Component的语义基础之上细化为@Reposity,@Service,@Controller.

```java
package com.ray.web.controller;

import com.ray.web.model.Student;
import com.ray.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController
 *
 * @author fengjirong 2019/08/15 14:09
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	StudentService studentService;
    
	@GetMapping(value = "/")
	List<Student> list(){
		return studentService.list();
	}
}
```

### @RequestMapping

在类的级别上的注解会将一个特定请求或者请求模式映射到一个控制器之上。之后你还可以另外添加方法级别的注解来进一步指定到处理方法的映射关系。 

### @RequestBody

主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；

GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。

在后端的同一个接收方法里，@RequestBody与@RequestParam可以同时使用，@RequestBody最多只能有一个，而@RequestParam可以有多个。

后端@RequestBody注解对应的类在将HTTP的输入流(含请求体)装配到目标类(即：@RequestBody后面的类)时，会根据json字符串中的key来匹配对应实体类的属性，如果匹配一致且json中的该key对应的值符合(或可转换为) 实体类的对应属性的类型要求时,会调用实体类的setter方法将值赋给该属性。

1. json字符串中，如果value为""的话，后端对应属性如果是String类型的，那么接受到的就是""，如果是后端属性的类型是Integer、Double等类型，那么接收到的就是null。

2. json字符串中，如果value为null的话，后端对应收到的就是null。

3. 如果某个参数没有value的话，在传json字符串给后端时，要么干脆就不把该字段写到json字符串中；要么写value时， 必须有值，null  或""都行。

原文链接：https://blog.csdn.net/justry_deng/article/details/80972817

### @RequestParam

@RequestParam用于将请求参数区数据映射到功能处理方法的参数上。

RequestParam汉语意思为`请求参数`

假设url:localhost:8080/users/?userName=zhangsan

```java
public String queryUserName(@RequestParam(value="userName", required=true, defaultValue="zhang") String username) 
```

参数

1. value：参数名字，即入参的请求参数名字，如userName表示请求的参数区中的名字为userName的参数的值将传入；
2. required：是否必须，默认是true，表示请求中一定要有相应的参数，否则将报404错误码；
3. defaultValue：默认值，表示如果请求中没有同名参数时的默认值，默认值可以是SpEL表达式，如“#{systemProperties['java.vm.version']}”。

### @PathVariable

@pathvariable注解同样是从url中获取相应的参数数据映射到处理方法的参数上。

pathvariable汉语意思为`路径变量`

其与@RequestParam不同在于url拼接方式的不同

假设url:localhost:8080/users/zhangsan

```java
@RequestMapping(value = "/users/{userName}")
    public void doAuditTaobao(@PathVariable Integer id){
        taobaoService.auditTaobao(id);
    }
```



### @responseBody

将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML。

数据，需要注意的呢，在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。

```java
@RequestMapping("/login")
　　@ResponseBody
　　public User login(User user){
　　　　return user;
　　}
```

User字段：userName pwd 那么在前台接收到的数据为：'{"userName":"xxx","pwd":"xxx"}'

### @GetMapping

@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。

### @PostMapping

@PostMapping是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写。

### @PutMapping

@PutMapping和@PostMapping作用等同，都是用来向服务器提交信息。如果是添加信息，倾向于用@PostMapping，如果是更新信息，倾向于用@PutMapping。两者差别不是很明显。

## Service

### @Service

 通常作用在业务层，它用于将业务层 (Service 层 ) 的类标识为 Spring Bean。

此注解写于Service接口的实现类上。

通过扫描将标记有@Service的bean交给Spring进行管理

```xml
<context:component-scan base-package="com.ray.web"/>
```

## Dao

## @Repository

@Repository用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean。具体只需将该注解标注在 DAO类上即可。

通过扫描将标记有@Repository的bean交给Spring进行管理

```xml
<context:component-scan base-package="com.ray.web"/>
```

