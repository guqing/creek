package xyz.guqing.creek.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import xyz.guqing.creek.model.constant.CreekConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 根据ip地址获取地理位置
 *
 * @author guqing
 * @date 2020-06-01
 */
@Slf4j
public class RegionAddressUtils {
    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, getIp2RegionPath());
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.warn("获取地址信息异常,[{}],异常堆栈: [{}]", e.getMessage(), e);
            return StringUtils.EMPTY;
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getIp2RegionPath() throws IOException {
        String dbPath = RegionAddressUtils.class.getResource("/ip2region/ip2region.db").getPath();
        File file = new File(dbPath);
        if (!file.exists()) {
            String tmpDir = System.getProperties().getProperty(CreekConstant.JAVA_TEMP_DIR);
            dbPath = tmpDir + "/ip2region.db";
            file = new File(dbPath);
            InputStream resourceAsStream = RegionAddressUtils.class.getClassLoader()
                    .getResourceAsStream("classpath:ip2region/ip2region.db");
            if (resourceAsStream != null) {
                FileUtils.copyInputStreamToFile(resourceAsStream, file);
            }
        }
        return file.getPath();
    }
}
