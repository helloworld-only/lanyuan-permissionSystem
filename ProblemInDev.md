# 一. 前端

## 1. js的import

> ```js
> import * as myModule from 'my-modules.js'
> // 导入所有接口
> 
> import {myExport} from 'my-modules.js'
> // 只导入某一个接口
> 
> import {myExport} from '@/my-modules'
> // @ 等价于 src 这个目录
> // vue还会自动补全扩展名（可补全的扩展名：.js、.vue、.json）
> ```



## 2.前端获取图片验证码问题

> 1. 通过img标签的src属性（src的值是生成图片验证码的后端接口，如果需要换一张的话，只需要在事件中更改一下src的值（访问后端的接口不能变，可以加一个随机数）（每次src的改变vue都会帮我们刷新页面，也就导致会重新请求验证码图片）
> 2. 使用ajax
>
> 
>
> 参考：https://blog.csdn.net/weixin_45092437/article/details/126842549



## 3. @click.native.prevent

> 



## 4. 登录成功后跳转至主页面，却显示不了任何数据

> 通过调试发现，跳转到的目标组件根本就没有挂载。

* 解决方法

  > ```js
  > this.$router.push({
  >  path:'/home'
  >  // name: 'home'
  > })
  > ```
  >
  > 把 name: 'home' 这一属性去掉即可



## 5. Vue项目中使用多个router-view

>  1.  为每个router-view设置那么属性
>
>      >  ```vue
>      >  <!-- 组件1中的router-view -->
>      >  <router-view name="appRouterView"></router-view>
>      >  ```
>      >
>      >  ```vue
>      >  <!-- 组件2中的router-view -->
>      >  <router-view name="homeRouterView"></router-view>
>      >  ```
>
>  2.  将`src/router/index.js`中的路由映射中的component改为components
>
>      >  ```js
>      >  const router = new VueRouter({
>      >      mode:'history',
>      >      routes : [
>      >          {
>      >              path:'/',
>      >              redirect:'/login'
>      >          },
>      >          {
>      >              path:'/login',
>      >              components: {
>      >                  appRouterView: LoginPage
>      >              }
>      >          },
>      >          {
>      >              path:'/home',
>      >              components:{
>      >                  appRouterView: Home,
>      >              },    
>      >              children:[
>      >                  {
>      >                      path: 'user/display',
>      >                      components: {
>      >                          homeRouterView: UserDisplay,
>      >                      }
>      >                  }
>      >              ]
>      >          }
>      >      ]
>      >  })
>      >  
>      >  export default router;
>      >  ```
>      >





## 6. js处理对象数组



## 7.当前地址在子路由，点击导航栏返回父路由（也就是当前路由的上一级），无法正常显示父路由的数据





# 二.后端

## 1. 在spring配置文件中引入jdbc.properties文件

>  ```xml
>  <context:property-placeholder 
>                                location="classpath:jdbc.properties">
>  </context:property-placeholder>
>  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>      <property name="driverClassName" value="${driver}"></property>
>      <property name="url" value="${url}"></property>
>      <property name="username" value="${username}"></property>
>      <property name="password" value="${password}"></property>
>  </bean>
>  ```
>
>  但获取dataSource后，查看username（dataSource.getUsername()），得到的不是root，而是本台机器上的用户名Mayn。



*  临时解决方案

>  <property name="username" value="root"></property>



## 2. maven模块化开发问题

>  使用maven进行模块化开发，分为entity模块、component模块（包含dao层和service层）、controller模块
>
>  测试component模块能够顺利通过（component模块依赖entity模块）
>
>  但是测试controller模块时（也就是运行web项目），报错说找不到component这一依赖（坐标均正确）

*  临时解决方案

   >  使用父工程的生命周期 install，把各模块打包上传至本地仓库



## 3.javax:servlet-api问题

> org.apache.catalina.LifecycleException: A child container failed during start
>
> pom文件中如果有servlet-api这一依赖，一定要将其scope设置为provided，如果设置了之后还是会报错，要查看一下其他依赖是否也会引入servlet-api，如果有记得将其排除
>
> ```xml
> <!--        图片验证码相关jar包-->
> <dependency>
>     <groupId>com.github.penggle</groupId>
>     <artifactId>kaptcha</artifactId>
>     <version>2.3.2</version>
>     <exclusions>
>         <exclusion>
>             <groupId>javax.servlet</groupId>
>             <artifactId>javax.servlet-api</artifactId>
>         </exclusion>
>     </exclusions>
> </dependency>
> ```
>



## 4.ajax与session问题

> 使用ajax访问后端生成验证码的接口，后端接口生成验证码并将正确结果保存在session域中。然后用户再进行登录操作，提交表单后，后端接口从session域中获取之前保存的正确结果，但是从session域中找不到之前保存的数据。



## 5.SpringMVC参数解析器无法解析到ajax的post请求的表单的参数

> 例如，控制器方法中的参数：(User user, String code)，正常情况下都是会帮我们封装好的。
>
> 甚至通过request对象都获取不到。

* 临时解决方案

  > 把ajax请求的Content-Type的值设置为 application/json。
  >
  > 再使用@RequestBody获取请求体。(@RequestBody String data)，再通过json工具类将该json字符串转为map。



## 6.在controller上加上访问前缀

> 比如说@Controller("/home/user")，然后@RequestMapping("/display")
>
> 访问地址为：http://localhost/home/user/display，结果404
>
> 如果直接使用 @RequestMapping("/home/user/display")，则能正常访问到

*  正确做法

   >  在类上添加@RequestMapping("/home/user")，而不是设置Controller注解的属性值



## 7. mybatis的insert问题

> mybatis的mapper接口和mapper文件均配置正确，且日志已打印：`DEBUG com.authSys.mapper.UserMapper.insertUser - ==>  Preparing: insert into t_user(acct, passwd, user_name) values(?,?,?);`，
>
> 却未打印：`DEBUG com.authSys.mapper.UserMapper.insertUser - ==> Parameters: 11111111(String), 11111(String), 111(String)`
> `DEBUG com.authSys.mapper.UserMapper.insertUser - <==    Updates: 1`

* 临时解决方案

  > ```xml
  > <!-- 原映射文件写法（无法正常执行）-->
  > <insert id="insertUser" parameterType="com.authSys.entity.UserEntity">
  >     insert into t_user(acct, passwd, user_name)
  >     values(#{acct},#{passwd},#{userName});
  > </insert>
  > ```

  > ```xml
  > <!-- 改正后写法（可正常执行） -->
  > <insert id="insertUser" parameterType="com.authSys.entity.UserEntity" 
  >         useGeneratedKeys="true"
  >         keyColumn="user_id"
  >         keyProperty="userId">
  >     insert into t_user(acct, passwd, user_name)
  >     values(#{acct,jdbcType=CHAR},#{passwd, jdbcType=CHAR},#{userName,jdbcType=CHAR});
  > </insert>
  > ```



## 8. mybatis的update问题

> 与insert问题不同的是，update时，打印了创建sqlSession相关日志，接着就直接打印关闭sqlSession相关日志。
