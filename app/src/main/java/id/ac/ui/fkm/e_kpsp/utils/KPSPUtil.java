package id.ac.ui.fkm.e_kpsp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KPSPUtil {
    private static Integer[] kpspMonthArray = {3,6,9,12,15,18,21,24,30,36,42,48,54,60,66,72};
    private static List<Integer> kpspMonthList = new ArrayList<>(Arrays.asList(kpspMonthArray));

    public static int getPrevMonthOf(int usia){
        if(kpspMonthList.contains(usia))return usia;
        for (int i = kpspMonthArray.length -1;i>=0;i--){
            int selectedMonth = kpspMonthArray[i];
            if(selectedMonth < usia){
                return selectedMonth;
            }
        }
        return usia;
    }

    public static int getNextMonthOf(int usia){
        if(kpspMonthList.contains(usia))return usia;
        for(int i = 0; i < kpspMonthArray.length;i++){
            int selectedMonth = kpspMonthArray[i];
            if(selectedMonth > usia){
                return selectedMonth;
            }
        }
        return usia;
    }
}
