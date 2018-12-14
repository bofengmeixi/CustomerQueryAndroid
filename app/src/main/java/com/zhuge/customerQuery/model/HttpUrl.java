package com.zhuge.customerQuery.model;

public class HttpUrl {
    //public final static String baseUrl="http://54.222.236.168:8080/zhugefront";
    //public final static String baseUrl="http://192.168.33.105:8080/v1";
    public final static String baseUrl="http://54.222.236.168:8084/v1";

    /*public final static String COMPANY_BY_EMAIL=baseUrl+"/getCompanyByEmailServlet";
    public final static String COMPANY_BY_COMPANYNAME=baseUrl+"/getCompanyByCompanyNameServlet";
    public final static String COMPANY_BY_APPID=baseUrl+"/getCompanyByAppIdServlet";
    public final static String COMPANY_BY_COMPANYID=baseUrl+"/getCompanyAppByCompanyIdServlet";*/

    public final static String COMPANY_BY_EMAIL=baseUrl+"/email/";
    public final static String COMPANY_BY_COMPANYNAME=baseUrl+"/name/";
    public final static String COMPANY_BY_APPID=baseUrl+"/app/";
    public final static String COMPANY_BY_COMPANYID=baseUrl+"/company/";

    public final static String LOGIN="http://54.222.236.168:8084/AuthTest";
}
