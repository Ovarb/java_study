package tk.ovarb.sort;

public class SortAlgoritms {

    public static int[] bubbleSort(int[] arr){

        System.out.println ("START SORTING");

        boolean isModified;
        int element;

        do {isModified = false;
            for (int i = 0; i < arr.length - 1; i++) {
                //debug   SortRunner.printArray(arr);
                element = arr[i];
                if (arr[i] > arr[i + 1]) {
                    arr[i] = arr[i + 1];
                    arr[i + 1] = element;
                    isModified = true;
                }
            }
        } while (isModified);

        System.out.println ("END SORTING");
        return arr;
    }
}
