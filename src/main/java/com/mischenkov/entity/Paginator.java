package com.mischenkov.entity;

public class Paginator {

    public static String[] getPages(int count, int total) {
        float fTotal = total;
        float fCount = count;
        int pages = (int) Math.ceil( fTotal / fCount );
        String[] result = new String[pages];

        for (int i = 0; i < pages; i++) {
            String preString = "&count=" + count;
            preString += "&position=" + (i * count);

            result[i] = preString;
        }
        return result;
    }

}
