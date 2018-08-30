package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BalanceApi {

    //查询个人银行信息,包括余额
    @FormUrlEncoded
    @POST("api/service-depository/app/funds/queryRechargeUserBankInfo")
    Observable<BaseBean<BankInfoOrBalanceBean>> queryRechargeUserBankInfo(@Field("type") int type);

    //绑定卡到电子账户充值（页面）
    @FormUrlEncoded
    @POST("api/service-depository/app/bankroll/rechargeP")
    Observable<BaseBean<DepositLinkBean>> rechargeP(@Field("amount") double amount,@Field("orderComefrom") int orderComefrom);

    //提现(页面)
    @FormUrlEncoded
    @POST("api/service-depository/app/bankroll/withdrawP")
    Observable<BaseBean<DepositLinkBean>> withdrawP(@Field("amount") double amount, @Field("couponStatus") boolean couponStatus,@Field("orderComefrom") int orderComefrom);

    //个人用户提现信息查询
    @FormUrlEncoded
    @POST("api/service-depository/app/withdraw/queryPersonalWithdrawInfo")
    Observable<BaseBean<WithDrawInfo>> queryPersonalWithdrawInfo(@Field("amount") String amount);

}
