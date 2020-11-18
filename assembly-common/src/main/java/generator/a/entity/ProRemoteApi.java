package generator.a.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyc
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProRemoteApi implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * post/get
     */
    private String requestType;

    /**
     * 参数
     */
    private String param;

    /**
     * appkey
     */
    private String appkey;

    /**
     * appsecret
     */
    private String appsecret;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 企业id
     */
    private Integer groupId;

    /**
     * 状态10000删除10001正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Integer updator;


}
