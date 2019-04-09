package com.example.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;
    private final int numberOfElements = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sortedLV = this.findViewById(R.id.sortedLV);
        this.unsortedLV = this.findViewById(R.id.unsortedLV);

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        //create simple list view row at 27min

        //set up arrayadapters
        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();

        //this.displayArray(this.sortedNumbers);
        //this.mergeSort(this.sortedNumbers);
    }

    private void displayArray(int[] ar)
    {
        String answer = "";
        for(int i = 0; i < ar.length; i++)
        {
            answer += ar[i] + ", ";
        }
        System.out.println(answer);
    }

    private void mergeHelper(int[] ar, int begin, int end)
    {
        if(begin != end)
        {
            int begin1 = begin;
            int end1 = (begin + end) / 2;
            int begin2 = end1 +1;
            int end2 = end;

            this.mergeHelper(ar, begin1, end1);
            this.mergeHelper(ar, begin2, end2);
            this.mergeStep(ar, begin1, end1, begin2, end2);
        }
        else
        {
            System.out.println("smallest size: mergeHelper");
        }
    }

    //#FIXME: Rewrite this to match 200 code
    private void mergeStep(int[] ar, int begin1, int end1, int begin2, int end2)
    {
        System.out.println("begin " + begin1 + "| end " + end2);
        int length = (end2 - begin1) +1;
        int start1 = begin1;
        int start2 = begin2;
        int[] temp = new int[length];
        int tempCount = 0;

        while(start1 <= end1 || start2 <= end2)
        {
            if((start1 <= end1) &&(start2 <= end2))
            {
                if(ar[start1] < ar[start2])
                {
                    temp[tempCount] = ar[start1];
                    start1++;
                    tempCount++;
                }
                else //start2 is greater than start1 or they are equal or something went really wrong
                {
                    temp[tempCount] = ar[start2];
                    start2++;
                    tempCount++;
                }
            }
            else if(start1 > end1)
            {
                temp[tempCount] = ar[start2];
                start2++;
                tempCount++;
            }
            else if(start2 > end2)
            {
                temp[tempCount] = ar[start1];
                start1++;
                tempCount++;
            }
            else
            {
                System.out.println("WRONG!");
            }
        }


        //replaces ar values in correct order for this recursive step
        for(int tempPos = 0; tempPos < length; tempPos++)
        {
            ar[begin1 + tempPos] = temp[tempPos];
        }
        //System.out.println("begin: " + begin);
        //System.out.println("end: " + end);
        //System.out.println("start1: " + start1);
        //System.out.println("start2: " + start2);
        this.displayArray(ar);
        //this.mergeStep(ar, begin, middle);
        System.out.println("******");
        //this.mergeStep(ar, middle +1, end);
    }

    private void mergeSort(int[] ar)
    {
        int begin1 = 0;
        int end1 = (ar.length -1) /2;
        int begin2 = end1 +1;
        int end2 = ar.length -1;

        this.mergeHelper(ar, begin1, end1);
        this.mergeHelper(ar, begin2, end2);
        this.mergeStep(ar, begin1, end1, begin2, end2);
    }

    //recurssion refressher example
    //5! = 5 * 4 * 3 * 2 * 1
    //5! = 5 * 4!
    //         4! = 4 * 3!
    //                  3! = 3 * 2!
    //                           2! = 2 * 1!
    //                                    1! = 1
    private int factorial(int n)
    {
        if(n == 1)
        {
            return 1; //1! = 1
        }
        else
        {
            return n * this.factorial(n-1); //factorial reducing to 1
        }
    }


    private void insertionSort(int[] ar)
    {
        int theFollower, swap;
        for(int currStart = 1; currStart < ar.length; currStart++)
        {
            theFollower = currStart;
            while(theFollower > 0 && ar[theFollower] < ar[theFollower-1])
            {
                swap = ar[theFollower];
                ar[theFollower] = ar[theFollower-1];
                ar[theFollower-1] = swap;
                theFollower--;
            }
        }
    }

    public void mergeSortButtonPressed(View luke)
    {
        this.mergeSort(this.sortedNumbers);
        this.updateStringArrays();
    }

    public void insertionSortButtonPressed(View vy)
    {
        this.insertionSort(this.sortedNumbers);
        this.updateStringArrays();
    }

    public void resetButtonPressed(View austin)
    {
        this.initializeArrays();
    }

    private void initializeArrays()
    {
        //fill my unsorted int array
        this.fillRandomIntArray(this.unsortedNumbers);
        this.copyContentOfIntArrays(this.unsortedNumbers, this.sortedNumbers);
        this.updateStringArrays();
    }

    private void updateStringArrays()
    {
        //fill my string arrays to mimic the int arrays
        this.copyIntArrayToStringArray(this.sortedNumbers, this.sortedStrings);
        this.copyIntArrayToStringArray(this.unsortedNumbers, this.unsortedStrings);
        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyIntArrayToStringArray(int[] arInts, String[] arStrings)
    {
        for(int i = 0; i< arInts.length; i++)
        {
            arStrings[i] = "" + arInts[i];
        }
    }

    private void copyContentOfIntArrays(int[] source, int[] destination)
    {
        for(int i = 0; i < source.length; i++)
        {
            destination[i] = source[i];
        }
    }

    private void fillRandomIntArray(int[] ar)
    {
        Random r = new Random();
        for(int i = 0; i < ar.length; i++)
        {
            ar[i] = r.nextInt(500);
        }
    }
}
