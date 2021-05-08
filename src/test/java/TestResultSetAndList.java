import com.example.springbootdemo.utils.JdbcUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将查询结果用 List循环， ResultSet循环效率更高
 */
public class TestResultSetAndList {
    private static JdbcTemplate jdbcTemplate;
    public static void main(String[] args){
        jdbcTemplate = JdbcUtil.getFreeJdbcTemplate();
        System.out.println("开始开始开始开始开始开始开始开始开始开始开始开始开始开始开始开始开始开始");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        long testLong = 0;
        long test2Long = 0;
        int count = 3;
        List testList = new ArrayList();
        List test2List = new ArrayList();
        for (int i = 0; i < count; i++) {
            testLong = testLong + test(testList);
            test2Long = test2Long + test2(test2List);
            System.out.println("======================================================================================================");
        }
        System.out.println("test平均时间："+testLong/count+","+count+"次总时间"+testLong+",list个数"+testList);
        System.out.println("test2平均时间："+test2Long/count+","+count+"次总时间"+test2Long+",list个数"+test2List);
        System.out.println("结束结束结束结束结束结束结束结束结束结束结束结束结束结束结束结束结束结束");
    }
    public static long test(List testList) {
        final long begin = System.currentTimeMillis();
        final Map<String, List<ResultSet>> segmentDataMap = new HashMap<String, List<ResultSet>>();
        jdbcTemplate.query("SELECT * FROM DE_EN_BASE_INF", new ResultSetExtractor<Object>() {
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                int i = 0;
                while (rs.next()) {
                    String pks;
                    //所有子表的主键都是SUBLIST_KEY
                    pks = rs.getString("CUSTOMER_ID");
                    if (segmentDataMap.containsKey(pks)) {
                        segmentDataMap.get(pks).add(rs);
                    } else {
                        List<ResultSet> segmentList = new ArrayList<ResultSet>();
                        segmentList.add(rs);
                        segmentDataMap.put(pks, segmentList);
                    }
                    i++;
                }
                System.out.println("test()条数：" + i + "，segmentDataMap个数：" + segmentDataMap.size());
                return null;
            }
        });
        testList.add(segmentDataMap);
        long end = System.currentTimeMillis();
        System.out.println("test()用时：" + (end - begin) / 1000);
        segmentDataMap.clear();
        return (end - begin) / 1000;
    }

    public static long test2(List testList) {
        long begin = System.currentTimeMillis();
        Map<String, List<Map<String, Object>>> segmentDataMap = new HashMap<String, List<Map<String, Object>>>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM DE_EN_BASE_INF");
        int i = 0;
        for (Map<String, Object> map : list) {
            String pks;
            //所有子表的主键都是SUBLIST_KEY
            pks = ((String) map.get("CUSTOMER_ID"));
            if (segmentDataMap.containsKey(pks)) {
                segmentDataMap.get(pks).add(map);
            } else {
                List<Map<String, Object>> segmentList = new ArrayList<Map<String, Object>>();
                segmentList.add(map);
                segmentDataMap.put(pks, segmentList);
            }
            i++;
        }
        testList.add(segmentDataMap);
        System.out.println("test2()条数：" + i + "，segmentDataMap个数：" + segmentDataMap.size());
        long end = System.currentTimeMillis();
        System.out.println("test2()用时：" + (end - begin) / 1000);
        segmentDataMap.clear();
        return (end - begin) / 1000;
        //JSONObject jsonObject = new JSONObject(segmentDataMap);
        //System.out.println("test2()"+jsonObject.toString());
    }

}
