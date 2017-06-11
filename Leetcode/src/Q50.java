/**
 * Created by ethan.zcl on 2017/6/9.
 */
public class Q50 {
    public double myPow(double x, int n) {
        if (n == 0) return (double)1;
        if (n < 0) {
            n = -n;
            x = 1/x;
        }
        int[] num = getNum(n);
        return help(x, num[0]) * myPow(x, num[1]);
    }

    public double help(double x, int n) {
        for (int i=1; i<n; i++) {
            x *= x;
        }
        return x;
    }

    public int[] getNum(int n) {
        int m = n;
        int num[] = {0, 0};
        int tmp = 1;
        while (n > 1) {
            n /= 2;
            num[0]++;
            tmp *= 2;
        }
        num[1] = m - tmp;
        return num;
    }
}
