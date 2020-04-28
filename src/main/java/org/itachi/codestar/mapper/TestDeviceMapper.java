package org.itachi.codestar.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.codestar.domain.TestDevice;

import java.util.List;
import java.util.Map;


@Mapper
public interface TestDeviceMapper {

    Integer findLoadingNumber(String da);

    List<TestDevice> findTestDevice(Map<String, Object> parameters);
}
