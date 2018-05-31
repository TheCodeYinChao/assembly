package cn.bainuo.plugin;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sq on 2017/11/20.
 * 分页统计使用参数
 */
public class PageTotalConstants {
    public static Map<String,String> SHOW_TOTAL_ID  = new HashMap<String,String>(); // 页面统计集合
    static {
        SHOW_TOTAL_ID.put("getListOrderTradingPage","SUM(now_price) as now_price_sum,SUM(price) as price_sum,SUM(actrue_price) as actrue_price_sum,");
        //获取总佣金
        SHOW_TOTAL_ID.put("getPage","cast(sum(rewardMoney) AS DECIMAL (19, 2))as rewardMoneyTotal,");
    }

    public static String getPagaTotalSql(String sqlId){
        if(sqlId.matches(".*getListOrderTradingPage")){
            return SHOW_TOTAL_ID.get("getListOrderTradingPage");
        }
        if(sqlId.matches(".*getPage")){
            return SHOW_TOTAL_ID.get("getPage");
        }
        return "";
    }

    public static void setPagaTotal(String sqlId,ParameterMap parameMap,ResultSet rs) throws Throwable {
        if (sqlId.matches(".*getListOrderTradingPage")) {
            parameMap.put("now_price_sum",rs.getInt("now_price_sum"));
            parameMap.put("price_sum",rs.getInt("price_sum"));
            parameMap.put("actrue_price_sum",rs.getInt("actrue_price_sum"));
        }
        if(sqlId.matches(".*getPage")){
            parameMap.put("rewardMoneyTotal",rs.getBigDecimal("rewardMoneyTotal"));
        }
    }
}
