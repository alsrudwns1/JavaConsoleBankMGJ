package banking;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Handler;

import javax.swing.MenuSelectionManager;

public class BankingSystemMain implements ICustomDefine {

    public static void menuShow() {
        System.out.println("==== 계좌관리 프로그램 ====");
        System.out.println("1. 계좌 개설");
        System.out.println("2. 입금");
        System.out.println("3. 출금");
        System.out.println("4. 전체 계좌정보 출력");
        System.out.println("5. 계좌정보 삭제");
        System.out.println("6. 저장 옵션");
        System.out.println("7. 프로그램 종료");
        System.out.println("메뉴 선택");
    }

    public static void main(String[] args) throws MenuSelectException {
    	AccountManager handler = new AccountManager(50);
    	Scanner scan = new Scanner(System.in);

    	while (true) {
    		menuShow();

    		try {
    			int choice = scan.nextInt();
    			if (choice < 1 || choice > 7) {
    				throw new MenuSelectException("지정된 숫자를 입력해주세요");
    			}
    			switch (choice) {
    			case MAKE:
    				handler.makeAccount();
    				break;
    			case DEPOSIT:
    				handler.depositMoney();
    				break;
    			case WITHDRAW:
    				handler.withdrawMoney();
    				break;
    			case INQUIRE:
    				handler.showAccInfo();
    				break;
    			case DEL:
    				handler.delete();
    				break;
    			case AUTOSAVE:
    				handler.AutoSaver();
    				break;
    			case EXIT:
    				handler.saveAccounts();
    				System.out.println("프로그램을 종료합니다.");
    				return;
    			}
    		} catch(InputMismatchException e) {
    			System.out.println("숫자를 입력해 주세요");
    			scan.next();
    		} catch (MenuSelectException e) {
    			System.out.println(e.getMessage());
    		}
    	}
    }
}


