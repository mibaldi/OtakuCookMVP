package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Base.DataListener;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.Services.Broadcast.MyAlarmReceiver;
import com.mibaldipabjimcas.otakucookmvp.Services.Firebase.FirebaseRepository;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.MeasureFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.TaskFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static android.content.Context.ALARM_SERVICE;

@PerActivity
public class RecipeDescriptionPresenter extends BasePresenter<RecipeDescriptionView> {

    @Inject
    FirebaseRepository firebaseRepository;

    Navigator navigator;
    private Recipe recipe;
    private Boolean favorite;
    private Context context;

    @Inject
    public RecipeDescriptionPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(Recipe recipe,Context context) {
        this.recipe = recipe;
        this.context = context;

        if (!BuildConfig.SHOW_PREMIUM_ACTIONS) {
            getView().hideFavoriteIcon();
        }

        if (firebaseRepository.getAuth() != null) {
            firebaseRepository.checkFavoriteRecipe(recipe, new DataListener<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    favorite = data;
                }

                @Override
                public void onError(int error) {
                    getView().showError(error);
                }
            });
        }

        getView().showRecipeImage(recipe.photo);
        getView().showRecipeName(recipe.name);
        getView().showRecipeAuthor(recipe.author);
        getView().showRecipeRating(recipe.score);
        getView().showRecipePortions(recipe.portions);
        getView().showRecipeTime(formatTime());
    }

    public int calculateTime() {
        List<Task> recipeTasks = this.recipe.getTasks();
        int finalTime = 300;
        for (Task t : recipeTasks) {
            finalTime = finalTime + t.seconds;
        }
        return finalTime * 1000;
    }

    public String formatTime() {
        int seconds = this.calculateTime()/1000;
        return String.format("%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60));
    }

    public void showRecipeIngredientList() {
        navigator.openRecipeIngredientList(recipe.getMeasures());
    }
    public void showRecipeTaskList() {
        navigator.openRecipeTaskList(recipe.getTasks());
    }


    public void setRecipeFavorite() {
        getView().showProgressBar(true);
        if (firebaseRepository.getAuth() != null) {
            firebaseRepository.setFirebaseFavorite(favorite,new DataListener<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    favorite = data;
                    getView().showProgressBar(false);
                    getView().changeFavoriteIcon(data);
                }

                @Override
                public void onError(int error) {
                    getView().showProgressBar(false);
                    getView().showError(error);
                }
            });

        } else {
            getView().showError(ErrorConstants.FIREBASE_ERROR);
        }
    }

    public void generateAlarm(Context context, long time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        int type = AlarmManager.RTC;
        Calendar calendar = Calendar.getInstance();
        long when = calendar.getTimeInMillis() + time;
        Intent myIntent = new Intent(context, MyAlarmReceiver.class);
        myIntent.putExtra("recipe",recipe);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        alarmManager.set(type, when, pendingIntent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RecipeDescriptionActivity.FAVORITE_OK){
                    setRecipeFavorite();
                }
                break;
            case 2:
                if(resultCode == RecipeDescriptionActivity.SHARED_OK){
                    navigator.sharedRecipe(recipe,getLocalBitmapUri());
                }
                break;
            case 3:
                if(resultCode == RecipeDescriptionActivity.SHARED_OK){
                    setTimeChallenge();
                }
                break;
            default:
                break;
        }


    }

    private void setTimeChallenge() {

        getView().setLayoutDisabled();
        getView().setImageTimeStart();
       // view.setEnabled(false);

        //imageTime.setImageResource(R.drawable.congelado);
        generateAlarm(context, /*calculateTime()*/ 3000);
    }

    public void openFavoriteDialog(Fragment fragment) {
        navigator.openFavoriteDialog(fragment,favorite);
    }

    public void openSharedDialog(Fragment fragment){
        navigator.openSharedDialog(fragment);
    }
    public void openTimeDialog(Fragment fragment){
        navigator.openTimeDialog(fragment);
    }

    private Uri getLocalBitmapUri() {

        Drawable drawable = getView().getDrawableImage();
        Bitmap bmp = null;
        if (drawable instanceof GlideBitmapDrawable){

            bmp = ((GlideBitmapDrawable) drawable).getBitmap();
        } else {
            return null;
        }
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
