package com.chd.chd56lc.entity;

import java.io.Serializable;

/**
 * Created by li on 2017/12/21.
 */

public class BaseBean<T> implements Serializable {


    //期望是对象但实际上是数组
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * status : {"errCode":2104,"message":"找不到该班级活动"}
     */

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class StatusBean {
        /**
         * errCode : 2104
         * message : 找不到该班级活动
         */

        private int errCode;
        private String message;

        public StatusBean(int errCode, String message) {
            this.errCode = errCode;
            this.message = message;
        }

        public StatusBean() {
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}