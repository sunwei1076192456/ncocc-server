package com.tz_tech.module.common.dao;

import com.tz_tech.module.common.utils.SpringContextUtil;
import com.tz_tech.module.common.utils.StringHelper;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.javassist.*;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonHelper {

    private static Logger logger = org.apache.log4j.LogManager.getLogger(CommonHelper.class);

    private static HashMap<String, CommonDao> daoMap = new HashMap<String, CommonDao>();

    /**
     * 如果正确函数返回一个boolean型的true； 相反则返回一个false；
     */
    public static boolean isValidDate(String sDate, String patternStr) {
        String datePattern = null == patternStr
                ? "\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}"
                : patternStr;
        //    String datePattern2 =
        //                "^((\\d{2}(([02468][048])|([13579][26]))"
        //                                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
        //                                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
        //                                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
        //                                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
        //                                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern);
            Matcher match = pattern.matcher(sDate);
            return match.matches();

            // if (match.matches()) {
            // pattern = Pattern.compile(datePattern2);
            // match = pattern.matcher(sDate);
            // return match.matches();
            // }
            // else {
            // return false;
            // }
        }
        return false;
    }

    /**
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @author:qi.qingli
     * @date:Jul 10, 2013 2:20:18 PM
     */
    public static CommonDao getCommonDao()
            throws Exception {
        //return (CommonDao) BaseDAOFactory.getImplDAO(CommonDao.class.getName());
        //修改为不从缓存中取dao实例 - qi.qingli@2014-5-26 15:21
        return getCommonDao("dataSource");
    }

    /**
     * 获取多数据源DAO
     *
     * @param dataSourceName
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException qiqingli Dec 4, 2017 6:28:28 PM
     */
    public static CommonDao getCommonDao(String dataSourceName)
            throws Exception {
        if(null == dataSourceName || dataSourceName.trim().length() <= 0) {
            dataSourceName = "dataSource";
        }

        CommonDao dao = null;
        if (daoMap.containsKey(dataSourceName)) {
            dao = daoMap.get(dataSourceName);
        } else {
            dao = new CommonDao().setDataSource(getDataSource(dataSourceName));
            daoMap.put(dataSourceName, dao);
        }
        logger.info("===========>getDataSourceDAO:[" + dataSourceName + "] :" + dao);

        return dao;
    }

    /**
     * 获取数据源
     *
     * @param dataSourceName
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException qiqingli Dec 12, 2017 2:09:18 PM
     */
    public static DataSource getDataSource(String dataSourceName)
            throws Exception {
        logger.info("===========>getDataSource:" + dataSourceName);
        return (DataSource) SpringContextUtil.getBean(dataSourceName);
    }
}
