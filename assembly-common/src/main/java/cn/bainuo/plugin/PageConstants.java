package cn.bainuo.plugin;

/**
 * Created by wyp on 2017/11/17.
 * 分页使用参数
 */
public interface PageConstants {
//    public static final String SHOW_COUNT_KEY = "showCount" ; // 每页显示记录数的key
//    public static final  String CURRENT_PAGE_KEY = "currentPage";//当前页
    public static final String SHOW_COUNT_KEY = "rows" ; // 每页显示记录数的key
    public static final  String CURRENT_PAGE_KEY = "page";//当前页
    public static final  String TOTAL_PAGE_KEY = "totalPage";//总页数
    public static final String TOTAL_RESULT_KEY = "totalResult";//总记录数
    public static final  String  CURRENT_RESULT_KEY = "currentResult"; // 当前记录起始索引

    //初始化当前页
    public static final Integer INIT_CURRENT_PAGE = 1;
    //初始化每页显示的个数
    public static final Integer INIT_SHOW_COUNT=20;

    /**
     * 通过总数量和显示个数计算总页数
     * @param totalResult
     * @param showCount
     * @return
     */
    public static int getTotalPage(Integer totalResult, Integer showCount) {
        int totalPage = 0;
        if (totalResult % showCount == 0)
            totalPage = totalResult / showCount;
        else
            totalPage = totalResult / showCount + 1;
        return totalPage;
    }

    /**
     * 获取当前结果
     * @param currentPage
     * @param showCount
     * @return
     */
    public static int getCurrentResult(Integer currentPage, Integer showCount) {

        int currentResult = (currentPage - 1) * showCount;
        if (currentResult < 0)
            currentResult = 0;
        return currentResult;
    }

}
