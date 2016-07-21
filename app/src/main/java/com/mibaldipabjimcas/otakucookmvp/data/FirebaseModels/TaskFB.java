package com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;

/**
 * Created by mikelbalducieldiaz on 21/7/16.
 */

public class TaskFB {
    public String name;
    public String photo;
    public int seconds;
    public String description;

    public TaskFB (Task task){
        this.name = task.name;
        this.photo = task.photo;
        this.seconds = task.seconds;
        this.description = task.description;
    }
}
