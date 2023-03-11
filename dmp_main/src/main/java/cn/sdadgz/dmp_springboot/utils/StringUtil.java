package cn.sdadgz.dmp_springboot.utils;

/**
 * 字符串工具类
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/11 14:27:27
 */
public class StringUtil {

    public static String longitudeAndLatitudeFormat(String longitudeOrLatitude){
        StringBuilder stringBuilder = new StringBuilder(longitudeOrLatitude);
        int indexOfPoint = stringBuilder.indexOf(".");
        stringBuilder.insert(indexOfPoint - 2, ".").deleteCharAt(indexOfPoint + 1);
        return stringBuilder.toString();
    }
}
