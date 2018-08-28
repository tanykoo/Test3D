package test;

public class F_Observer extends Observer {
    public void update(String msg) {
        System.out.println(F_Observer.class.getName() + " : " + msg);
    }
}