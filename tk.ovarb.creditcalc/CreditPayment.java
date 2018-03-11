package tk.ovarb.creditcalc;

public class CreditPayment {
    public int payNum;
    public int payValue;

    @Override
    public String toString() {
        return "# " + payNum + ", value=" + payValue;
    }
}
