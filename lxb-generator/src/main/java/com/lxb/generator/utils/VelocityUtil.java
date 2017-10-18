package com.lxb.generator.utils;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * VelocityUtil 工具类 根据模板生成文件
 *
 * @Author Liaoxb
 * @Date 2017/9/30 10:56:56
 */
public class VelocityUtil {

    /**
     * 根据模板生成文件
     *
     * @param vmFilePath 模板路径
     * @param outputFilePath 输出文件路径
     * @param context
     * @throws Exception
     */
    public static void generate(String vmFilePath, String outputFilePath, VelocityContext context) throws Exception {
        try {
            System.out.println("vmFilePath : " + vmFilePath);
            System.out.println("outputFilePath : " + outputFilePath);
            // 设置velocity资源加载器
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            // 设置编码，解决中文乱码
            ve.setProperty(Velocity.INPUT_ENCODING, "utf8");
            ve.setProperty(Velocity.OUTPUT_ENCODING, "utf8");
            ve.init();
            Template template = ve.getTemplate(vmFilePath);
            File outputFile = new File(outputFilePath);
            FileWriterWithEncoding writer = new FileWriterWithEncoding(outputFile, "utf-8");
            template.merge(context, writer);
            writer.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 根据文件绝对路径获取目录
     *
     * @param filePath
     * @return
     */
    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    /**
     * 根据文件绝对路径获取文件
     *
     * @param filePath
     * @return
     */
    public static String getFile(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }

    public static void main(String[] args) {
        // 设置velocity资源加载器
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Properties prop = new Properties();
//        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        prop.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "org.apache.velocity.runtime.resource.loader.URLResourceLoader");
        ve.init(prop);

        Template template = ve.getTemplate("template/ListTableTemplate.vm");
        VelocityContext ctx = new VelocityContext();
        ctx.put("classNameLowCase", "teacher");
        ctx.put("classNameUpCase", "Teacher");
        String[][] attrs = {
                {"Integer", "id"},
                {"String", "name"},
                {"String", "serializeNo"},
                {"String", "titile"},
                {
                        "String", "subject"
                }
        };
        ctx.put("attrs", attrs);

/*        String MODULE = "lxb-web";
        String rootPath = MybatisGeneratorUtil.getRootModulePath(VelocityUtil.class);
        String path = (rootPath + MODULE + "/src/main/resources/teacherList.jsp");
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
//        String path = "E:\\IntelliJ IDEA\\MyGithub\\lxb\\lxb-generator\\src\\main\\resources\\teacherList.jsp";
        String path = "E:\\IntelliJ IDEA\\MyGithub\\lxb\\lxb-web\\src\\main\\java\\com\\lxb\\web\\teacherList.jsp";
        System.out.println(path);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
            template.merge(ctx, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        System.out.println("success...");
    }

}
