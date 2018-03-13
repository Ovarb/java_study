package tk.ovarb.sort;

public class SortRunner {

    int[] inputArray;
    int[] outputArray;

    public SortRunner (TestData td) {
        this.inputArray = td.dataArray;
    }

    public static void printArray(int[] ary) {
        for(int i: ary) {System.out.printf("%d ",i);}
        System.out.printf("\n");
    }

    public void printInput() {
        System.out.print ("Input array: ");
        printArray(this.inputArray);
    }

    public void printOutput() {
        System.out.print ("Output array: ");
        printArray(this.outputArray);
    }

    public static void main(String[] args) {

        TestData td = new TestData();
        SortRunner sr = new SortRunner(td);
        sr.printInput();
        sr.outputArray = SortAlgoritms.bubbleSort(sr.inputArray);
        sr.printOutput();
    }
}
