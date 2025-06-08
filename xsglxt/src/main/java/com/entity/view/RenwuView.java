package com.entity.view;

import com.entity.RenwuEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email
 * @date 2021-03-02
 */
@TableName("renwu")
public class RenwuView extends RenwuEntity implements Serializable {
    private static final long serialVersionUID = 1L;
		/**
		* 任务类型的值
		*/
		private String renwuValue;
		/**
		* 接收身份的值
		*/
		private String yonghuValue;



	public RenwuView() {

	}

	public RenwuView(RenwuEntity renwuEntity) {
		try {
			BeanUtils.copyProperties(this, renwuEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 任务类型的值
			*/
			public String getRenwuValue() {
				return renwuValue;
			}
			/**
			* 设置： 任务类型的值
			*/
			public void setRenwuValue(String renwuValue) {
				this.renwuValue = renwuValue;
			}
			/**
			* 获取： 接收身份的值
			*/
			public String getYonghuValue() {
				return yonghuValue;
			}
			/**
			* 设置： 接收身份的值
			*/
			public void setYonghuValue(String yonghuValue) {
				this.yonghuValue = yonghuValue;
			}










}
