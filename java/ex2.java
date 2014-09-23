/**
 * Android组第二次练习 in Java
 */
public class ex2 {
    public static void main(String[] argv) {
//      1. 水仙花数
        int[] nn = findNarcissu();
//      2. [1, 1000]质数
        int[] primes = findPrimes(1000);
//      3. 公约公倍
        System.out.println(gcd(13, 169));
        System.out.println(lcm(13, 169));
//      4. 直角三角
        printRtTri();
//      5. 斜三角
        printTri();
//      6. 字母三角
        printASCIITri();
//      7. 日历
        for (int i = 1; i <= 12; i++) {
            printCalendar(i);
        }
//      8. 9x9
        print99();
    }

    public static int[] findNarcissu() {
        int[] buffer = new int[1000];
        int counter = 0;
        for (int i = 100; i < 1000; i++) {
            if (isNarcissu(i))
                buffer[counter++] = i;
        }
        int[] ans = new int[counter];
        System.arraycopy(buffer, 0, ans, 0, counter);
        return ans;
    }

    static boolean isNarcissu(int value) {
        return cubed(value % 10)
                + cubed(value / 10 % 10)
                + cubed(value / 100) == value;
    }

    static int cubed(int base) {
        return base * base * base;
    }

    public static int[] findPrimes(int max) {
        byte sieve[] = new byte[max + 1];
        for (int i = 2; i <= max; i++) {
            sieve[i] = 1;
        }
        for (int i = 2; i <= max; i++) {
            if (sieve[i] == 1) {

                for (int j = i + i; j <= max; j += i) {
                    sieve[j] = 0;
                }
            }
        }
        int count = 0;
        for (int i = 2; i <= max; i++) {
            count += sieve[i] == 1 ? 1 : 0;
        }
        int ans[] = new int[count];
        int pos = 0;
        for (int i = 2; i <= max; i++) {
            if (sieve[i] == 2)
                ans[pos++] = i;
        }
        return ans;
    }

    public static int gcd(int a, int b) {
        // 困了...没写异常情况的处理
        if (a == b)
            return a;
        if (a < b)
            return gcd(b, a);
        // 如果a, b都是偶数
        if ((a | b & 1) == 0)
            return gcd(a / 2, b / 2);
        return gcd(a - b, b);
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    static void printRtTri() {
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    static void printTri() {
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 5 - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j < i + i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    static void printASCIITri() {
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 26 - i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i; j++) {
                System.out.print((char) ('a' + j));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    static void print99() {
        for (int y = 0; y < 10; y++) {
            System.out.print(y > 0 ? y : "-");
            System.out.print(" ");
            for (int x = 1; x < 10; x++) {
                if (y <= x) {
                    System.out.print(String.format("%2d", x * (y > 0 ? y : 1)));
                } else {
                    System.out.print("  ");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void printCalendar(int mon) {
//        既然你们秦神不让打表
//        int[] pos = new int[]{3, 6, 6, 2, 4, 7, 2, 5, 1, 3, 6, 1};
//        那就换个方法作弊吧:P
        int[] days = new int[]{0,
                31, 28, 31, 30, 31, 30,
                31, 31, 30, 31, 30, 31};
        int pos = 3;
        for (int i = 1; i < mon; i++)
            pos += days[i];
        pos %= 7;
        System.out.println(String.format("       %2d月", mon));
        System.out.println("日 一 二 三 四 五 六");
        for (int i = 0; i < days[mon] + pos; i++) {
            if (i < pos)
                System.out.print("   ");
            else {
                System.out.print(String.format("%2d ", i + 1 - pos));
            }
            if (i >= 6 && (1 + i) % 7 == 0)
                System.out.println();
        }
        System.out.println();
    }
}
