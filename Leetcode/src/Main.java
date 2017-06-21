import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan.zcl on 2017/6/8.
 */
public class Main {
    public static void main(String[] args) {

        String str = "www.test.com/aaa/bbb/ccc/a1.b2.c3.d4";
        System.out.println(str.substring(0, str.indexOf('t')));
        String ans = str.substring(str.lastIndexOf('/') + 1);
        System.out.println(ans);
        String[] tmp = ans.split("\\.");
        System.out.println(tmp.length);


    }
}
