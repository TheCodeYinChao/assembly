package com.raydata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PerOperLogService {
    Object selectPerOperLogsPage(String param, IPage page);
}
