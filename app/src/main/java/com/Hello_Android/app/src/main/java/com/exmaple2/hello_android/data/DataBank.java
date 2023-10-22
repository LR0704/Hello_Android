package com.exmaple2.hello_android.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String DATA_FILENAME = "booknames.data";
    public ArrayList<BookName> LoadBookNames(Context context) {
        ArrayList<BookName> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<BookName>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            Log.d("Ser1alization", "Data loaded successfully.book count " + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void SaveBookNames(Context context, ArrayList<BookName> booknames) {
        try{
            //打开内部文件
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME,Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(booknames);
            out.close();
            fileOut.close();
            Log.d("Serialization","Data is serialized and saved.");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
