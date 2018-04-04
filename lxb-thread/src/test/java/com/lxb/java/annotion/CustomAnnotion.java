package com.lxb.java.annotion;

import java.lang.annotation.*;

/**
 * 自定义annotion
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
public @interface CustomAnnotion {
    //如果注解中仅包含一个元素，这个元素的名字应该为value
    String value() default "";

    /**
     * 元注解
     *  注解 @Retention 可以用来修饰注解，是注解的注解，称为元注解。
     */
    /**
     *
     * 提取 Annotation信息

     •JDK5.0 在 java.lang.reflect包下新增了 AnnotatedElement接口,该接口代表程序中可以接受注释的程序元素
     •当一个 Annotation类型被定义为运行时Annotation后,该注释才是运行时可见,当 class文件被载入时保存在 class文件中的 Annotation才会被虚拟机读取
     •程序可以调用AnnotationElement对象的如下方法来访问 Annotation信息
     –获取 Annotation实例：
     •getAnnotation(Class<T> annotationClass)
     •getDeclaredAnnotations()
     •getParameterAnnotations()
     JDK 的元Annotation

     •JDK 的元Annotation 用于修饰其他Annotation 定义
     •@Retention:只能用于修饰一个 Annotation定义,用于指定该 Annotation可以保留多长时间,@Rentention包含一个RetentionPolicy类型的成员变量,使用 @Rentention时必须为该 value成员变量指定值:
     –RetentionPolicy.CLASS:编译器将把注释记录在 class文件中.当运行 Java程序时,JVM 不会保留注释.这是默认值
     –RetentionPolicy.RUNTIME:编译器将把注释记录在class文件中. 当运行 Java 程序时, JVM 会保留注释. 程序可以通过反射获取该注释
     –RetentionPolicy.SOURCE:编译器直接丢弃这种策略的注释
     •@Target: 用于修饰Annotation 定义,用于指定被修饰的 Annotation能用于修饰哪些程序元素.@Target 也包含一个名为 value的成员变量.
     •@Documented:用于指定被该元 Annotation修饰的 Annotation类将被 javadoc工具提取成文档.
     •@Inherited:被它修饰的 Annotation将具有继承性.如果某个类使用了被@Inherited 修饰的Annotation, 则其子类将自动具有该注释
     */

    /**
     *     •从 JDK5.0 开始,Java 增加了对元数据(MetaData)的支持,也就是Annotation(注释)
     •Annotation其实就是代码里的特殊标记,这些标记可以在编译,类加载, 运行时被读取,并执行相应的处理.通过使用Annotation,程序员可以在不改变原有逻辑的情况下,在源文件中嵌入一些补充信息.
     •Annotation 可以像修饰符一样被使用,可用于修饰包,类,构造器, 方法,成员变量, 参数,局部变量的声明,这些信息被保存在Annotation的 “name=value”对中.
     •Annotation能被用来为程序元素(类,方法,成员变量等)设置元数据
     基本的 Annotation

     •使用 Annotation时要在其前面增加@符号,并把该Annotation 当成一个修饰符使用.用于修饰它支持的程序元素
     •三个基本的Annotation:
     –@Override:限定重写父类方法,该注释只能用于方法
     –@Deprecated:用于表示某个程序元素(类,方法等)已过时
     –@SuppressWarnings:抑制编译器警告.
     自定义 Annotation

     •定义新的 Annotation类型使用@interface关键字
     •Annotation 的成员变量在Annotation 定义中以无参数方法的形式来声明.其方法名和返回值定义了该成员的名字和类型.
     •可以在定义Annotation的成员变量时为其指定初始值,指定成员变量的初始值可使用default关键字
     •没有成员定义的Annotation称为标记;包含成员变量的Annotation称为元数据Annotation
     */




}
