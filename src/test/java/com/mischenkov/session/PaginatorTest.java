package com.mischenkov.session;

import com.mischenkov.entity.Paginator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PaginatorTest {

    @Test
    public void shouldCountTest() {
        int total = 100;
        int count = 20;

        String[] result = Paginator.getPages(count, total);
        Assert.assertEquals(5, result.length);

        System.out.println(Arrays.toString(result) );
    }

}
