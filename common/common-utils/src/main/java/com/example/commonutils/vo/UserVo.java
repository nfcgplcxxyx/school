package com.example.commonutils.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    //    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String openid;

    private String mobile;

    private String password;

    private String nickname;

    private Integer sex;

    private Integer age;

    private String avatar;

    private String sign;

    private Boolean isDisabled;

    private Boolean isDeleted;

    //    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    //    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
