package com.raydata.service.process.impl;// +----------------------------------------------------------------------

import com.raydata.common.Constant;
import com.raydata.dao.BusinessFilesMapper;
import com.raydata.dao.BusinessFormMapper;
import com.raydata.dao.BusinessNoticeMapper;
import com.raydata.model.BusinessFiles;
import com.raydata.model.BusinessForm;
import com.raydata.model.BusinessNotice;
import com.raydata.pojo.process.FormVo;
import com.raydata.service.process.FormService;
import com.raydata.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// | ProjectName: raydata-pm
// +----------------------------------------------------------------------
// | Date: 2019/9/12
// +----------------------------------------------------------------------
// | Time: 14:32
// +----------------------------------------------------------------------
// | Author: haiying.qin <haiying.qin@raykite.com>
// +----------------------------------------------------------------------
@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private BusinessFilesMapper businessFilesMapper;

    @Autowired
    private BusinessFormMapper businessFormMapper;

    @Autowired
    private BusinessNoticeMapper businessNoticeMapper;

    @Override
    public int addForm(FormVo formVo) {
        long time =  System.currentTimeMillis();
        String userId = formVo.getUserId();
        BusinessForm form = formVo.getForm();
        if(form == null){
            return 0;
        }
        String formId = UUIDUtil.getUUID();
        form.setFormId(formId);
        form.setCreateBy(userId);
        form.setCreateTime(time);
        form.setUpdateTime(time);
        int i = addForm(form);

        List<BusinessFiles> files = formVo.getFiles();
        if(files != null && files.size() > 0){
            for(BusinessFiles file : files){
                file.setFormId(formId);
                file.setCreateBy(userId);
                file.setCreateTime(time);
                file.setUpdateTime(time);
                addFiles(file);
            }
        }

        //知会
        List<String> users = formVo.getUsers();
        if(users != null && users.size() > 0){

        }
        return i;
    }

    public int addForm(BusinessForm form){
        form.setStatus(Constant.ALLOW_STATUS);
        return businessFormMapper.insert(form);
    }

    public int addFiles(BusinessFiles files){
        files.setStatus(Constant.ALLOW_STATUS);
        return businessFilesMapper.insert(files);
    }

    public int addNotice(BusinessNotice notice){
        notice.setStatus(Constant.ALLOW_STATUS);
        return businessNoticeMapper.insert(notice);
    }
}
