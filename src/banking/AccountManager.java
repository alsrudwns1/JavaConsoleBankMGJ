package banking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

class AccountManager {
	HashSet<Account> myAccount = new HashSet<Account>();
	int numOfAccount;
	private AutoSaver autoSaver;

    public AccountManager(int num) {
        numOfAccount = 0;
        loadAccounts();
    }

    public void makeAccount() {
        Scanner scan = new Scanner(System.in);
        System.out.println("[계좌 종류 선택]");
        System.out.println("1: 보통계좌, 2: 신용신뢰계좌");
        int choice = scan.nextInt();

        if (choice == 1) {
            makeNormalAccount();
        } else if (choice == 2) {
            makeHighCreditAccount();
        } else {
            System.out.println("1 또는 2를 입력하세요");
        }
    }

    public void makeNormalAccount() {
        Scanner scan = new Scanner(System.in);
        String num, name;
        int money, interest;

        System.out.print("계좌번호: ");
        num = scan.nextLine();
        System.out.print("이름: ");
        name = scan.nextLine();
        System.out.print("잔고: ");
        money = scan.nextInt();
        System.out.print("기본이자%(정수): ");
        interest = scan.nextInt();

        NormalAccount nAccount = new NormalAccount(num, name, money, interest);
        if (!myAccount.add(nAccount)) {
            System.out.println("중복된 계좌번호가 있습니다.");
            System.out.print("덮어쓸까요? (Y or N): ");
            Scanner sc = new Scanner(System.in);
            String answer = sc.next();
            if (answer.equalsIgnoreCase("Y")) {
                myAccount.remove(nAccount); 
                myAccount.add(nAccount);
                System.out.println("덮어쓰기 완료되었습니다.");
            } else {
                System.out.println("기존 정보를 유지합니다.");
            }
        } else {
            System.out.println("계좌가 생성되었습니다.");
        }
    }

    public void makeHighCreditAccount() {
        Scanner scan = new Scanner(System.in);
        String num, name, rank;
        int money, interest;

        System.out.print("계좌번호: ");
        num = scan.nextLine();
        System.out.print("이름: ");
        name = scan.nextLine();
        System.out.print("잔고: ");
        money = scan.nextInt();
        System.out.print("기본이자%(정수): ");
        interest = scan.nextInt();
        System.out.print("신용등급: ");
        rank = scan.next();

        HighCreditAccount hAccount = new HighCreditAccount(num, name, money, interest, rank);
        myAccount.add(hAccount);
    }

    public void depositMoney() {
        Scanner scan = new Scanner(System.in);
        System.out.println("입금할 계좌번호를 입력하세요: ");
        String searchNum = scan.next();
        boolean isFound = false;

        for (Account account : myAccount) {
            if (account.num.equals(searchNum)) {
            	isFound = true;
                System.out.println("입금할 금액을 입력하세요: ");
                
                try {
                    int money = scan.nextInt();
                    if (money < 0) {
                        throw new MenuSelectException("음수는 입금할 수 없습니다.");
                    } else if(money % 500 != 0) {
                    	throw new MenuSelectException("입금은 500원 단위로만 가능합니다.");
                    }
                    account.depositMoney(money);
                    System.out.println("입금이 완료되었습니다.");
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해 주세요.");
                    scan.next();
                } catch (MenuSelectException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
        if (isFound == false) {
            System.out.println("없는 계좌번호입니다.");
        }
    }

    public void withdrawMoney() {
        Scanner scan = new Scanner(System.in);
        System.out.println("출금할 계좌번호를 입력하세요: ");
        String searchNum = scan.next();
        boolean isFound = false;

        for (Account account : myAccount) {
            if (account.num.equals(searchNum)) {
                isFound = true;
                System.out.println("출금할 금액을 입력하세요: ");
                
                try {
                    int money = scan.nextInt();
                    if (money < 0) {
                        throw new MenuSelectException("음수는 출금할 수 없습니다.");
                    } else if(money % 1000 != 0) {
                    	throw new MenuSelectException("출금은 1000원 단위로만 가능합니다.");
                    } else if (account.money >= money) {
                        account.money -= money;
                        System.out.println("출금이 완료되었습니다.");
                    } else {
                        System.out.println("잔고가 부족합니다."
                        		+ "금액 전체를 출금할까요? (Y or N)");
                        String answer = scan.next();
                        switch(answer) {
                        case "Y", "y" :
                        	money = account.money;
                            account.money -= money;
                            System.out.println("잔액 전체가 출금되었습니다.");
                        	break;
                        case "N", "n" :
                        	System.out.println("출금이 취소되었습니다.");
                        	break;
                        default :
                        	System.out.println("Y 또는 N을 입력해주세요");
                        	break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자를 입력해주세요.");
                    scan.next();
                } catch (MenuSelectException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
        if (isFound == false) {
            System.out.println("없는 계좌번호입니다.");
        }
    }
    
    public void delete() {
        Scanner scan = new Scanner(System.in);
        System.out.println("삭제할 계좌번호를 입력하세요: ");
        String search = scan.next();
        boolean delNum = false;

        for (Account account : myAccount) {
            if (account.num.equals(search)) {
                myAccount.remove(account);
                System.out.println("계좌가 삭제되었습니다.");
                delNum = true;
                break;
            }
        }

        if (!delNum) {
            System.out.println("해당 계좌번호를 찾을 수 없습니다.");
        }
    	
    }

    public void showAccInfo() {
        for (Account account : myAccount) {
            if (account instanceof NormalAccount) {
            	System.out.println("---계좌정보(일반계좌)---");
                ((NormalAccount) account).showNaccInfo();
                System.out.println("------------------------");
            } else if (account instanceof HighCreditAccount) {
            	System.out.println("----계좌정보(신용계좌)----");
                ((HighCreditAccount) account).showHaccInfo();
                System.out.println("--------------------------");
            }
        }
        System.out.println("전체 계좌 정보가 출력되었습니다.");
    }
    
	private void loadAccounts() {
    	try (ObjectInputStream load = new ObjectInputStream(
    			new FileInputStream("AccountInfo.obj"))) {
    		myAccount = (HashSet<Account>) load.readObject();
    		System.out.println("계좌정보를 불러왔습니다");
    	} catch (FileNotFoundException e) {
    		System.out.println("저장된 계좌 정보가 없습니다.");
    	} catch (IOException | ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    public void saveAccounts() {
    	try (ObjectOutputStream save = new ObjectOutputStream(
    			new FileOutputStream("AccountInfo.obj"))) {
    		save.writeObject(myAccount);
    		System.out.println("계좌 정보가 저장되었습니다.");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void AutoSaver() {
        System.out.println("저장 옵션을 선택하세요:");
        System.out.println("1. 자동저장 On");
        System.out.println("2. 자동저장 Off");

        Scanner scan = new Scanner(System.in);
        int saveOption = scan.nextInt();
        scan.nextLine();

        if (saveOption == 1) {
            if (autoSaver != null && autoSaver.isAlive()) {
                System.out.println("이미 자동저장이 실행중입니다.");
            } else {
                autoSaver = new AutoSaver(this);
                autoSaver.setDaemon(true);
                autoSaver.start();
                System.out.println("자동저장을 시작합니다.");
            }
        } else if (saveOption == 2) {
            if (autoSaver != null && autoSaver.isAlive()) {
                autoSaver.interrupt();
                System.out.println("자동저장을 중지합니다.");
            } else {
                System.out.println("자동저장이 실행중이지 않습니다.");
            }
        }
    }
    
}