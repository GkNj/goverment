package com.example.egoverment.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImgUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDate = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public  String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailInputStream).size(29, 29)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxxjpg     *
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取随机五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDate.format(new Date());
        return nowTimeStr + rannum;
    }


}
