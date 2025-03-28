/**
 * Created by Tomato on 2024/7/23.
 * Description：
 */
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by tomato 2021/12
 * JAVA生成混淆字典
 */
public class ProguardGenerateClass {

    //字典样本
    private static List<String> SOURCE = Arrays.asList("a", "o", "e");
    //字典行数
    private static int LENGTH = 30000;
    //输出路径
    private static final String ROOT_PATH = System.getProperty("user.dir") + "/app/";
    //输出名称
    private static final String FILE_NAME = "output_dict.txt";

    private static Random random = new Random();


    public static void main(String[] args) {
        List<String> unicodeList = new ArrayList(SOURCE);
        List<String> outputList = new ArrayList<String>();
        Collections.sort(unicodeList);
        File file = new File(ROOT_PATH, FILE_NAME);
        if (file.exists()) {
            System.out.println("文件已存在，删除");
            file.delete();
        } else {
            System.out.println("文件不存在");
        }

        String encoding = "UTF-8";
        int repeatCount = 0;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int i = 0;
            while (i < LENGTH) {
                String tmp = "";
                int width = random.nextInt(7) + 4;
                for (int j = 0; j < width; j++) {
                    tmp = tmp + getRandomString(unicodeList);
                }
                if (!outputList.contains(tmp)) {
                    i++;
                    outputList.add(tmp);
                    fileOutputStream.write(tmp.getBytes(encoding));
                    if (i < LENGTH) {
                        //最后一行不输入回车
                        fileOutputStream.write('\n');
                    }
                    repeatCount = 0;
                } else {
                    repeatCount++;
                    System.out.println("重复生成的字符串当前行数--->" + i + " 内容---> " + tmp);
                    if (repeatCount == 10000) {
                        System.out.println("连续重复次数超过10000次 已达到最大行数 无法继续生成");
                        break;
                    }
                }
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(List<String> list) {
        String tm;
        int s = random.nextInt(list.size());
        tm = list.get(s);
        return tm;
    }
}
