package banking;

class HighCreditAccount extends Account {
    int interest;
    String rank;

    public HighCreditAccount(String num, String name, int money, int interest, String rank) {
        super(num, name, money);
        this.interest = interest;
        this.rank = rank;
     
    }

    public void showHaccInfo() {
        System.out.println("계좌번호: " + num);
        System.out.println("이름: " + name);
        System.out.println("잔액: " + money);
        System.out.println("기본이자: " + interest);
        System.out.println("신용등급: " + rank);
    }
    
    @Override
    public void depositMoney(int deposit) {
        int nomalInterest = (int)(this.money * (interest / 100.0));
        int highInterest = 0;
        switch (rank) {
            case "A":
                highInterest = (int)(this.money * 0.07);
                break;
            case "B":
                highInterest = (int)(this.money * 0.04);
                break;
            case "C":
                highInterest = (int)(this.money * 0.02);
                break;
        }
        this.money += nomalInterest + highInterest + deposit;
    }
}
