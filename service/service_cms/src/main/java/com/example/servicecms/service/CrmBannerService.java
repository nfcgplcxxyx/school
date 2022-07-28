package com.example.servicecms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Bob
 * @since 2022-07-22
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectIndexList();
}
