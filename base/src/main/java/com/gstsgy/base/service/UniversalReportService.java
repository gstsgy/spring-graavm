package com.gstsgy.base.service;

import java.util.List;
import java.util.Map;

public interface UniversalReportService {
    List<Map<String, Object>> execute(String fileName,String methodName, Map<String, Object> params);

    Map<String, Object> pageExecute(String fileName, String methodName, Map<String, Object> params);
}
