package com.chd.chd56lc.constant;

public interface Url {
    /**
     * 本地测试
     */
//    String HOSTHOME = "http://10.0.0.31:7777/#";
//    String HOSTHOME = "http://10.0.0.88:7800//wechat/dist/#";
//    String HOST = "http://10.0.0.88:10020/";
    /**
     * 线上SSL 正式
     */
//    String HOST = "http://47.106.182.95/";
//    String HOSTHOME = "http://47.106.182.95/wechat/#";

    String HOST = "https://test.56p2b.com/";
    String HOSTHOME = "https://test.56p2b.com/wechat/#";


    //网络借贷风险和禁止性行为提示书
    String LENDING_NETWORK_HINT = HOSTHOME + "/contract/contract-1";

    //反洗钱、反恐怖融资承诺书
    String FUNDS_LEGAL_UNDERTAKING = HOSTHOME + "/contract/contract-2";

    //个人电子签名章授权委托书
    String SIGNATURE_SEAL_ATTORNEY = HOSTHOME + "/contract/contract-3";

    //充值提现规则说明
    String PREPAID_WITHDRAWAL_RULES = HOSTHOME + "/contract/contract-4";

    //用户快捷充值协议
    String USER_QUICK_RECHARGE_PROTOCOL = HOSTHOME + "/contract/contract-7";

    /**
     * 隐私协议
     */
    String PRIVACY_AGREEMENT = HOSTHOME + "/contract/contract-6";

    //借款合同
    String LOAN_CONTRACT = HOSTHOME + "/contract/contract-8";

    //债权转让
    String ASSIGNMEN_OF_DEBT = HOSTHOME + "/contract/contract-9";

    /**
     * 56金服注册协议
     */
    String REGISTER_AGREEMENT = "file:///android_asset/user_register_agreement.html";

    /**
     * 客服中心
     */
    String USER_MYSERVICE = HOSTHOME + "/customerService";
    /**
     * 风险测评
     */
    String RISK_ASSESS = HOSTHOME + "/riskQuiz";
    /**
     * 存管指南
     */
    String DEPOSITORY_GUIDE = HOSTHOME + "/guide/depository-guide";
    /**
     * 信息披露
     */
    String INFORMATION_DISCLOSURE = HOSTHOME + "/infoShow";
    /**
     * 邀请有礼
     */
    String REWARD_INVITING = HOSTHOME + "/invitor";
    /**
     * 风险管理
     */
    String RISK_CONTROL = HOSTHOME + "/dangerManage";
    /**
     * 银行限额表
     */
    String BANK_LIMIT_SHEET = HOSTHOME + "/investment/bindCard/bankLimit";
    /**
     * 标的详情
     */
    String BIDS_DETAIL = HOSTHOME + "/investment/bidsDetail";
    /**
     * 多重保障
     */
    String MULTISCURE = HOSTHOME + "/discovery/multiscure";
}
