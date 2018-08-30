package com.chd.chd56lc.entity;

/**
 * beginnerTask	integer($int32)
 * 新手任务 0-注册 1-开户 2-首投
 * <p>
 * countBo	MessageCountBo{
 * count	integer($int32)
 * 消息数
 * <p>
 * }
 * promotionBo	HomePromotionBo{
 * activityUrl	string
 * 跳转地址
 * <p>
 * id	integer($int64)
 * id
 * <p>
 * imgUrl	string
 * 图片地址
 * <p>
 * title	string
 * 标题
 * <p>
 * type	integer($int32)
 * 类型,1.首投宣传,2.推广宣传
 */
public class HomeDataBean {
    private int beginnerTask = -1;
    private CountBoBean countBo;
    private PromotionBoBean promotionBo;

    public int getBeginnerTask() {
        return beginnerTask;
    }

    public void setBeginnerTask(int beginnerTask) {
        this.beginnerTask = beginnerTask;
    }

    public CountBoBean getCountBo() {
        return countBo;
    }

    public void setCountBo(CountBoBean countBo) {
        this.countBo = countBo;
    }

    public PromotionBoBean getPromotionBo() {
        return promotionBo;
    }

    public void setPromotionBo(PromotionBoBean promotionBo) {
        this.promotionBo = promotionBo;
    }

    public static class CountBoBean {
        /**
         * count : 0
         */

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class PromotionBoBean {
        /**
         * activityUrl : string
         * id : 0
         * imgUrl : string
         * title : string
         * type : 0
         */

        private String activityUrl;
        private String id;
        private String imgUrl;
        private String title;
        private int type;

        public String getActivityUrl() {
            return activityUrl;
        }

        public void setActivityUrl(String activityUrl) {
            this.activityUrl = activityUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
