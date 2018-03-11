package tk.ovarb.creditcalc;

//all data and methods about loan
public class CreditRecord {
    //all values are in roubles
    public static final double RATE = 0.12/12;
    public int creditSum;
    public int creditInstallment;
    public int creditLength;
    public int payCount;
    public int creditResidue;
    public MonthInstallment[] schedule;
    public CreditPayment[] history;

    //constructor
    public CreditRecord(int sum, int installment){
        this.creditSum = this.creditResidue = sum;
        this.creditLength = proposalLength(sum, installment, RATE);
        this.creditInstallment = calculateInstallment(sum, creditLength, RATE);
        payCount = 0;

        //create initial schedule
        schedule = new MonthInstallment[creditLength];
        //initialize every month
        for (int i = 0; i < this.creditLength; i++) {

            schedule[i] = new MonthInstallment();

            schedule[i].monthNum = i;

            if (0 == i) {
                schedule[i].bomDebt = this.creditSum;
            } else {
                schedule[i].bomDebt = schedule[i - 1].eomDebt;
            }

            schedule[i].monthlyIntrst = monthIntAmount(schedule[i].bomDebt, RATE);
            schedule[i].totalPaymentAmount = this.creditInstallment;
            schedule[i].debtDecremnt = schedule[i].totalPaymentAmount - schedule[i].monthlyIntrst;
            schedule[i].eomDebt = schedule[i].bomDebt - schedule[i].debtDecremnt;
        }

        //initialize payment history
        history = new CreditPayment[creditLength];
        for (int i = 0; i < this.creditLength; i++) {
            history[i] = new CreditPayment();
            history[i].payNum = i;
            history[i].payValue = 0;
        }
    }

    @Override
    public String toString() {
        return "Sum=" + creditSum + ", Inst = " + creditInstallment + ", Length = " + creditLength;
    }

    //print schedule from desired start point
    public void printSchedule(int startMonth) {
        System.out.println("Credit schedule:");
        if (this.payCount == this.creditLength) {
            System.out.println("The loan is successfully paid.");
        }
        for (int i = startMonth; i < this.schedule.length; i++) {
            System.out.println(schedule[i]);
        }
    }

    //print whole payment history
    public void printHistory() {
        System.out.println("Payment history:");
        for (int i = 0; i < this.schedule.length; i++) {
            System.out.println(history[i]);
        }
    }

    public void printCreditInfo(int month) {
        printSchedule(month);
        printHistory();
    }

    public boolean validPayment(int sum, int month) {
        if (sum >= this.schedule[month].totalPaymentAmount) {
            return true;
        }
        return false;
    }


    public void makePayment(int sum) {
        this.history[payCount].payValue = sum;
        if (sum > this.creditInstallment) {
            this.makeExtraPayment(this.payCount, sum);
        }
        this.creditResidue = this.schedule[payCount].eomDebt;
        this.payCount++;
        this.printCreditInfo(this.payCount);
    }

    public int getCurrentCreditInstallment(int month) {
        return this.schedule[month].totalPaymentAmount;
    }

    public void makeExtraPayment(int month, int sum) {

        //recalculate current month data
        this.schedule[month].totalPaymentAmount = this.history[month].payValue;
        this.schedule[month].debtDecremnt = this.schedule[month].totalPaymentAmount - (int)this.schedule[month].monthlyIntrst;
        this.schedule[month].eomDebt = this.schedule[month].bomDebt - this.schedule[month].debtDecremnt;

        //recalculate next payments from the month of extra payment
        int nextMonth = month + 1;
        int newCreditSum = this.schedule[month].eomDebt;
        int newCreditLength = this.creditLength - nextMonth;
        this.creditInstallment = calculateInstallment(newCreditSum, newCreditLength, RATE);

        //recalculate next schedule data from the month of extra payment
        for (int j = nextMonth; j < this.creditLength; j++) {

            this.schedule[j].bomDebt = this.schedule[j - 1].eomDebt;
            this.schedule[j].monthlyIntrst = monthIntAmount(this.schedule[j].bomDebt, RATE);
            this.schedule[j].totalPaymentAmount =  this.creditInstallment;
            this.schedule[j].debtDecremnt = this.schedule[j].totalPaymentAmount - (int)this.schedule[j].monthlyIntrst;
            this.schedule[j].eomDebt = this.schedule[j].bomDebt - this.schedule[j].debtDecremnt;
        }
    }

    //calculate comfortable loan length based on payment entered by user
    public int proposalLength(int sum, int maxPayment, double rateMonth){
        double a1 = ((double)maxPayment/(double)sum - rateMonth);
        double a = 1 + 1/(a1/rateMonth);
        double b = 1 + rateMonth;
        int proposedLength = (int)Math.ceil(Math.log(a)/Math.log(b));
        return proposedLength;
    }

    //calculate accurate periodical installment
    public int calculateInstallment(int sum, int creditLength, double rateMonth) {
        double payment;
        double temp = Math.pow((1 + rateMonth), (double)creditLength);
        payment = sum * (rateMonth + rateMonth/(temp - 1));
        return (int)Math.ceil(payment);
    }

    //calculate month interest amount
    public int monthIntAmount(int sum, double rateMonth) {
        return (int)Math.ceil(sum * rateMonth);
    }
}
