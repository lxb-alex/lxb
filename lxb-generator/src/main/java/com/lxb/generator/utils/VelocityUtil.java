package com.lxb.generator.utils;

import com.lxb.generator.controller.MybatisGeneratorUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
     * @param inputVmFilePath 模板路径
     * @param outputFilePath 输出文件路径
     * @param context
     * @throws Exception
     */
    public static void generate(String inputVmFilePath, String outputFilePath, VelocityContext context) throws Exception {
        try {
            System.out.println("1231312123-----" + inputVmFilePath);
            System.out.println("*************" + outputFilePath);

            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template template = ve.getTemplate("template/" + getFile(inputVmFilePath));

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
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

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
        String path = "E:\\IntelliJ IDEA\\MyGithub\\lxb\\lxb-web\\src\\main\\java\\com\\lxb\\rpc\\api\\teacherList.jsp";
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
