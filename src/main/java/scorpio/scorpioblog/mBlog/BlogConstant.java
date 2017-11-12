package scorpio.scorpioblog.mBlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlogConstant {

    private final static String p1 = "/images/1.jpg";
    private final static String p2 = "/images/2.jpg";
    private final static String p3 = "/images/3.jpg";
    private final static String p4 = "/images/4.jpg";
    private static List<String> list = new ArrayList<>();
    static {
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
    }

    public static String getPic(){
        int i = new Random().nextInt(4);
        return list.get(i);
    }
}
