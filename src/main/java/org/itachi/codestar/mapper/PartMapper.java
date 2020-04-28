package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.codestar.domain.*;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author itachi
 * @since 2018/3/20 21:30
 */
@Mapper
public interface PartMapper {
    //查询库存
    List<PartConfiguration> findPart(Map<String, Object> parameters);

    //排期零件
    List<PlannedScheduling> findPlanDeviceId(Map<String, Object> parameters);
     //查询出库零件
    List<PlannedScheduling> findOutPlanDeviceId(Map<String, Object> parameters);
    int findOutPlanDeviceIdCount(Map<String, Object> parameters);
    //查询装配零件
    List<PlannedScheduling> findAssemblingPlanDeviceId(Map<String, Object> parameters);
    //更新出库零件状态
    void updateOutModifyIdIn(List<Long> ids);
    //单个更新出库零件状态
    void updateAssemblingStatusById(Long id);
    //更新需要装配的零件
    void updateAssemblingStatus(List<Long> ids);
    //单个更新需要装配的零件
    void updateAssemblingStatusSingle(Long id);
    void updateAssemblingStatusDel(List<Long> ids);

    void updateAssemblingStatusDelSingle(Long id);
    int findAssemblingPlanDeviceIdCount(Map<String, Object> parameters);
    //临时计划单
    List<WarehouseTemp> findPartTemp(Map<String, Object> parameters);

    //查询零件标准
    List<DeviceStandard> findManageStandard(Map<String, Object> parameters);

    /**
     * 设备装配完成n
     *
     * @param completeState
     */
    void updateCompleteModifyIdIn(List<Long> completeState);

    /*
     *装备完成查询
     */
    List<Assembling> findAssembling(Map<String, Object> parameters);

    /**
     * 调试查询
     *
     * @param parameters
     * @return
     */

    List<Assembling> findTestDevicd(Map<String, Object> parameters);

    List<Entrucking> findIds(Map<String, Object> parameters);


    Integer findOutNumber(String time);

    Integer findLoadingNumber(String da);

    int countRepair(Map<String, Object> map);

     //维修管理查询
     List<Entrucking> findRepair(Map<String, Object> parameters);

    int countTestDevice(Map<String, Object> map);

    int countPartName(Map<String, Object> map);

    /**
     * 零件列表查询
     * @param parameters
     * @return
     */
    List<PartConfiguration> findPartList(Map<String, Object> parameters);
}
