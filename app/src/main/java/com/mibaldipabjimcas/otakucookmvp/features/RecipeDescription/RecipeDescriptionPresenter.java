package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiClient;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiEndPointInterface;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        refUserRecipe  = database.getReference("users").child(mAuth.getCurrentUser().getUid()).child("recipes");
        refRecipes  = database.getReference("recipes");
        refMeasures  = database.getReference("measures");
        refTasks  = database.getReference("tasks");
        refIngredients  = database.getReference("ingredients");
        mAuth = FirebaseAuth.getInstance();
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
            refRecipes.setValue(recipeFirebase);
            String recipeId = refRecipes.getKey();
            for(Task t: recipe.getTasks()){
                refTasks.setValue(t);
                refRecipes.child(recipeId).child("tasks").setValue(refTasks.getKey());
            }
            for(Measure m : recipe.getMeasures()){
                refMeasures.setValue(m);
            }
            //refUserRecipe.setValue(recipe.id);
            //refRecipes.setValue(recipeFirebase)
            //refMeasures.setValue(measuresFirebase)
            //refTasks.setValue(tasksFirebase)
            //refIngredients.setValue(ingredientsFirebase)
            getView().changeFavoriteIcon(true);
        }
    }
    public void deleteRecipeFavorite() {

        if (mAuth != null) {
            refUserRecipe.child(String.valueOf(recipe.id)).removeValue();
            getView().changeFavoriteIcon(false);
        }
    }

    public void setRecipeFavorite() {
        if(refUserRecipe.child(String.valueOf(recipe.id)) != null){
            deleteRecipeFavorite();
        }else{
            saveRecipeFavorite();
        }
    }
}
