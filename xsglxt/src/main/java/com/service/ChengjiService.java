package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChengjiEntity;
import java.util.Map;

/**
 * 成绩 服务类
 * @author 
 * @since 2021-03-02
 */
public interface ChengjiService extends IService<ChengjiEntity> {

     PageUtils queryPage(Map<String, Object> params);

}