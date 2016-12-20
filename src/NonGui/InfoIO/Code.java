package NonGui.InfoIO;

/**
 * Created by Administrator on 12/09/2016.
 */
public class Code {
    public static String encode(String str) {
        str = str.replaceAll("%", "%9");
        str = str.replaceAll("&", "%8");
        return str;
    }

    public static String decode(String str) {
        str = str.replaceAll("%8", "&");
        str = str.replaceAll("%9", "%");
        return str;
    }
}