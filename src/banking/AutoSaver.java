package banking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AutoSaver extends Thread {
	private AccountManager accountManager;
	private Account account;
    private boolean running = true;
    
    public AutoSaver(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public void run() {
        while (running) {
            try {
                saveAccountInfo();
                Thread.sleep(5000);
                System.out.println("자동저장중...");
            } catch (InterruptedException e) {
                System.out.println("자동저장이 중지되었습니다.");
                running = false; 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAccountInfo() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("AutoSaveAccount.txt", true));
        writer.write("자동저장 중입니다");
        writer.newLine();
        writer.close();
    }

    @Override
    public void interrupt() {
        running = false;
        super.interrupt();
    }
}
