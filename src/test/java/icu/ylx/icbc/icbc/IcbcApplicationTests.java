package icu.ylx.icbc.icbc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IcbcApplicationTests {

    /**
     * question01:
     *      a. 89种
     *      b. 4C14 = 1001种
     * question02:
     *      a.
     *          1) D.E
     *          2) 12G  60%
     *      b.
     *          1) A
     *          2) C E
     */

    @Test
    public void question03() {
        int loopCount = 100000000;
        int piCount = 0;
        for (int i = 0; i < loopCount; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x*x + y*y < 1) {
                piCount++;
            }
        }
        System.out.println("圆周率估算值=" + 4.0 * piCount / loopCount);
    }
}

