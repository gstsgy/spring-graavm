package com.gstsgy.base.utils.generator;

import com.gstsgy.base.bean.db.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneratorService {
    public static void main(String[] args) {
        System.out.println();
    }

    public static void generateS(Class<? extends BaseTable>... classes) {
        String rootPath = System.getProperty("user.dir");
        for (Class<? extends BaseTable> clazz : classes) {
            String className = clazz.getSimpleName();
            String packageName = clazz.getPackage().getName();
            String moduleName = packageName.split("\\.")[2];
            String packagePath = "com.gstsgy." + moduleName ;

            String fullDir = rootPath + File.separator + moduleName + "/src/main/java/" + packagePath.replace(".", "/");
            try {
                saveToFilebyRepository(fullDir,packagePath,className,moduleName);
                saveToFilebyService(fullDir,packagePath,className,moduleName);
                saveToFilebyServiceImpl(fullDir,packagePath,className,moduleName);
                saveToFilebyController(fullDir,packagePath,className,getClassDescription(clazz),moduleName);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private static void saveToFilebyRepository(String fullDir, String pkg, String className,String moduleName) throws IOException {
        fullDir=fullDir+"/repository";
        pkg = pkg+".repository";
        File dir = new File(fullDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, String.format("%sRepository.java", className));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("package %s;\n\n",pkg));
        stringBuffer.append(String.format("import com.gstsgy.%s.bean.db.%s;\n",moduleName,className));
        stringBuffer.append("import com.gstsgy.base.repository.BaseRepository;\n\n");

        stringBuffer.append(String.format("public interface %sRepository extends BaseRepository<%s> {\n",className,className));
        stringBuffer.append("}");


        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(stringBuffer);
        }
    }
    private static void saveToFilebyService(String fullDir, String pkg, String className,String moduleName) throws IOException {
        fullDir=fullDir+"/service";
        pkg = pkg+".service";
        File dir = new File(fullDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, String.format("%sService.java", className));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("package %s;\n\n",pkg));
        stringBuffer.append(String.format("import com.gstsgy.%s.bean.db.%s;\n",moduleName,className));
        stringBuffer.append("import com.gstsgy.base.service.BaseService;\n\n");

        stringBuffer.append(String.format("public interface %sService extends BaseService<%s> {\n",className,className));
        stringBuffer.append("}");


        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(stringBuffer);
        }
    }
    private static void saveToFilebyServiceImpl(String fullDir, String pkg, String className,String moduleName) throws IOException {
        fullDir=fullDir+"/service/impl";
        pkg = pkg+".service.impl";
        File dir = new File(fullDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, String.format("%sServiceImpl.java", className));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("package %s;\n\n",pkg));
        stringBuffer.append(String.format("import com.gstsgy.%s.bean.db.%s;\n",moduleName,className));
        stringBuffer.append(String.format("import com.gstsgy.%s.repository.%sRepository;\n",moduleName,className));
        stringBuffer.append(String.format("import com.gstsgy.%s.service.%sService;\n",moduleName,className));
        stringBuffer.append("import org.springframework.stereotype.Service;\n");
        stringBuffer.append("import com.gstsgy.base.service.impl.BaseServiceImpl;\n\n");


        stringBuffer.append("@Service\n");
        stringBuffer.append(String.format("public class %sServiceImpl extends BaseServiceImpl<%s, %sRepository> implements %sService {\n",className,className,className,className));
        stringBuffer.append("}");


        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(stringBuffer);
        }
    }
    private static void saveToFilebyController(String fullDir, String pkg, String className,String tag,String moduleName) throws IOException {
        fullDir=fullDir+"/controller";
        pkg = pkg+".controller";
        File dir = new File(fullDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, String.format("%sController.java", className));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("package %s;\n\n",pkg));

        stringBuffer.append(String.format("import com.gstsgy.%s.bean.db.%s;\n",moduleName,className));
        stringBuffer.append(String.format("import com.gstsgy.%s.service.%sService;\n",moduleName,className));
        stringBuffer.append("import com.gstsgy.base.controller.BaseController;\n");
        stringBuffer.append("import io.swagger.v3.oas.annotations.tags.Tag;\n");
        stringBuffer.append("import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;\n");
        stringBuffer.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        stringBuffer.append("import org.springframework.web.bind.annotation.RestController;\n\n");

        stringBuffer.append(String.format("@Tag(name = \"%s\")\n",tag));
        stringBuffer.append(String.format("@RegisterReflectionForBinding({%s.class})\n",className));
        stringBuffer.append("@RestController\n");
        stringBuffer.append(String.format("@RequestMapping(\"%s\")\n",camelToKebab(className)));

        stringBuffer.append(String.format("public class %sController extends BaseController<%sService, %s> {\n",className,className,className));
        stringBuffer.append("}");


        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(stringBuffer);
        }
    }
    private static void saveToFile(File file, String pkg, String className,String comment, String fields) throws IOException {

        String content = "package " + pkg + ";\n\n" +
                "import com.gstsgy.base.bean.db.BaseTable;\n" +
                "import jakarta.persistence.Entity;\n" +
                "import io.swagger.v3.oas.annotations.media.Schema;\n" +
                "import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n\n" +
                "@EqualsAndHashCode(callSuper = true)\n" +
                "@Schema(description = \""+comment+"\")\n"+
                "@Data\n" +
                "@Entity\n" +
                "public class " + className + " extends BaseTable {\n" +
                fields +
                "}";
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.print(content);
        }
    }

    public static String camelToKebab(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        // 在每个大写字母前加-，然后转小写
        return camelCase.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    public static String getClassDescription(Class<?> clazz) {
        // 方法1：直接获取注解
        Schema schema = clazz.getAnnotation(Schema.class);
        if (schema != null) {
            return schema.description();
        }
        return null;
    }

}
