package com.mibaldipabjimcas.otakucookmvp.data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.TaskFB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 9/4/16.
 */
public class Task implements Parcelable,Comparable<Task> {
    public long id;
    public String name;
    public String photo;
    public int seconds;
    public String description;

    public Task() {
    }

    @Override
    public int compareTo(Task another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.photo);
        dest.writeInt(this.seconds);
        dest.writeString(this.description);
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.photo = in.readString();
        this.seconds = in.readInt();
        this.description = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    public static TaskFB Task2FB(Task task,long recipeId){
        return new TaskFB(task,recipeId);
    }
    public static List<TaskFB> TaskList2FB(List<Task>taskList,long recipeId){
        List<TaskFB> taskFBList = new ArrayList<>();
        for (Task t: taskList){
            taskFBList.add(Task.Task2FB(t,recipeId));
        }
        return taskFBList;
    }
    public static Task FB2Task(TaskFB taskFB){
        Task task = new Task();
        task.name = taskFB.name;
        task.photo = taskFB.photo;
        task.seconds = taskFB.seconds;
        task.description = taskFB.description;
        return task;
    }
}
