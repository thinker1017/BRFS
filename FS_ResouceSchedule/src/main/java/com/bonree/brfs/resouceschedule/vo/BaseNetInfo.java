package com.bonree.brfs.resouceschedule.vo;

import com.alibaba.fastjson.JSONObject;
import com.bonree.brfs.resouceschedule.vo.ServerEnum.NET_ENUM;

/*******************************************************************************
 * 版权信息：北京博睿宏远数据科技股份有限公司
 * Copyright: Copyright (c) 2007北京博睿宏远数据科技股份有限公司,Inc.All Rights Reserved.
 *
 * @date 2018-3-7
 * @author: <a href=mailto:zhucg@bonree.com>朱成岗</a>
 * Description: 
 * Version: 网卡基本信息
 ******************************************************************************/
public class BaseNetInfo {
    /**
     * 网卡设备名称
     */
    private String devName;
    /**
     * 网卡mac
     */
    private String macAddress;
    /**
     * 网卡IP
     */
    private String ipAddress;
    /**
     * 网卡最大接收速度 单位byte
     */
    private long maxRSpeed;
    /**
     * 网卡最大发送速度 单位byte
     */
    private long maxTSpeed;

    public BaseNetInfo(String devName, String macAddress, String ipAddress, long maxRSpeed, long maxTSpeed) {
        this.devName = devName;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.maxRSpeed = maxRSpeed;
        this.maxTSpeed = maxTSpeed;
    }

    public BaseNetInfo(String devName, String macAddress, String ipAddress) {
        this.devName = devName;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
    }
    public BaseNetInfo() {

    }
    public JSONObject toJSONObject(){
    	JSONObject obj = new JSONObject();
    	obj.put(NET_ENUM.NET_DEVICE_NAME.name(), this.devName);
    	obj.put(NET_ENUM.IP_ADDRESS.name(), this.ipAddress);
    	obj.put(NET_ENUM.MAC_ADDRESS.name(), this.macAddress);
    	obj.put(NET_ENUM.MAX_R_SPEED.name(), this.maxRSpeed);
    	obj.put(NET_ENUM.MAX_T_SPEED.name(), this.maxTSpeed);
    	return obj;
    }
    public String toString(){
    	return toJSONObject().toString();
    }
    public String toJSONString(){
    	return toJSONObject().toJSONString();
    }
    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getMaxRSpeed() {
        return maxRSpeed;
    }

    public void setMaxRSpeed(long maxRSpeed) {
        this.maxRSpeed = maxRSpeed;
    }

    public long getMaxTSpeed() {
        return maxTSpeed;
    }

    public void setMaxTSpeed(long maxTSpeed) {
        this.maxTSpeed = maxTSpeed;
    }
}