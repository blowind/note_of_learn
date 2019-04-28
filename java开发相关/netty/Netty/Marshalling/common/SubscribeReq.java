package com.zxf.nio.marshalling;

import java.io.Serializable;

/**
 * @ClassName: SubscribeReq
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/28 23:57
 * @Version: 1.0
 **/
public class SubscribeReq implements Serializable {
    /**
     * 默认的序列号ID
     */
    private static final long serialVersionUID = 1L;

    private int subReqID;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;

    public final int getSubReqID() {
        return subReqID;
    }

    public final void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getProductName() {
        return productName;
    }

    public final void setProductName(String productName) {
        this.productName = productName;
    }

    public final String getPhoneNumber() {
        return phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public final String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName
                + ", productName=" + productName + ", phoneNumber="
                + phoneNumber + ", address=" + address + "]";
    }
}
