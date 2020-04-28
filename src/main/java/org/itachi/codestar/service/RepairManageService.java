package org.itachi.codestar.service;
import org.apache.commons.lang3.StringUtils;
import org.itachi.codestar.domain.Assembling;
import org.itachi.codestar.domain.Entrucking;
import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.domain.RepairManage;
import org.itachi.codestar.exception.ServiceException;
import org.itachi.codestar.mapper.PartMapper;
import org.itachi.codestar.repositories.jpa.JpaAssemblingRepository;
import org.itachi.codestar.repositories.jpa.JpaRepairManageRepository;
import org.itachi.codestar.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairManageService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PAGER = "pager";
    private static final String REPAIRMANAGES = "repairmanages";
    @Autowired
    private PartMapper mapper;
    @Autowired
    private JpaRepairManageRepository repository;
    @Autowired
    private JpaAssemblingRepository assemblingRepository;
    /**
     * 维修管理查询
     * @param orderNumber
     * @param pager
     * @return
     */
    public Map<String,Object> repair(String orderNumber, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("orderNumber",orderNumber);
        int count = mapper.countRepair(map);
        pager.setTotal(count);
        map.put("offset", (pager.getPage() - 1) * pager.getRows());
        map.put("rows", pager.getRows());
        List<Entrucking> repairManages = mapper.findRepair(map);
        map.put(REPAIRMANAGES, repairManages);
        map.put(PAGER, pager);
        return map;
    }

    /**
     * 维修明细
     * @param loadingId
     * @return
     * @throws ServiceException
     */
    public Map<String,Object> repairDetailed(String loadingId, Pager pager) {
        Map<String, Object> map = new HashMap<>(16);
        if(StringUtils.isBlank(loadingId)){
            return null;
        }
        pager.setTotal(repository.countByLoadingId(Long.valueOf(loadingId)));
        Page<RepairManage> servicePage = repository.findByLoadingId(Long.valueOf(loadingId), PageRequest.of(pager.getPage() - 1, pager.getRows()));
        map.put("repairManages", servicePage.getContent());
        map.put("pager", pager);
        return map;
    }

    public RepairManage repairManage(Long id,Long loadingId) throws Exception {
        String BeOverdue = this.findStatus(loadingId);
        if(id <= 0){
            RepairManage re = new RepairManage();
            re.setBeOverdue(BeOverdue);
            return re;
        }
        return repository.getOne(id);
    }

    /**
     * 创建维修信息
     * @param repairManage
     * @throws Exception
     */
    public void create(RepairManage repairManage) throws Exception {
        Assembling assembling = assemblingRepository.getOne(repairManage.getLoadingId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       /* Date loadingDate = dateFormat.parse(assembling.getLoadingTime());
        if(loadingDate.compareTo(new Date()) > 0){
            repairManage.setBeOverdue("是");
        } else {
            repairManage.setBeOverdue("否");
        }*/
        assembling.setRepairTime(dateFormat.format(new Date()));
        repository.save(repairManage);
        assemblingRepository.save(assembling);
    }
    /**
     * 查询是否过保
     *
     */
    public String findStatus(Long id) throws Exception{
        Assembling assembling = assemblingRepository.getOne(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date loadingDate = dateFormat.parse(assembling.getLoadingTime());
        Long lo = System.currentTimeMillis();
        Long loading = loadingDate.getTime();
        Long ll = lo - loading;
        if(ll > Constants.YEAR_TIME){
            return "是";
        }
        return "否";
    }
    /**
     * 修改维修数据
     * @param repairManage
     * @throws Exception
     */
    public void modify(RepairManage repairManage){
        repository.save(repairManage);
        Assembling assembling = assemblingRepository.getOne(repairManage.getLoadingId());
        assembling.setRepairTime(df.format(new Date()));
        assemblingRepository.save(assembling);
    }
    /**
     *根据id批量删除
     * @param ids
     */
    @Transactional(rollbackOn = Throwable.class)
    public void delete(List<Long> ids) {
        repository.deleteByIdIn(ids);
    }

}
