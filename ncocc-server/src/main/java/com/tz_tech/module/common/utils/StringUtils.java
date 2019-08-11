package com.tz_tech.module.common.utils;

import java.util.LinkedList;

/**
 * String 的工具类，在JDK1.4中有更好的方法可以替代，请参考JDK1.4 JavaDoc。
 *
 * @author guojun
 * @version 1.0
 */
public class StringUtils {
    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
    private static final char AMP_ENCODE[] = "&amp;".toCharArray();
    private static final char LT_ENCODE[] = "&lt;".toCharArray();
    private static final char GT_ENCODE[] = "&gt;".toCharArray();

    private StringUtils() {
    }

    /**
     * 用新的字符串替换给定字符串中的老的字符串
     *
     * @param string
     * @param oldString
     * @param newString
     * @return
     */
    public static final String replace(String string,
                                       String oldString,
                                       String newString) {
        if (string == null) {
            return null;
        }
        if (newString == null) {
            return string;
        }
        int i = 0;
        if ((i = string.indexOf(oldString, i)) >= 0) {
            char string2[] = string.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(string2.length);
            buf.append(string2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = string.indexOf(oldString, i)) > 0; j = i) {
                buf.append(string2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(string2, j, string2.length - j);
            return buf.toString();
        } else {
            return string;
        }
    }

    /**
     * 忽略大小写的替换
     *
     * @param line
     * @param oldString
     * @param newString
     * @return
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else {
            return line;
        }
    }

    /**
     * 忽略大小写的替换，并且返回替换个个数
     *
     * @param line
     * @param oldString
     * @param newString
     * @param count     替换个数数组 通过new int[0]来调用
     *                  （为什么是数组，因为Java中基本数据类型是按值传递的而对象是按引用传递的）
     * @return
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString, int count[]) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }

    /**
     * 替换字符串，并且返回替换的个数
     *
     * @param line
     * @param oldString
     * @param newString
     * @param count
     * @return
     */
    public static final String replace(String line, String oldString,
                                       String newString, int count[]) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 1;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else {
            return line;
        }
    }

    /**
     * 剥去字符串中的HTML标签
     *
     * @param in
     * @return
     */
    public static final String stripTags(String in) {
        if (in == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<') {
                    if (i + 3 < len && input[i + 1] == 'b' &&
                            input[i + 2] == 'r' && input[i + 3] == '>') {
                        i += 3;
                    } else {
                        if (i > last) {
                            if (last > 0) {
                                out.append(" ");
                            }
                            out.append(input, last, i - last);
                        }
                        last = i + 1;
                    }
                } else if (ch == '>') {
                    last = i + 1;
                }
            }
        }

        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * 字符串中的HTML标签替换
     *
     * @param in
     * @return
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        }
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
        for (; i < len; i++) {
            char ch = input[i];
            if (ch <= '>') {
                if (ch == '<') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else if (ch == '>') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(GT_ENCODE);
                } else if (ch == '"') {
                    if (i > last) {
                        out.append(input, last, i - last);
                    }
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                }
            }
        }

        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    public static final String[] split(String line, String separator) {
        LinkedList list = new LinkedList();
        if (line != null) {
            int start = 0;
            int end = 0;
            int separatorLen = separator.length();
            while ((end = line.indexOf(separator, start)) >= 0) {
                list.add(line.substring(start, end));
                start = end + separatorLen;
            }
            if (start < line.length()) {
                list.add(line.substring(start, line.length()));
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 组织数组为SQL查询中的In子句: (x,y,z)
     * 如果数组是字符串，那么组织为 ('x','y','z')
     *
     * @param conditions String[]
     * @param isString   boolean
     * @return String
     */
    public static final String getQryCondtion(String[] conditions,
                                              boolean isString) {
        if (conditions == null || conditions.length <= 0) {
            return null;
        }
        StringBuffer cond = new StringBuffer("(");
        for (int i = 0; i < conditions.length; i++) {
            if (isString) {
                cond.append("'").append(conditions[i]).append("'");
            } else {
                cond.append(conditions[i]);
            }
            cond.append(",");
        }
        cond.replace(cond.length() - 1, cond.length(), ")");

        return cond.toString();
    }

    /**
     * 解码
     *
     * @param inStr String
     * @return String
     */
    public static String decodeString(String inStr) {
        return inStr.replaceAll("’", "'")
                .replaceAll("”", "\"")
                .replaceAll("＜", "<")
                .replaceAll("＞", ">")
                .replaceAll("＆", "&");
    }

    public static String nullReplaceObj(Object oldObj, String repStr) {
        return oldObj == null ? repStr : oldObj.toString();
    }
}

