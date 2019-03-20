package com.tz_tech.module.common.utils;

public class ConstantUtil {

    public enum BusinessCommandCode{
        COMMAND_CODE_ONE("createWork", "创建工单");

        private String code;
        private String name;

        BusinessCommandCode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
