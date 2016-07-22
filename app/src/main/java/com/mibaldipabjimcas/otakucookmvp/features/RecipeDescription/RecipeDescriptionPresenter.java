package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiClient;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiEndPointInterface;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.MeasureFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.TaskFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@PerActivity
public class RecipeDescriptionPresenter extends BasePresenter<RecipeDescriptionView> {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    Navigator navigator;
    private Recipe recipe;
    private FirebaseDatabase database;
    private DatabaseReference refRecipes;
    private DatabaseReference refMeasures;
    private DatabaseReference refTasks;
    private DatabaseReference refIngredients;
    private DatabaseReference refUserRecipe;
    private ArrayList<Task> recipeTasks;
    private int taskNumber;
    private List<Measure> measures;
    private int measuresNumber;
    private String recipeId;


    @Inject
    public RecipeDescriptionPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(Recipe recipe){
        this.recipe = recipe;
        getView().showRecipeImage(recipe.photo);
        getView().showRecipeName(recipe.name);
        getView().showRecipeAuthor(recipe.author);
        getView().showRecipeRating(recipe.score);
        getView().showRecipePortions(recipe.portions);
        initRef();
    }

    private void initRef() {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        refUserRecipe  = database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("recipes");
        refRecipes  = database.getReference("Recipes");
        refMeasures  = database.getReference("Measures");
        refTasks  = database.getReference("Tasks");
        refIngredients  = database.getReference("Ingredients");

        recipeId = String.valueOf(recipe.id);

        recipeTasks =this.recipe.getTasks();
        taskNumber = recipeTasks.size();

        measures = this.recipe.getMeasures();
        measuresNumber = measures.size();
    }

    private int calculateTime(){
        List<Task> recipeTasks =this.recipe.getTasks();
        int finalTime = 300;
        for (Task t : recipeTasks){
            finalTime = finalTime + t.seconds;
        }
        return finalTime;
    }

    public void showRecipeTaskList(){
        navigator.openRecipeTaskList(recipe.getTasks());
    }

    public void showRecipeIngredientList() {
    }

    public void recipeTime() {
        int time = calculateTime();
    }

    public void saveRecipeFavorite() {

        if (mAuth != null) {
            refRecipes.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        saveRecipeInUser();
                    }else{
                        saveTasksFB(recipeTasks.get(0),0);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getView().showError(ErrorConstants.FIREBASE_ERROR);
                }
            });
        }
    }

    private void saveTasksFB(Task task, final int index){
        TaskFB taskFB = new TaskFB(task);
        refTasks.child(String.valueOf(task.id)).setValue(taskFB).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                int newIndex = index + 1;
                if(newIndex < taskNumber) {
                    saveTasksFB(recipeTasks.get(newIndex), newIndex);
                }else{
                    saveIngredientsFB(measures.get(0),0);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveIngredientsFB(final Measure measure, final int index) {
        refIngredients.child(String.valueOf(measure.ingredient.id)).setValue(measure.ingredient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                saveMeasureFB(measure,measure.ingredient.id,index);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveMeasureFB(Measure measure, long id, final int index) {
        MeasureFB measureFB = new MeasureFB(measure,String.valueOf(id));
        refMeasures.child(String.valueOf(measure.id)).setValue(measureFB).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                int newIndex = index + 1;
                if(newIndex < measuresNumber) {
                    saveIngredientsFB(measures.get(newIndex), newIndex);
                }else{
                    saveRecipeFB();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveRecipeFB() {
        RecipeFB recipeFB = new RecipeFB(recipe);
        refRecipes.child(recipeId).setValue(recipeFB).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                saveTasksInRecipe();
                saveMeasuresInRecipe();
                saveRecipeInUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveMeasuresInRecipe() {
        refRecipes.child(recipeId).child("measures").setValue(recipe.getMeasureListId()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveTasksInRecipe() {
        refRecipes.child(recipeId).child("tasks").setValue(recipe.getTaskListId()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveRecipeInUser() {
        refUserRecipe.child(recipeId).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getView().changeFavoriteIcon(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    public void deleteRecipeFavorite() {

        if (mAuth != null) {
            refUserRecipe.child(recipeId).removeValue();
            getView().changeFavoriteIcon(false);
        }
    }

    public void setRecipeFavorite() {
        refUserRecipe.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    deleteRecipeFavorite();
                }else{
                    saveRecipeFavorite();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
