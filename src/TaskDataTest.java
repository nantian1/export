import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TaskDataTest
 * @Description: 任务测试数据类
 * @author: Chengkaifeng
 * @date: 2022/8/8
 */
public class TaskDataTest {


    public static void main(String[] args) {
        /*
        JSONObject resultJson = new JSONObject();
        //1.获取下发任务数据值,获取总体任务值
        JSONObject jsonObject = branchTask();
        BigDecimal bgTotal = getBigDecimal(jsonObject.get("total"));
        BigDecimal cycle = new BigDecimal(jsonObject.get("cycle").toString());

        //2.获取需要划分的机构个数
        List<Organization> orgList = createOrgData();
        int orgSize = orgList.size();

        //3.计算划分一个机构的金额数，向下取整
        BigDecimal avgAmount = BigDecimal.valueOf(Math.floor(bgTotal.doubleValue() / orgSize));

        //按照选择监测周期划分出第一个周期金额和最后一个周期金额
        BigDecimal firstQuaValue = avgAmount.divide(cycle).setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal lastQuaValue = avgAmount.subtract(firstQuaValue.multiply(cycle.subtract(new BigDecimal(1)))).setScale(0);
        Map<String, BigDecimal> sumQuarterMap = new HashMap<>();
        JSONObject orgJson = null;
        //4.处理除最后一行机构的记录的数据填充
        for (int i = 0; i < orgSize - 1; i++) {
            orgJson = new JSONObject();
            for (int j = 1; j < cycle.intValue() + 1; j++) {
                if (j < cycle.intValue()) {
                    orgJson.put("Q" + j, firstQuaValue);
                    if (null != sumQuarterMap.get("Q" + j)) {
                        BigDecimal bg = sumQuarterMap.get("Q" + j);
                        sumQuarterMap.put("Q" + j, bg.add(firstQuaValue));
                    } else {
                        sumQuarterMap.put("Q" + j, firstQuaValue);
                    }
                } else {
                    orgJson.put("Q" + j, lastQuaValue);
                    if (null != sumQuarterMap.get("Q" + j)) {
                        BigDecimal bg = sumQuarterMap.get("Q" + j);
                        sumQuarterMap.put("Q" + j, bg.add(lastQuaValue));
                    } else {
                        sumQuarterMap.put("Q" + j, lastQuaValue);
                    }
                }

            }
            resultJson.put(orgList.get(i).getName(), orgJson);
        }
        //5.填充最后一个机构的记录信息
        Organization lastOrg = orgList.get(orgSize-1);
        orgJson = new JSONObject();
        for (int k = 1; k < cycle.intValue() + 1; k++) {
            if(null != sumQuarterMap.get("Q" + k)){
                BigDecimal sendQValue = getBigDecimal(jsonObject.get("Q" + k));       //下发Q值
                BigDecimal sumQuarterValue = sumQuarterMap.get("Q" + k);  //累计q值
                orgJson.put("Q" + k,sendQValue.subtract(sumQuarterValue).setScale(0));
            }
        }
        resultJson.put(lastOrg.getName(),orgJson);
        //6.打印结果
        System.out.println(resultJson);
*/

        JSONObject resultJson = new JSONObject();
        JSONObject orgJson = new JSONObject();
        JSONObject quarterJson;
        //1.获取下发任务数据值,获取总体任务值
        JSONObject jsonObject = branchTask();
        BigDecimal cycle = new BigDecimal(jsonObject.get("cycle").toString());

        //2.获取需要划分的机构个数
        List<Organization> orgList = createOrgData();

        //3.任务切分等额，最后不足的最后一行进行补全
        for (int i = 1; i < cycle.intValue() + 1; i++) {
            //获取周期阶段下发任务值
            BigDecimal quarterTotal = getBigDecimal(jsonObject.get("Q" + i));
            BigDecimal firstOrgValue = quarterTotal.divide(new BigDecimal(orgList.size()), 0, BigDecimal.ROUND_DOWN);
            BigDecimal calValue = BigDecimal.ZERO;  //累计计算值
            for (int j = 0; j < orgList.size(); j++) {
                quarterJson = new JSONObject();
                //是否第一次放入数据，若是第一次放入，json放入{机构名称，季度串}，否则根据机构名称取出quarterJson,添加新元素
                if (null == orgJson.get(orgList.get(j).getName())) {
                    //若最后第一次，则放入平均向下取正的数值，否则放入【该季度总值-前几项的值】
                    if (j != orgList.size()-1) {
                        quarterJson.put("Q" + i, firstOrgValue);
                        orgJson.put(orgList.get(j).getName(), quarterJson);
                        calValue = calValue.add(firstOrgValue);
                    } else {
                        BigDecimal lastOrgValue = quarterTotal.subtract(calValue);
                        quarterJson.put("Q" + i, lastOrgValue);
                        orgJson.put(orgList.get(j).getName(), quarterJson);
                        calValue = BigDecimal.ZERO;

                    }
                } else {
                    if (j != orgList.size()-1) {
                        quarterJson = (JSONObject) orgJson.get(orgList.get(j).getName());
                        quarterJson.put("Q" + i, firstOrgValue);
                        calValue = calValue.add(firstOrgValue);
                    }else{
                        BigDecimal lastOrgValue = quarterTotal.subtract(calValue);
                        quarterJson = (JSONObject) orgJson.get(orgList.get(j).getName());
                        quarterJson.put("Q" + i,lastOrgValue);
                        calValue = BigDecimal.ZERO;
                    }
                }
            }

        }
        System.out.println(orgJson);

    }


    /**
     * 设置总行下发北京总任务值1千万数据
     *
     * @return
     */
    public static JSONObject branchTask() {
        JSONObject bjData = new JSONObject();
        bjData.put("total", 2000000);
        bjData.put("Q1", 2000000);
//        bjData.put("Q2", 700000);
//        bjData.put("Q3", 500000);
//        bjData.put("Q4", 300000);
        bjData.put("cycle", "1");
        System.out.println("aaa");
        return bjData;
    }


    /**
     * 创建支行数据
     *
     * @return
     */
    public static List<Organization> createOrgData() {
        List<Organization> orgList = new ArrayList<>();
        orgList.add(new Organization(1, "昌平支行"));
        orgList.add(new Organization(2, "天通苑支行"));
        orgList.add(new Organization(3, "燕京支行"));
        /** orgList.add(new Organization(4, "陶然支行"));
         orgList.add(new Organization(5, "西单支行"));
         orgList.add(new Organization(6, "长安街支行"));
         orgList.add(new Organization(7, "马连道支行"));
         orgList.add(new Organization(8, "陶然亭路支行"));
         orgList.add(new Organization(9, "报国寺支行"));
         orgList.add(new Organization(10, "永定门支行"));
         orgList.add(new Organization(11, "华安支行"));*/
        return orgList;
    }


    /**
     * 假设实现获取数据
     *
     * @param value
     * @return
     */


    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }


}
