package com.exmaple2.hello_android.data;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.exmaple2.hello_android.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataBankTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loadBookNames() {
        DataBank dataBank = new DataBank();
        ArrayList<BookName> bookNames = dataBank.LoadBookNames(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertEquals(0,bookNames.size());
    }

    @Test
    public void saveBookNames() {
        DataBank dataSaver=new DataBank();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<BookName> bookNames=new ArrayList<>();
        BookName bookName=new BookName("测试", R.drawable.book_1);
        bookNames.add(bookName);
        bookName=new BookName("正常", R.drawable.book_2);
        bookNames.add(bookName);
        dataSaver.SaveBookNames(targetContext,bookNames);

        DataBank dataLoader=new DataBank();
        ArrayList<BookName> shopItemsRead=dataLoader.LoadBookNames(targetContext);
        assertNotSame(bookNames,shopItemsRead);
        assertEquals(bookNames.size(),shopItemsRead.size());
        for(int index=0;index<bookNames.size();++index)
        {
            assertNotSame(bookNames.get(index),shopItemsRead.get(index));
            assertEquals(bookNames.get(index).getTitle(),shopItemsRead.get(index).getTitle());
            assertEquals(bookNames.get(index).getCoverResourceId(),shopItemsRead.get(index).getCoverResourceId());
        }
    }
}