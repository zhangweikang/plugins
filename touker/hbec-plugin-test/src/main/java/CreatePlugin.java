import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动生成Repository,RepositoryImpl,Service,ServiceImpl,Facade,FacadeImpl
 * Created by zhangweikang on 2017/7/24.
 */
public class CreatePlugin {

    public static String BASE_PACKAGE = "hbec.app.acnt";//项目基础包名称，根据自己公司的项目修改

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".bean";//bean所在包
    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";//Repository所在包
    public static final String REPOSITORY_IMPL_PACKAGE = REPOSITORY_PACKAGE + ".impl";//Repository所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String FACADE_PACKAGE = BASE_PACKAGE + ".facade";//Facade所在包
    public static final String FACADE_IMPL_PACKAGE = FACADE_PACKAGE + ".impl";//FacadeImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//Controller所在包

    private static final String REPOSITORY_FTL_NAME = "repository";
    private static final String SERVICE_FTL_NAME = "service";
    private static final String FACADE_FTL_NAME = "facade";
    private static final String CONTROLLER_FTL_NAME = "controller";

    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

    private static final String TEMPLATE_FILE_PATH = "/generator/template";//模板位置
    private static final String JAVA_PATH = "/src/main/java"; //java文件路径

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名

    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_REPOSITORY = packageConvertPath(REPOSITORY_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_REPOSITORY_IMPL = packageConvertPath(REPOSITORY_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_FACADE = packageConvertPath(FACADE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_FACADE_IMPL = packageConvertPath(FACADE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

    private static final String AUTHOR = "CodeGenerator";//@author
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date
    private static final String ENCODING = "UTF-8";
    private static Boolean FILTER_HEAD = false;//是否过滤表名头
    private static String FILTER_HEAD_STRING = "";//过滤表名头

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please insert args ...... error");
            return;
        }
        String tableName = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                tableName = args[0];
                if (tableName == null) {
                    System.out.println("Please insert tableName ,tableName = " + tableName + "...... error");
                    return;
                }
            }
            if (i == 1) {
                String filterHead = args[1];
                if (filterHead != null) {
                    System.out.println("过滤表名头,filterHead = " + filterHead);
                    FILTER_HEAD = true;
                    FILTER_HEAD_STRING = filterHead;
                }
            }
            if (i == 2) {
                String basePackage = args[2];
                if (basePackage != null) {
                    BASE_PACKAGE = basePackage;
                }
            }
        }

        String cmd = "mvn mybatis-generator:generate";
        ProcessBuilder builder = new ProcessBuilder("cmd", "/c", cmd);
        try {
            builder.start();
        } catch (IOException e) {
            System.out.println("mapperXmlAndBean error ......");
            e.printStackTrace();
            return;
        }
        writerJava(tableName, REPOSITORY_FTL_NAME);
        writerJava(tableName, SERVICE_FTL_NAME);
        writerJava(tableName, FACADE_FTL_NAME);
    }

    public static void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("baseRequestMapping", tableNameConvertMappingPath(tableName));
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(CreatePlugin.class, TEMPLATE_FILE_PATH);
        cfg.setDefaultEncoding(ENCODING);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        //如果需要截掉表头
        tableName = FILTER_HEAD ? tableName.substring(FILTER_HEAD_STRING.length() + 1, tableName.length()) : tableName;
        StringBuilder result = new StringBuilder();
        if (tableName != null && tableName.length() > 0) {
            tableName = tableName.toLowerCase();//兼容使用大写的表名
            boolean flag = false;
            for (int i = 0; i < tableName.length(); i++) {
                char ch = tableName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        String camel = tableNameConvertLowerCamel(tableName);
        return convertUpperCamel(camel);
    }

    private static String convertUpperCamel(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    private static void writerJava(String tableName, String baseFtl) {
        //获取当前层的名称
        String baseJava = convertUpperCamel(baseFtl);
        String baseImplFtl = baseFtl + "-impl";
        String[] split = baseImplFtl.split("-");
        String baseImplJava = convertUpperCamel(split[0]) + convertUpperCamel(split[1]);

        //获取当前层的包路径
        String packageName = BASE_PACKAGE + "." + baseFtl;//Repository所在包
        String implPackageName = packageName + ".impl";//Repository所在包
        String packagePath = packageConvertPath(packageName);
        String implPackagePath = packageConvertPath(implPackageName);
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);

            String path = PROJECT_PATH + JAVA_PATH + packagePath + "I" + modelNameUpperCamel + baseJava + ".java";
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Writer outPath = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), ENCODING));
            cfg.getTemplate(baseFtl + ".ftl", ENCODING).process(data, outPath);
            System.out.println(modelNameUpperCamel + baseJava + ".java success");

            String implPath = PROJECT_PATH + JAVA_PATH + implPackagePath + modelNameUpperCamel + baseImplJava + ".java";
            File file1 = new File(implPath);
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            //修改输出文件编码
            Writer outImplPath = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(implPath), ENCODING));
            //cfg.getTemplate("facade-impl.ftl",ENCODING).process(data,new FileWriter(file1));
            cfg.getTemplate(baseImplFtl + ".ftl", ENCODING).process(data, outImplPath);
            System.out.println(modelNameUpperCamel + baseImplJava + ".java success");
        } catch (Exception e) {
            throw new RuntimeException("生成" + baseJava + ",error", e);
        }
    }
}
