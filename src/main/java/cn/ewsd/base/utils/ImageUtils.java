package cn.ewsd.base.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

public class ImageUtils {

    /**
     * base64字符串转图片
     * @param base64Str
     * @param filePath
     * @return
     */
    public static String base64ToImg(String base64Str, String filePath){
        // 解密
        try {
            // 图片分类路径+图片名+图片后缀
            String imgClassPath = filePath.concat(UUID.randomUUID().toString()).concat(".png");
            // 解密
            Base64.Decoder decoder = Base64.getDecoder();
            // 去掉base64前缀 data:image/jpeg;base64,
            base64Str = base64Str.substring(base64Str.indexOf(",", 1) + 1, base64Str.length());
            byte[] b = decoder.decode(base64Str);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 保存图片
            OutputStream out = new FileOutputStream(imgClassPath);
            out.write(b);
            out.flush();
            out.close();
            // 返回图片的相对路径 = 图片分类路径+图片名+图片后缀
            return imgClassPath;
        } catch (IOException e) {
            return null;
        }
    }
}
