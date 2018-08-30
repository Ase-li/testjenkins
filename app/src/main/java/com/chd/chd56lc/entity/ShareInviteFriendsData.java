package com.chd.chd56lc.entity;

import java.io.Serializable;

/**
 * @param
 * @author shulan 作者 E-mail:
 *         创建时间：2016-7-7
 *         类说明
 */
public class ShareInviteFriendsData implements Serializable {


    private static final long serialVersionUID = -2208402665299954618L;
    private String content;//内容
    private String title;//标题
    private String invite_rule_id;//
    private String key_char;//
    private String share_url;//分享链接
    private String invitor_id;//分享人id
    private String headimgurl;//分享图片
    private String channel_source;//分享渠道


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvite_rule_id() {
        return invite_rule_id;
    }

    public void setInvite_rule_id(String invite_rule_id) {
        this.invite_rule_id = invite_rule_id;
    }

    public String getKey_char() {
        return key_char;
    }

    public void setKey_char(String key_char) {
        this.key_char = key_char;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getInvitor_id() {
        return invitor_id;
    }

    public void setInvitor_id(String invitor_id) {
        this.invitor_id = invitor_id;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getChannel_source() {
        return channel_source;
    }

    public void setChannel_source(String channel_source) {
        this.channel_source = channel_source;
    }


}
