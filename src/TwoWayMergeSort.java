import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TwoWayMergeSort {
    public static void main(String[] args) {

        try{
            long start = System.nanoTime();
            //Phase 1
            File inputFile = new File("src/inputFiles/input100k.txt");
            Scanner sc = new Scanner(inputFile);
            String[] firstLine = sc.nextLine().split(" ");

            int n = Integer.parseInt(firstLine[0]);
            int mm = Integer.parseInt(firstLine[1]);
            int bufferCapacity = mm*10000;
            int outputBufferCapacity = (int)Math.floor(bufferCapacity * 0.2);
            int inputBufferCapacity = (int)Math.floor(bufferCapacity * 0.8);

            int i = 0;
            int[] array = new int[bufferCapacity];

            int phID = 0;
            String PH = "PH";

            while(sc.hasNextInt()){
                if(i == bufferCapacity){
                    sort(array);
                    BufferedWriter out = new BufferedWriter(new FileWriter("src/tempFiles/"+PH+phID+".txt"));
                    phID++;
                    out.write(toString(array));
                    out.flush();
                    n-=bufferCapacity;
                    if(n>bufferCapacity)
                        array = new int[bufferCapacity];
                    else{
                        array = new int[n];
                        System.out.println(n);
                    }
                    i=0;
                    out.close();
                }
                array[i] = sc.nextInt();
                i++;
            }
            System.out.println(i);
            sort(array);
            BufferedWriter out = new BufferedWriter(new FileWriter("src/tempFiles/"+PH+phID+".txt"));
            out.write(toString(array));
            out.flush();
            out.close();
            sc.close();

            phID++;

            //Phase 2
            System.gc();

            BufferedWriter outputFile = new BufferedWriter(new FileWriter("src/output.txt",true));

            //System.out.println(usableMemory);


            int[] output = new int[outputBufferCapacity];
            int outputIndex = 0;
            int maxInputSize = inputBufferCapacity/phID;

            ArrayList<Integer>[] inputBuffers = new ArrayList[phID];

            Scanner[] scanners = new Scanner[phID];


            for(i=0;i<phID;i++){
                scanners[i] = new Scanner( new File("src/tempFiles/"+PH+i+".txt"));
                inputBuffers[i] = new ArrayList<>();
            }

            boolean[] isEmpty = new boolean[phID];

            //Initial Load
            for(i=0;i<phID;i++){
                isEmpty[i] = false;
                for (int j =0;j < maxInputSize && !isEmpty[i] ;j++){
                    if(scanners[i].hasNextInt()){
                        inputBuffers[i].add(scanners[i].nextInt());
                    }else{
                        isEmpty[i] = true;
                    }
                }
            }

            int min = 2147483645,index = -1;
            boolean boo = true;

            while(true){
                //whether all files are empty or not.
                for (boolean b:isEmpty) {
                    boo = boo && b;
                }
                if(boo)
                    break;

                //find min and load if a list is empty
                for(int k = 0; k < phID;k++){

                    //if empty then refill
                    if(inputBuffers[k].isEmpty()){
                        for (int j =0;j < maxInputSize && !isEmpty[k] ;j++){
                            if(scanners[k].hasNextInt()){
                                inputBuffers[k].add(scanners[k].nextInt());
                            }else{
                                isEmpty[k] = true;
                            }
                        }
                    }

                    //if still empty then file is also empty.
                    if(inputBuffers[k].isEmpty()){
                        isEmpty[k] = true;
                        continue;
                    }

                    //
                    //System.out.print(inputBuffers[k].get(0) + " ");
                    if(min > inputBuffers[k].get(0)){
                        min = inputBuffers[k].get(0);
                        index = k;
                    }
                    //System.out.print("min : " + min);
                }
                //System.out.println("Min :" + min + " Output Index : " + outputIndex + " Output Buffer Capacity :" + outputBufferCapacity);

                if(min == 2147483645)
                    break;

                if(outputIndex==outputBufferCapacity){
                    outputFile.append(toString(output));
                    outputIndex = 0;
                    output = new int[outputBufferCapacity];
                }

                output[outputIndex] = min;
                inputBuffers[index].remove(0);
                min = 2147483645;
                outputIndex++;
            }
            outputFile.write(toString(output));
            outputFile.flush();
            long end = System.nanoTime();
            double sec = (end - start) / 1000000000.0;
            System.out.println(sec + " seconds");
            for (Scanner fsc: scanners) {
                fsc.close();
            }
            outputFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void sort(int arr[]) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i=n-1; i>=0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    static void heapify(int arr[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;
        if (r < n && arr[r] > arr[largest])
            largest = r;

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    public static String toString(int arr[]) {
        return Arrays.stream(arr).mapToObj(s -> s + " ").collect(Collectors.joining());
    }
}
