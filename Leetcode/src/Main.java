import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        volatile int ans = 0;
        volatile int index = 0;
        List<String> list = new ArrayList<>();

        while (in.hasNextInt()) {
            list.add(in.next());
        }

        ThreadPc pa = new ThreadPrinter("A", c, a);
        ThreadPc pb = new ThreadPrinter("B", a, b);
        ThreadPc pc = new ThreadPrinter("C", b, c);


        new Thread(pa).start();
        Thread.sleep(10);
        new Thread(pb).start();
        Thread.sleep(10);
        new Thread(pc).start();
        Thread.sleep(10);


    }

    class ThreadPc implements Runnable{


        @Override
        public void run() {
            int tmp = index;

        }


    }

}