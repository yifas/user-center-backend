package com.yupi.usercenter.model.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yupi.usercenter.model.domain.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserQueryVO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;


    /**
     * 电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private String createTime;


}
