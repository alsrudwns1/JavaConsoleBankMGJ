package banking;

class NormalAccount extends Account {
    int interest;

    public NormalAccount(String num, String name, int money, int interest) {
        super(num, name, money);
        this.interest = interest;
    }

    public void showNaccInfo() {
        System.out.println("계좌번호: " + num);
        System.out.println("이름: " + name);
        System.out.println("잔액: " + money);
        System.out.println("기본이자: " + interest);
    }
    
    @Override
    public void depositMoney(int deposit) {
        int nomalInterest = (int)(this.money * (interest / 100.0));
        this.money += nomalInterest + deposit;
    }
    
}
