package com.example.servicecms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.servicecms.entity.CrmBanner;
import com.example.servicecms.mapper.CrmBannerMapper;
import com.example.servicecms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Bob
 * @since 2022-07-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
