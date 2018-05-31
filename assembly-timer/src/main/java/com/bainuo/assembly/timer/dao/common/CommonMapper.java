package com.bainuo.assembly.timer.dao.common;

import com.bainuo.assembly.timer.vo.TimerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * User: zyc
 * Date: 2018-05-14
 * Time: 13:31
 * Version ：1.0
 * Description:
 */
@Repository
public interface CommonMapper {
    public TimerVo selectById(@Param("id") Integer id);
}
