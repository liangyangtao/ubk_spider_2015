package com.unbank.mybatis.dao;

import com.unbank.mybatis.entity.EveryWebDayNum;
import com.unbank.mybatis.entity.EveryWebDayNumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EveryWebDayNumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int countByExample(EveryWebDayNumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int deleteByExample(EveryWebDayNumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int deleteByPrimaryKey(Integer numId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int insert(EveryWebDayNum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int insertSelective(EveryWebDayNum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    List<EveryWebDayNum> selectByExample(EveryWebDayNumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    EveryWebDayNum selectByPrimaryKey(Integer numId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int updateByExampleSelective(@Param("record") EveryWebDayNum record, @Param("example") EveryWebDayNumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int updateByExample(@Param("record") EveryWebDayNum record, @Param("example") EveryWebDayNumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int updateByPrimaryKeySelective(EveryWebDayNum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptf_day_num_web
     *
     * @mbggenerated Mon Sep 14 16:07:34 GMT+08:00 2015
     */
    int updateByPrimaryKey(EveryWebDayNum record);
}