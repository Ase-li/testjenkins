package com.chd.chd56lc.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chd.chd56lc.R;
import com.chd.chd56lc.utils.DateToString;

import java.util.Date;
import java.util.List;

public class CouponBean {

    /**
     * couponType (integer, optional): 优惠券类型，1：提现卷，2：加息劵，3：现金红包，4：抵扣红包 ,
     * couponValues (number, optional): 优惠数值，金额或小数点 ,
     * endTime (string, optional): 结束时间 ,
     * id (integer, optional): 用户优惠券id ,
     * ifSpecifiedDate (boolean, optional): 是否指定日期；0：否，1：是 ,
     * interestDay (integer, optional): 加息天数 ,
     * investmentDay (integer, optional): 起投天数 ,
     * limitAmount (number, optional): 金额要求 ,
     * name (string, optional): 优惠券名称 ,
     * profit (number, optional): 收益 ,
     * projectName (string, optional): 项目名称 ,
     * startTime (string, optional): 开始时间 ,
     * useStatus (integer, optional): 使用状态；0：未使用，1：已使用，2：已锁定，3：已失效 ,
     * useTime (string, optional): 使用时间
     */

    private int count;
    private ExtrasBean extras;
    private int offset;
    private int page;
    private int total;
    private int totalPageNo;
    private List<Item> array;
    private double sentInterest;

    public double getSentInterest() {
        return sentInterest;
    }

    public void setSentInterest(double sentInterest) {
        this.sentInterest = sentInterest;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(int totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public List<Item> getArray() {
        return array;
    }

    public void setArray(List<Item> array) {
        this.array = array;
    }

    public static class ExtrasBean {
    }

    public static class Item implements Parcelable {

        /**
         * addInterestRate	number
         * 加息券-加息利率
         * <p>
         * addRateDays	integer($int32)
         * 加息券-加息天数
         * <p>
         * couponName	string
         * 优惠券名称
         * <p>
         * couponType	integer($int32)
         * 优惠券类型，1：提现卷，2：加息劵，4：抵扣红包
         * <p>
         * createdAt	string($date-time)
         * 创建时间
         * <p>
         * discountAmount	number
         * 抵扣红包-优惠金额
         * <p>
         * endTime	string($date)
         * 结束时间
         * <p>
         * id	integer($int64)
         * ifFullInterest	boolean
         * example: false
         * 是否全程加息；0：不是全程加息，1：全程加息
         * <p>
         * limitAmount	number
         * 抵扣红包/加息券-使用金额要求
         * <p>
         * projectList	[...]
         * startTime	string($date)
         * 开始时间
         * <p>
         * updatedAt	string($date-time)
         * 修改时间
         * <p>
         * useStatus	integer($int32)
         * 使用状态；0：未使用，1：已使用，2：已锁定，3：已过期
         * <p>
         * useTime	string($date-time)
         * 使用时间
         * <p>
         * userId	integer($int64)
         * 用户id
         * <p>
         * userName	string
         * 用户名
         * <p>
         * userPhone	string
         * 手机号
         */
        public boolean isSelect;
        private double addInterestRate;
        private int addRateDays;
        private String couponName;
        private int couponType;
        private String createdAt;
        private double discountAmount;
        private Date endTime;
        private String id;
        private boolean ifFullInterest;
        private double limitAmount;
        private String startTime;
        private String updatedAt;
        private int useStatus;
        private String useTime;
        private String userId;
        private String userName;
        private String userPhone;
        private List<ProjectListBean> projectList;

        protected Item(Parcel in) {
            isSelect = in.readByte() != 0;
            addInterestRate = in.readDouble();
            addRateDays = in.readInt();
            couponName = in.readString();
            couponType = in.readInt();
            createdAt = in.readString();
            discountAmount = in.readDouble();
            id = in.readString();
            ifFullInterest = in.readByte() != 0;
            limitAmount = in.readDouble();
            startTime = in.readString();
            updatedAt = in.readString();
            useStatus = in.readInt();
            useTime = in.readString();
            userId = in.readString();
            userName = in.readString();
            userPhone = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };

        public String getUserStatusName() {
            String status = "";
            switch (useStatus) {
                case 0:
                    status = "使用";
                    break;
                case 1:
                    status = "已使用";
                    break;
                case 2:
                    status = "已锁定";
                    break;
                case 3:
                    status = "已过期";
                    break;
            }

            return status;
        }

        public String getCouponTypeName() {
            if (couponType == 1)
                return "提现券";
            else if (couponType == 2)
                return "加息券";
            else
                return "红包";
        }

        public int getUserStatusBackground() {
            int resourceId = R.mipmap.btn_ysyyhq_ysy;
            switch (couponType) {
                case 1:
                    resourceId = useStatus == 0 ? R.mipmap.btn_qt_sy : (useStatus == 2 ? R.mipmap.btn_qt_sdz : R.mipmap.btn_ysyyhq_ysy);
                    break;
                case 2:
                    resourceId = useStatus == 0 ? R.mipmap.btn_jxq_sy : (useStatus == 2 ? R.mipmap.btn_jxq_sdz : R.mipmap.btn_ysyyhq_ysy);
                    break;
                case 4:
                    resourceId = useStatus == 0 ? R.mipmap.btn_xjhb_sy : (useStatus == 2 ? R.mipmap.btn_xjhb_sdz : R.mipmap.btn_ysyyhq_ysy);
                    break;
            }
            return resourceId;
        }

        public double getAddInterestRate() {
            return addInterestRate;
        }

        public void setAddInterestRate(int addInterestRate) {
            this.addInterestRate = addInterestRate;
        }

        public int getAddRateDays() {
            return addRateDays;
        }

        public void setAddRateDays(int addRateDays) {
            this.addRateDays = addRateDays;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        /**
         * 1：提现卷，2：加息劵，4：抵扣红包
         *
         * @return
         */
        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getEndTime() {
            return DateToString.formalDateString(endTime, "yyyy.MM.dd");

        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIfFullInterest() {
            return ifFullInterest;
        }

        public void setIfFullInterest(boolean ifFullInterest) {
            this.ifFullInterest = ifFullInterest;
        }

        public double getLimitAmount() {
            return limitAmount;
        }

        public void setLimitAmount(double limitAmount) {
            this.limitAmount = limitAmount;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getUseStatus() {
            return useStatus;
        }

        public void setUseStatus(int useStatus) {
            this.useStatus = useStatus;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public List<ProjectListBean> getProjectList() {
            return projectList;
        }

        public void setProjectList(List<ProjectListBean> projectList) {
            this.projectList = projectList;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (isSelect ? 1 : 0));
            dest.writeDouble(addInterestRate);
            dest.writeInt(addRateDays);
            dest.writeString(couponName);
            dest.writeInt(couponType);
            dest.writeString(createdAt);
            dest.writeDouble(discountAmount);
            dest.writeString(id);
            dest.writeByte((byte) (ifFullInterest ? 1 : 0));
            dest.writeDouble(limitAmount);
            dest.writeString(startTime);
            dest.writeString(updatedAt);
            dest.writeInt(useStatus);
            dest.writeString(useTime);
            dest.writeString(userId);
            dest.writeString(userName);
            dest.writeString(userPhone);
        }

        public static class ProjectListBean {
            /**
             * id : 0
             * projectName : string
             */

            private String id;
            private String projectName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }
        }
    }
}
