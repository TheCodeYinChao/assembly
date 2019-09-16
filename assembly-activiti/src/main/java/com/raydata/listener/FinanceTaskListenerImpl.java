package com.raydata.listener;// +----------------------------------------------------------------------

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// | ProjectName: activiti
// +----------------------------------------------------------------------
// | Date: 2019/8/27
// +----------------------------------------------------------------------
// | Time: 15:53
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Slf4j
@Component
public class FinanceTaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //查询数据库，获取所有财务人员用户id
        List<String> financeUserList = new ArrayList<>();
        financeUserList.add("1");
        financeUserList.add("2");
        financeUserList.add("3");
        delegateTask.addCandidateUsers(financeUserList);
    }
}
