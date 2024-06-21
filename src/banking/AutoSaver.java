package banking;

import java.io.FileWriter;
import java.io.IOException;

public class AutoSaver extends Thread {
    private AccountManager accountManager;
    private volatile boolean running = true;

    public AutoSaver(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void stopAutoSaver() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try (FileWriter writer = new FileWriter("AutoSaveAccount.txt")) {
                for (Account account : accountManager.myAccount) {
                    if (account instanceof NormalAccount) {
                        NormalAccount nAccount = (NormalAccount) account;
                        writer.write("=보통계좌=" + "\n" + "계좌번호: " + nAccount.num + ", 이름: " + nAccount.name +
                                ", 잔액: " + nAccount.money + ", 이자율: " + nAccount.interest + "\n");
                    } else if (account instanceof HighCreditAccount) {
                        HighCreditAccount hAccount = (HighCreditAccount) account;
                        writer.write("=신용계좌=" + "\n" +"계좌번호: " + hAccount.num + ", 이름: " + hAccount.name +
                                ", 잔액: " + hAccount.money + ", 이자율: " + hAccount.interest +
                                ", 신용: " + hAccount.rank + "\n");
                    }
                }
                writer.flush();
                Thread.sleep(3000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                if (!running) {
                    System.out.println("AutoSaver interrupted and stopping.");
                    break;
                }
            }
        }
    }
}
