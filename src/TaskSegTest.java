import net.sf.json.JSONObject;

/**
 * @ClassName: TaskSegTest
 * @Description:
 * @author: Chengkaifeng
 * @date: 2022/8/5
 */
public class TaskSegTest {

    public static void main(String[] args) {

        JSONObject jsonR = new JSONObject();
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        json1.put("name","zhangsan");
        json2.put("name","lisi");
        jsonR.put("json1",json1);
        jsonR.put("json2",json2);
        //System.out.println(jsonR);
        if(null != jsonR.get("json1")){
           JSONObject json = (JSONObject) jsonR.get("json1");
           json.put("age",12);
           json.put("id","1367819272");
        }
        System.out.println(jsonR);






    }


}
