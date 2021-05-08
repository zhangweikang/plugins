package com.zhangwk;

/**
 * Uglifyjs压缩JS文件,默认忽略 require,exports,module 三个关键字
 * Created by Administrator on 2016/12/15.
 */
public class UglifyjsYUI {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please select the JS file to be compressed ......");
            return;
        }
        StringBuffer string = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                System.out.println("Compressed file = [" + args[i] + "]");
            } else {
                string.append("," + args[i]);
                System.out.println("Ignore characters = [" + args[i] + "]");
            }
        }
        try {
            String substring = args[0].substring(0, args[0].indexOf("."));
            StringBuffer sb = new StringBuffer();
            sb.append("uglifyjs ");
            sb.append(substring);
            sb.append(".js -o ");
            sb.append(substring);
            sb.append(".min.js -m -r '$,require,exports,module");
            sb.append(string);
            sb.append(",$'");
            System.out.println("Execute the DOS command : " + sb.toString());
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", sb.toString());
            builder.start();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
