1、pom 中的配置信息的继承，与包的依赖

        pom中依赖对其他module的pom的依赖<dependency>，只是包的依赖。除此之外的其他配置，如properties、build 等只能通过
        继承得到<parent>。因此 maven编译jdk版本，字符编码，项目版本等都是配置在root结构的pom中的。

2、各个module之间的引用

        通过标准方式创建（非基于archetype）的maven module需要在pom中手动配上<artifactId>，
    并且各个module的<artifactId>都必须相同，否则各个module之间不能互相引用