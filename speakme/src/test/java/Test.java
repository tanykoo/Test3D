import com.tanykoo.Lib;

/**
 * @Author ThinkPad
 * Created : 2019-05-22 19:53
 * @Since
 */
public class Test {

    public static void main(String[] args) {
        String usr = null;
        String pwd = null;
        String appid = "appid=5ce52f72";
        int a = Lib.instance.MSPLogin(usr,pwd,appid);
        System.out.println(a);

    }
}
