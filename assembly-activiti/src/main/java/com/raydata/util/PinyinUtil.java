package com.raydata.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
public class PinyinUtil {
    public static String getPinyin(String paramString) {
        return getPinyinZh_CN(convertStringByChinese(paramString));
    }

    public static String getPinyinToUpperCase(String paramString) {
        return getPinyinZh_CN(convertStringByChinese(paramString)).toUpperCase();
    }

    public static String getPinyinToLowerCase(String paramString) {
        return getPinyinZh_CN(convertStringByChinese(paramString)).toLowerCase();
    }

    public static String getPinyinFirstToUpperCase(String paramString) {
        return getPinyin(paramString);
    }

    private static HanyuPinyinOutputFormat getDefaultFormat() {
        HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        localHanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        return localHanyuPinyinOutputFormat;
    }

    private static Set<String> convertStringByChinese(String paramString) {
        char[] arrayOfChar1 = paramString.toCharArray();
        if ((paramString != null) && (!paramString.trim().equalsIgnoreCase(""))) {
            char[] arrayOfChar2 = paramString.toCharArray();
            String[][] arrayOfString = new String[paramString.length()][];
            for (int i = 0; i < arrayOfChar2.length; i++) {
                char c = arrayOfChar2[i];
                if ((String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) || (String.valueOf(c).matches("[\\u3007]"))) {
                    try {
                        arrayOfString[i] = PinyinHelper.toHanyuPinyinStringArray(arrayOfChar1[i], getDefaultFormat());
                    } catch (BadHanyuPinyinOutputFormatCombination localBadHanyuPinyinOutputFormatCombination) {
                        localBadHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                } else {
                    arrayOfString[i] = new String[] { String.valueOf(arrayOfChar2[i]) };
                }
            }
            String[] arrayOfString1 = exchange(arrayOfString);
            HashSet localHashSet = new HashSet();
            for (int j = 0; j < arrayOfString1.length; j++) {
                localHashSet.add(arrayOfString1[j]);
            }
            return localHashSet;
        }
        return null;
    }

    private static String[] exchange(String[][] paramArrayOfString) {
        String[][] arrayOfString = doExchange(paramArrayOfString);
        return arrayOfString[0];
    }

    private static String[][] doExchange(String[][] paramArrayOfString) {
        int i = paramArrayOfString.length;
        if (i >= 2) {
            int j = paramArrayOfString[0].length;
            int k = paramArrayOfString[1].length;
            int m = j * k;
            String[] arrayOfString = new String[m];
            int n = 0;
            for (int i1 = 0; i1 < j; i1++) {
                for (int i2 = 0; i2 < k; i2++) {
                    arrayOfString[n] = (capitalize(paramArrayOfString[0][i1]) + capitalize(paramArrayOfString[1][i2]));
                    n++;
                }
            }
            String[][] arrayOfString1 = new String[i - 1][];
            for (int i2 = 2; i2 < i; i2++) {
                arrayOfString1[(i2 - 1)] = paramArrayOfString[i2];
            }
            arrayOfString1[0] = arrayOfString;
            return doExchange(arrayOfString1);
        }
        return paramArrayOfString;
    }

    private static String capitalize(String paramString) {
        char[] arrayOfChar = paramString.toCharArray();
        if ((arrayOfChar != null) && (arrayOfChar.length > 0) && (arrayOfChar[0] >= 'a') && (arrayOfChar[0] <= 'z')) {
            arrayOfChar[0] = ((char) (arrayOfChar[0] - ' '));
        }
        return new String(arrayOfChar);
    }

    private static String getPinyinZh_CN(Set<String> paramSet) {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        Iterator localIterator = paramSet.iterator();
        while (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            if (i == paramSet.size() - 1) {
                localStringBuilder.append(str);
            } else {
                localStringBuilder.append(str + ",");
            }
            i++;
        }
        return localStringBuilder.toString();
    }

    public static String getPinYinHeadChar(String paramString) {
        StringBuffer localStringBuffer = new StringBuffer();
        if ((paramString != null) && (!paramString.trim().equalsIgnoreCase(""))) {
            for (int i = 0; i < paramString.length(); i++) {
                char c = paramString.charAt(i);
                String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(c);
                if (arrayOfString != null) {
                    localStringBuffer.append(arrayOfString[0].charAt(0));
                } else {
                    localStringBuffer.append(c);
                }
            }
        }
        return localStringBuffer.toString();
    }

    public static String strFilter(String paramString) throws PatternSyntaxException {
        String str = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        Pattern localPattern = Pattern.compile(str);
        Matcher localMatcher = localPattern.matcher(paramString);
        return localMatcher.replaceAll("").trim();
    }

    public static String getPinYinHeadCharFilter(String paramString) {
        return strFilter(getPinYinHeadChar(paramString));
    }

    public static void main(String[] paramArrayOfString) {
        String str = "〇的输￥￥#s，ldsa";
        log.info("小写输出：" + getPinyinToLowerCase(str));
        log.info("大写输出：" + getPinyinToUpperCase(str));
        log.info("首字母大写输出：" + getPinyinFirstToUpperCase(str));
        log.info("返回中文的首字母输出：" + getPinYinHeadChar(str));
        log.info("返回中文的首字母并过滤特殊字符输出：" + getPinYinHeadCharFilter(str));
    }
}
