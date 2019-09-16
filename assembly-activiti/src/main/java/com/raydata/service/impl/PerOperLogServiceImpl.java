package com.raydata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.raydata.core.ResultVo;
import com.raydata.dao.PerOperLogMapper;
import com.raydata.model.PerOperLog;
import com.raydata.service.PerOperLogService;
import com.raydata.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerOperLogServiceImpl implements PerOperLogService {
    @Autowired
    private PerOperLogMapper perOperLogMapper;

    @Override
    public Object selectPerOperLogsPage(String param, IPage page) {
        PerOperLog perOperLog = JacksonUtil.toObj(param, PerOperLog.class);
        page = perOperLogMapper.selectPage(page,new QueryWrapper<PerOperLog>()
                                                .eq(false,"oper",perOperLog.getOper())
                                                .eq(false,"beoper",perOperLog.getBeoper())
                                                .between(false,"opertime",perOperLog.getBegin(),perOperLog.getEnd())
                                           );
        return ResultVo.success(page);
    }
}
