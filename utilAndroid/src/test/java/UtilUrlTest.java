import com.cy.data.UtilUrl;

import org.junit.Test;

public class UtilUrlTest {
    @Test
    public void test(){
        String result = UtilUrl.removeParam("http://sfsf.com/dfsf?a=1&b=2","a");
        String result2 = UtilUrl.removeParam("a=1&b=2","a");

        System.out.println(result);
    }
}
