package org.itachi.codestar.service;

import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.*;
import org.itachi.codestar.mapper.DeviceMapper;
import org.itachi.codestar.mapper.OrderMapper;
import org.itachi.codestar.repositories.jpa.JpaDeviceRepository;
import org.itachi.codestar.repositories.jpa.JpaHistoricalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *零件配置服务
 * @author itachi
 * @since 2018/3/9 20:00
 */
@Service
public class HistoricalRecordService {
    private static final String PAGER = "pager";
    SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM");
    @Autowired
    private JpaHistoricalRecordRepository repository;
    @Autowired
    private JpaDeviceRepository deviceRepository;
    @Autowired
    private OrderMapper mapper;
    @Autowired
    private DeviceMapper deviceMapper;
    /**
     * 分页查询
     * @param status
     * @param pager
     * @return
     */
    public Map<String, Object> findManageService(String partId,String status, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isBlank(partId)) {
            return null;
           }
         Page<HistoricalRecord> servicePage =null;
         if("3".equals(status)) {
             pager.setTotal(repository.countByPartId(Long.valueOf(partId)));
             servicePage = repository.findByPartId(Long.valueOf(partId), PageRequest.of(pager.getPage() - 1, pager.getRows()));
         }else {
             pager.setTotal(repository.countByPartIdAndStatus(Long.valueOf(partId),status));
             servicePage = repository.findByPartIdAndStatus(Long.valueOf(partId),status, PageRequest.of(pager.getPage() - 1, pager.getRows()));
         }
        map.put("historicalRecords", servicePage.getContent());
        map.put("pager", pager);
        return map;
    }

    public Map<String, Object> findOverview(Pager pager,String orderNumber,String deviceNumber){
        Map<String, Object> map = new HashMap<>(16);
        map.put("orderNumber", orderNumber);
        map.put("deviceNumber", deviceNumber);
        int count = deviceMapper.findOverviewCount(map);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        pager.setTotal(count);
        List<PlanDevice> planDevices = deviceMapper.findOverview(map);
        map.put("overviews", planDevices);
        map.put("pager", pager);
        return map;
    }

    public  Map<String, Object>  findByReport()throws Exception  {
        Map<String, Object> map = new HashMap<>(16);
        circularReport(map);
        columnReport(map);
        return map;
    }
    //圆形报表
    public void circularReport(Map<String, Object> map){
        Calendar ca = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        int month = ca.get(Calendar.MONTH) + 1;
        String[] category = new String[month];
        List<Report> li = new ArrayList<Report>();
        for (int i = 0 ; i<month;i++) {
            Report report = new Report();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(new Date());
            rightNow.add(Calendar.MONTH,-i);//日期加3个月
            Date dt1=rightNow.getTime();
            String time = df_.format(dt1);
            Integer sum = mapper.findSum(time);
            if(sum ==null){
                sum = 0;
            }
            report.setValue(sum);
            String caData = rightNow.get(Calendar.MONTH)+1 +"月";
            report.setName(caData);
            category[i] = caData;
            li.add(report);
        }
        map.put("report",li);
        map.put("caData",category);
    }
    //柱形报表
    public void columnReport(Map<String, Object> map){
        Calendar ca = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        int month = ca.get(Calendar.MONTH) + 1;
        List<Device> devices = deviceRepository.findByIsCore(1);
        String[] isCore = new String[devices.size()];
        String[] category = new String[month];
       List<Core> coreList=new ArrayList<Core>();
        for(int i = 0 ; i< devices.size();i++){
            Core core = new Core();
            isCore[i]=devices.get(i).getDeviceName();
            core.setName(devices.get(i).getDeviceName());
            core.setType("bar");
            int[] data = new int[month];
            List<HashMap> hashMap= mapper.findCore(devices.get(i).getId());
            for(int j = 0 ; j < hashMap.size();j++){
                int dataCount = Integer.valueOf(hashMap.get(j).get("count").toString());
                int dataIndex= Integer.valueOf(hashMap.get(j).get("time").toString())-1;
                data[dataIndex]=dataCount;
            }
            core.setData(data);
            coreList.add(core);
        }
        for(int i = 0 ; i < month ;i++){
            String y = i+1+"月";
            category[i] = y;
        }
        map.put("category",category);
        map.put("coreName",isCore);
        map.put("data",coreList);

    }

}
