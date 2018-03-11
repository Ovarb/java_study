package tk.ovarb.creditcalc;

import java.util.Scanner;

public class CCalcRunner {
    public static void main(String[] args) {
        //System.out.println("DEBUG runner start");
        Scanner sc = new Scanner(System.in);

        //enter sum and  installment
        System.out.println("Enter Sum");
        int inputSum = sc.nextInt();

        System.out.println("Enter comfortable payment per month");
        int inputInstallment = sc.nextInt();

        //create new credit record
        CreditRecord c1 = new CreditRecord(inputSum, inputInstallment);

        System.out.println(c1);
        c1.printCreditInfo(0);

        //iteration over schedule
        for (int i = 0; i < c1.creditLength; i++) {
            int currentPayment = 0;
            do {
                System.out.printf("Enter payment %d (equal or more than %d):\n", i, c1.getCurrentCreditInstallment(i));
                currentPayment = sc.nextInt();
            } while (!c1.validPayment(currentPayment, i));
            c1.makePayment(currentPayment);
        }
    }
}

