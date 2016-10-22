/**
 * Created by my on 2016/10/21.
 */
import java.util.*;

/**
 * @author my
 *
 * 下压栈实现
 */
public class MyStack<Item> {
    private Item[] stack;
    private int size;

    public MyStack(){
        stack = (Item[]) new Object[1];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void changeSize(int newsize){
        Item[] temp = (Item[]) new Object[newsize];
        for (int i=0; i<stack.length; i++){
            temp[i] = stack[i];
        }
        stack = temp;
    }

    public void push(Item item) {
        if (size == stack.length)
            changeSize(2*stack.length);
        stack[size++] = item;
    }

    public Item pop(){
        Item item = stack[--size];
        stack[size] = null;
        if (size != 0 && size <= stack.length/4)
            changeSize(stack.length/4);
        return item;
    }

    public static void main(String[] args){
        MyStack<Integer> stack = new MyStack<Integer>();
        Scanner in = new Scanner(System.in);

        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        stack.push(in.nextInt());
        System.out.println(stack.size);
        System.out.println(stack.pop());
        System.out.println(stack.size);
    }
}
