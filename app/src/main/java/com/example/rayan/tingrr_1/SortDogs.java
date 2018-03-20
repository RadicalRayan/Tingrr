package com.example.rayan.tingrr_1;

/**
 * Created by FredQ on 3/20/2018.
 */

public class SortDogs
{
    public void sort(Dog dogs[], int l, int r)
    {
        if (l < r)
        {
            int m = (l+r)/2;
            sort(dogs, l, m);
            sort(dogs , m+1, r);
            merge(dogs, l, m, r);
        }
    }
    private void merge(Dog dogs[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        Dog L[] = new Dog [n1];
        Dog R[] = new Dog [n2];
        for (int i=0; i<n1; ++i)
            L[i] = dogs[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = dogs[m + 1+ j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i].getPoints() >= R[j].getPoints())
            {
                dogs[k] = L[i];
                i++;
            }
            else
            {
                dogs[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1)
        {
            dogs[k] = L[i];
            i++;
            k++;
        }

        while (j < n2)
        {
            dogs[k] = R[j];
            j++;
            k++;
        }
    }
}