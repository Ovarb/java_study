package tk.ovarb.creditcalc;

public class MonthInstallment {

    // #paym   %%sum decrement Paym SumEnd
    public int monthNum;
    public int bomDebt; //beginning of month debt amount
    public int monthlyIntrst;
    public int debtDecremnt;
    public int totalPaymentAmount;
    public int eomDebt; //end of month debt amount

    @Override
    public String toString() {
        return "# " + monthNum + ", BegOfMnth=" + bomDebt + ", %%= " + monthlyIntrst + ", debtPay=" + debtDecremnt + ", totalPay=" + totalPaymentAmount + ", EndOfMnth=" + eomDebt;
    }
}

