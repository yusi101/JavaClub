package com.JavaClub.entity.system;

import java.util.Date;

/**
 *
* @ClassName: User
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 李海涛
* @date 2016年3月24日 下午2:39:52
*
 */
public class User {

	private String USER_ID;		    //用户id
	private String USERNAME;	    //用户名
	private String PASSWORD; 	    //密码
	private String LAST_PASSWORD;   //最后一次修改前密码
	private String NAME;		    //姓名
	private Date LAST_LOGIN;	    //最后登录时间
	private String IP;			    //用户登录ip地址
	private String STATUS;		    //状态
	private String REMARK;          //备注
	private String SEX;                //性别
	private String SKIN;		    //皮肤
	private String EMAIL;           //邮箱
	private String PRIPID;          //企业pripid
	private String PHONE;           //电话号码
	private String MEMBER_ID;		//对应会员ID
	private String AREA_CODE;       //区域

    /**
     * @return the uSER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param uSER_ID the uSER_ID to set
     */
    public void setUSER_ID(String uSER_ID) {
        USER_ID = uSER_ID;
    }

    /**
     * @return the uSERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * @param uSERNAME the uSERNAME to set
     */
    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

    /**
     * @return the pASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * @param pASSWORD the pASSWORD to set
     */
    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }

    public String getPRIPID() {
		return PRIPID;
	}

	public void setPRIPID(String pRIPID) {
		PRIPID = pRIPID;
	}

	/**
     * @return the lAST_PASSWORD
     */
    public String getLAST_PASSWORD() {
        return LAST_PASSWORD;
    }

    /**
     * @param lAST_PASSWORD the lAST_PASSWORD to set
     */
    public void setLAST_PASSWORD(String lAST_PASSWORD) {
        LAST_PASSWORD = lAST_PASSWORD;
    }

    /**
     * @return the nAME
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * @param nAME the nAME to set
     */
    public void setNAME(String nAME) {
        NAME = nAME;
    }

    /**
     * @return the lAST_LOGIN
     */
    public Date getLAST_LOGIN() {
        return LAST_LOGIN;
    }

    /**
     * @param lAST_LOGIN the lAST_LOGIN to set
     */
    public void setLAST_LOGIN(Date lAST_LOGIN) {
        LAST_LOGIN = lAST_LOGIN;
    }

    /**
     * @return the iP
     */
    public String getIP() {
        return IP;
    }

    /**
     * @param iP the iP to set
     */
    public void setIP(String iP) {
        IP = iP;
    }

    /**
     * @return the sTATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * @param sTATUS the sTATUS to set
     */
    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    /**
     * @return the rEMARK
     */
    public String getREMARK() {
        return REMARK;
    }

    /**
     * @param rEMARK the rEMARK to set
     */
    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }

    /**
     * @return the sEX
     */
    public String getSEX() {
        return SEX;
    }

    /**
     * @param sEX the sEX to set
     */
    public void setSEX(String sEX) {
        SEX = sEX;
    }

    /**
     * @return the sKIN
     */
    public String getSKIN() {
        return SKIN;
    }

    /**
     * @param sKIN the sKIN to set
     */
    public void setSKIN(String sKIN) {
        SKIN = sKIN;
    }

    /**
     * @return the eMAIL
     */
    public String getEMAIL() {
        return EMAIL;
    }

    /**
     * @param eMAIL the eMAIL to set
     */
    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    /**
     * @return the pHONE
     */
    public String getPHONE() {
        return PHONE;
    }

    /**
     * @param pHONE the pHONE to set
     */
    public void setPHONE(String pHONE) {
        PHONE = pHONE;
    }

    /**
     * @return the mEMBER_ID
     */
    public String getMEMBER_ID() {
        return MEMBER_ID;
    }

    /**
     * @param mEMBER_ID the mEMBER_ID to set
     */
    public void setMEMBER_ID(String mEMBER_ID) {
        MEMBER_ID = mEMBER_ID;
    }

    /**
     * @return the aREA_CODE
     */
    public String getAREA_CODE() {
        return AREA_CODE;
    }

    /**
     * @param aREA_CODE the aREA_CODE to set
     */
    public void setAREA_CODE(String aREA_CODE) {
        AREA_CODE = aREA_CODE;
    }





}
