package top.wwf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Description:    字符工具类
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/19 10:08
*/
public class CharsetUtils {
    private static Logger logger= LoggerFactory.getLogger(CharsetUtils.class);

    /**
     * 判断某个字符串中的中文字符有几个
     * @param str
     * @return
     */
    public static int countChineseNum(String str){
        if (StringUtils.isBlank(str)){
            logger.info("输入字符串：{} 为空",str);
            return 0;
        }
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i)))count++;
        }
        return count;
    }

    /**
     * 统计ascll字符中位于33～126号字符的个数
     * @param str
     * @return
     */
    public static int countAscllNum(String str){
        if (StringUtils.isBlank(str)){
            logger.info("输入字符串：{} 为空",str);
            return 0;
        }
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            if (33<=str.charAt(i)&&126>=str.charAt(i))count++;
        }
        return count;
    }



    /**
     * 判断某个字符是不是中文
     * @param c 待判断的字符
     * @return
     */
    private static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;
        }
        return false;
    }

}
