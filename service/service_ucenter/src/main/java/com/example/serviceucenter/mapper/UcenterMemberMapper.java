package com.example.serviceucenter.mapper;

import com.example.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Bob
 * @since 2022-07-24
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer selectRegisterCount(String value);
}
