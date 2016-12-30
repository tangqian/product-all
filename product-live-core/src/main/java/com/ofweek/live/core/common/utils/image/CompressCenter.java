package com.ofweek.live.core.common.utils.image;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.sys.service.SysFileService;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 已压缩过图片集合缓存类
 *
 * @author tangq
 */
public class CompressCenter {

    private static SysFileService sysFileService = SpringContextHolder.getBean(SysFileService.class);

    public enum CompressTypeEnum {
        MIDDLE_IMAGE("_m", "中图"), SMALL_IMAGE("_s", "小图");

        private static final Set<String> subfixs = Sets.newHashSet();

        static {
            for (CompressTypeEnum typeEnum : CompressTypeEnum.values()) {
                subfixs.add(typeEnum.getSubfix());
            }
        }

        CompressTypeEnum(String subfix, String meaning) {
            this.subfix = subfix;
            this.meaning = meaning;
        }

        public String getSubfix() {
            return subfix;
        }

        public Map<String, String> getCacheMap() {
            return compressedMap;
        }

        public static Set<String> getSubfixs() {
            return Collections.unmodifiableSet(subfixs);
        }

        private final String subfix;
        @SuppressWarnings("unused")
        private final String meaning;

        private final Map<String, String> compressedMap = Maps.newHashMap();
    }

    public static void put(String key, String uri, CompressTypeEnum typeEnum) {
        typeEnum.getCacheMap().put(key, uri);
    }

    public static String get(String key, CompressTypeEnum typeEnum) {
        return typeEnum.getCacheMap().get(key);
    }

    public static boolean containsKey(String key, CompressTypeEnum typeEnum) {
        return typeEnum.getCacheMap().containsKey(key);
    }

    public static boolean containsValue(String uri, CompressTypeEnum typeEnum) {
        return typeEnum.getCacheMap().containsValue(uri);
    }


    /**
     * 对文件进行压缩处理
     * @param fileId 文件id
     * @param fileUri 文件物理地址相对uri
     * @param width
     * @param height
     * @param typeEnum 压缩类型
     * @return
     */
    public static boolean doCompressImage(String fileId, String fileUri, int width, int height, CompressTypeEnum typeEnum) {
        boolean result = false;
        if (containsKey(fileId, typeEnum)) {
            result = true;
            return result;
        }
        File srcFile, targetFile;
        fileUri = getPureUri(fileUri);
        try {
            srcFile = new File(sysFileService.getAbsolutePath(fileUri));
            if (srcFile.isFile()) {
                String targetUri = getCompressUri(fileUri, typeEnum);
                targetFile = new File(sysFileService.getAbsolutePath(targetUri));
                if (!targetFile.exists()) {
                    result = PictureCompressionUtils.equalRatioSize(srcFile, targetFile, width, height);
                    if(result){
                        put(fileId, targetUri, typeEnum);
                    }
                } else {
                    put(fileId, targetUri, typeEnum);
                    result = true;
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 获取原始图片uri
     *
     * @param imageUri
     * @return
     */
    public static String getPureUri(String imageUri) {
        if (StringUtils.isNotBlank(imageUri)) {
            for (String subfix : CompressTypeEnum.getSubfixs()) {
                imageUri = imageUri.replace(subfix, "");
            }
        }
        return imageUri;
    }

    /**
     * 是否是缩略图
     *
     * @param imageUri
     * @return
     */
    public static boolean isThumbnailImage(String imageUri) {
        boolean result = false;
        for (String subfix : CompressTypeEnum.getSubfixs()) {
            if (imageUri.contains(subfix)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 获取压缩后的图片uri
     *
     * @param srcUri
     * @return
     */
    public static String getCompressUri(String srcUri, CompressTypeEnum typeEnum) {
        return combineUri(srcUri, typeEnum.getSubfix());
    }

    /**
     * 组合uri
     *
     * @param srcUri
     * @param subfix
     * @return
     */
    private static String combineUri(String srcUri, String subfix) {
        int pos = srcUri.lastIndexOf('.');
        return srcUri.substring(0, pos) + subfix + srcUri.substring(pos);
    }

    public static void main(String[] args) {
        System.out.println(getCompressUri("ss/dwwww.jpg", CompressTypeEnum.MIDDLE_IMAGE));
        File file = new File("F:/sss/sff.tee");
        System.out.println(file.getAbsolutePath());
        System.out.println(getPureUri("ss/dwwww_s_b_m_l.jpg"));
        System.out.println(getPureUri("ss/dwwww_l.jpg"));
    }
}
