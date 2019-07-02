package top.wwf.common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description:    TODO
 * @Author:         wwf（hitwh_wwf@163.com）
 * @CreateDate:     2019/1/30 15:42
 */
public class PngUtils {

    private static Logger logger= LoggerFactory.getLogger(PngUtils.class);


    /**
     * 实际获取图片列表的方法
     * @param pngDirName
     * @return
     */
    private static List<String> getPngNameListByDir(String pngDirName){
        return MyFileUtils.getFileNameListByDir(pngDirName);
    }

}
