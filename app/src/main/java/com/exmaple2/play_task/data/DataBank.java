package com.exmaple2.play_task.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    /*final String DATA_FILENAME = "tasknames.data";
    public ArrayList<TaskName> LoadTaskNames(Context context) {
        ArrayList<TaskName> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<TaskName>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            Log.d("Ser1alization", "Data loaded successfully.book count " + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void SaveTaskNames(Context context, ArrayList<TaskName> booknames) {
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
    }*/
    final String SCORE_FILENAME = "score.data";

    public void saveScore(Context context, int score) {
        try {
            FileOutputStream fileOut = context.openFileOutput(SCORE_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(score);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadScore(Context context) {
        int score = 0;
        try {
            FileInputStream fileIn = context.openFileInput(SCORE_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            score = objectIn.readInt();
            objectIn.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }
    public void saveTasks(Context context, ArrayList<TaskName> tasks, String filename) {
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tasks);
            out.close();
            fileOut.close();
            Log.d("Serialization", "Tasks are serialized and saved: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<TaskName> loadTasks(Context context, String filename) {
        ArrayList<TaskName> tasks = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            tasks = (ArrayList<TaskName>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "Tasks loaded successfully: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
