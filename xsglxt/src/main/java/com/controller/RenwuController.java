package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;

import com.entity.YonghuEntity;
import com.service.YonghuService;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.RenwuEntity;

import com.service.RenwuService;
import com.entity.view.RenwuView;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 任务
 * 后端接口
 * @author
 * @email
 * @date 2021-03-02
*/
@RestController
@Controller
@RequestMapping("/renwu")
public class RenwuController {
    private static final Logger logger = LoggerFactory.getLogger(RenwuController.class);

    @Autowired
    private RenwuService renwuService;
    @Autowired
    private YonghuService yonghuService;


    @Autowired
    private TokenService tokenService;


    //级联表service

    //字典表map
    Map<String, Map<Integer, String>> dictionaryMap;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
    logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && ("学生".equals(role) ||"班干部".equals(role) )){
            YonghuEntity yonghuEntity = yonghuService.selectById((Integer)request.getSession().getAttribute("userId"));
            params.put("yonghuTypes",yonghuEntity.getYonghuTypes());
        }
    PageUtils page = renwuService.queryPage(params);

    //字典表数据转换
    List<RenwuView> list =(List<RenwuView>)page.getList();
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        for(RenwuView c:list){
        this.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        RenwuEntity renwu = renwuService.selectById(id);
        if(renwu !=null){
            //entity转view
            RenwuView view = new RenwuView();
            BeanUtils.copyProperties( renwu , view );//把实体数据重构到view中

            //字典表字典转换
            ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            dictionaryMap = (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
            this.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody RenwuEntity renwu, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,renwu:{}",this.getClass().getName(),renwu.toString());
        renwu.setInsertTime(new Date());
        renwu.setCreateTime(new Date());
        renwuService.insert(renwu);
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody RenwuEntity renwu, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,renwu:{}",this.getClass().getName(),renwu.toString());
        renwuService.updateById(renwu);//根据id更新
        return R.ok();
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        renwuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    *字典表数据转换
    */
    public void dictionaryConvert(RenwuView renwuView){
        //当前表的字典字段
        if(StringUtil.isNotEmpty(String.valueOf(renwuView.getRenwuTypes()))){
            renwuView.setRenwuValue(dictionaryMap.get("renwu_types").get(renwuView.getRenwuTypes()));
        }
        if(StringUtil.isNotEmpty(String.valueOf(renwuView.getYonghuTypes()))){
            renwuView.setYonghuValue(dictionaryMap.get("yonghu_types").get(renwuView.getYonghuTypes()));
        }

        //级联表的字典字段
    }


}

