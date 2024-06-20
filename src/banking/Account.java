package banking;

import java.io.Serializable;
import java.util.Objects;

abstract class Account implements Serializable {
    String num;
    String name;
    int money;

    public Account(String num, String name, int money) {
        this.num = num;
        this.name = name;
        this.money = money;
    }

    public void showAccInfo() {
        System.out.println("계좌번호: " + num);
        System.out.println("이름: " + name);
        System.out.println("잔액: " + money);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(num, account.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }
    
    public void depositMoney(int deposit) {}
    
}