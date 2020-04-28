package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.codestar.domain.Device;
import org.itachi.codestar.domain.OrderDevice;
import org.itachi.codestar.domain.PlanDevice;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:30
 */
@Mapper
public interface DeviceMapper {

    List<OrderDevice> findOrderManage(Map<String, Object> parameters);
    //设备明细
    List<OrderDevice> findPannedManage(Map<String, Object> parameters);
    void updateStatus(Long id);
    //确定排期
    void updatePlanStatus(List<Long> ids);
    //撤销点单
    void updateStatusIdIn(List<Long> ids);
    //减少数量
    void updateDeviceCount(int count ,Long id);
    //减少排期
    void updatePlan(List<Long> ids);

    void updateDeviceId(@Param("deviceId") Long deviceId,@Param("planDeviceId") Long planDeviceId);

    //计划排期
    List<PlanDevice> findPlanManage(Map<String, Object> parameters);

    //查询计划排期数量
    int countPlanManage(Map<String, Object> parameters);

    //模糊查询订单编号
    int countOrder(Map<String, Object> parameters);

    //计划出库
    List<PlanDevice> findPlanManageOut(Map<String, Object> parameters);
    String findOrderDate();
    Integer findPlanNumber(String da);

    PlanDevice fidPlanOne(Long planDeviceId);

    //查询生产总览
    List<PlanDevice> findOverview(Map<String, Object> parameters);

    int findOverviewCount(Map<String, Object> parameters);

    int countDevice(Map<String, Object> map);

    List<Device> findDevice(Map<String, Object> map);
}
