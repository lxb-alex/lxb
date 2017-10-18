template中的vm模板文件中的 baseXXX 可以不引用

如果要单独提出来：
    ①、将template中继承的 baseXxx 类去掉。如：baseController 
   
设置velocity资源加载器 的三种方式：<br/>

     // 设置velocity资源加载器<br/>
     VelocityEngine ve = new VelocityEngine();<br/>
     ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");<br/>
     ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());<br/>
     ve.init();<br/>
 
     Template template = ve.getTemplate("template/ListTableTemplate.vm");<br/>
       Properties prop = new Properties();<br/>
       prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");<br/>
       Velocity.init(prop);<br/>
       
     VelocityEngine ve = new VelocityEngine();<br/>
     Properties prop = new Properties();<br/>
//     prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");<br/>
     prop.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "org.apache.velocity.runtime.resource.loader.URLResourceLoader");<br/>
     ve.init(prop);<br/>
