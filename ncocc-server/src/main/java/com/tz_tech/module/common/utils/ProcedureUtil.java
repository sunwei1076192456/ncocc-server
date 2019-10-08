package com.tz_tech.module.common.utils;

import com.tz_tech.module.common.dao.CommonHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProcedureUtil {

    /**
     * 执行存储过程
     * 无返回值，outParamStart=0
     */
    public static Map<String, Object> executeProcedure(final String[] params, final int outParamStart, String produceName) throws Exception{
        final int paramLen = (params != null) ? params.length : 0;
        StringBuffer sb = new StringBuffer();
        sb.append(" CALL ").append(produceName).append("(").append(StringUtils.repeat("?", ",", paramLen)).append(")");
        final String executeSQL = sb.toString();

        CallableStatementCreator csc = new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                CallableStatement cs = conn.prepareCall(executeSQL);
                if (params != null) {
                    int dbloop = 1;
                    for (int i = 0; i < outParamStart; i++) {
                        cs.setString(dbloop++, params[i]);
                    }
                    for (int i = outParamStart; i < paramLen; i++) {
                        cs.registerOutParameter(dbloop++, java.sql.Types.VARCHAR);
                    }
                }
                return cs;
            }
        };

        CallableStatementCallback<Map<String, Object>> callBack = new CallableStatementCallback<Map<String, Object>>() {
            @Override
            public Map<String, Object> doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {
                cs.execute();
                Map<String, Object> map = new HashMap<String, Object>();
                if (params != null) {
                    for (int i = outParamStart; i < paramLen; i++) {
                        map.put(params[i], StringUtils.defaultString(cs.getString(i + 1)));
                    }
                }
                return map;
            }
        };
        return CommonHelper.getCommonDao().execute(csc, callBack);
    }
}
