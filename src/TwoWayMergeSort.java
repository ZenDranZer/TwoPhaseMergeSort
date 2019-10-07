import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TwoWayMergeSort {
    public static void main(String[] args) {

        try{
            long start = System.currentTimeMillis();
            //Phase 1
            File inputFile = new File("src/inputFiles/input3M.txt");
            Scanner sc = new Scanner(inputFile);
            String[] firstLine = sc.nextLine().split(" ");

            int n = Integer.parseInt(firstLine[0]);
            int i = 0;
            int[] array = new int[50000];


            int phID = 0;
            String PH = "PH";


            Runtime runtime = Runtime.getRuntime();
            double freeMemory = runtime.freeMemory();
            double usableMemory = freeMemory * 0.6; //60% usage.
            //System.out.println(usableMemory/1024);


            while(sc.hasNextInt()){
                if(i == 50000){
                    Arrays.parallelSort(array);
                    BufferedWriter out = new BufferedWriter(new FileWriter("src/tempFiles/"+PH+phID+".txt"));
                    phID++;
                    out.write(toString(array));
                    out.flush();
                    n-=50000;
                    if(n>50000){
                        array = new int[50000];
                    }else{
                        array = new int[n];
                    }
                    i=0;
                    out.close();
                }
                array[i] = sc.nextInt();
                i++;
            }


            Arrays.parallelSort(array);
            BufferedWriter out = new BufferedWriter(new FileWriter("src/tempFiles/"+PH+phID+".txt"));
            out.write(toString(array));
            out.flush();
            out.close();
            sc.close();

            phID++;

            //Phase 2
            System.gc();

            BufferedWriter outputFile = new BufferedWriter(new FileWriter("src/output.txt",true));

            runtime = Runtime.getRuntime();
            usableMemory = (runtime.freeMemory() * 0.6) / 1024; //60% usage.
            //System.out.println(usableMemory);


            int memoryPerBuffer = (int)Math.floor(usableMemory/(phID+1));

            int[] output = new int[10000];
            int outputIndex = 0;
            int maxInputSize = 40000/phID;

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
                    //System.out.print(min);
                }
                //System.out.println();
                if(outputIndex==10000){
                    outputFile.write(toString(output));
                    outputFile.flush();
                    outputIndex = 0;
                    output = new int[10000];
                }
                if(inputBuffers[index].isEmpty())
                    break;
                output[outputIndex] = inputBuffers[index].remove(0);
                min = 2147483645;
                outputIndex++;
            }
            long end = System.currentTimeMillis();
            float sec = (end - start) / 1000F;
            System.out.println(sec + " seconds");
            for (Scanner fsc: scanners) {
                fsc.close();
            }
            outputFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String toString(int arr[]) {
        return Arrays.stream(arr).mapToObj(s -> s + " ").collect(Collectors.joining());
    }
}
