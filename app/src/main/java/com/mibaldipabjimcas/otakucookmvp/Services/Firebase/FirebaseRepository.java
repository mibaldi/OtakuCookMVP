package com.mibaldipabjimcas.otakucookmvp.Services.Firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.DataListener;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.MeasureFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.TaskFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Pablo Jiménez Casado on 29/07/2016.
 */
@Singleton
public class FirebaseRepository {
    private DatabaseReference refRecipes;
    private DatabaseReference refTasks;
    private DatabaseReference refMeasures;
    private DatabaseReference refIngredients;
    private DatabaseReference refFavorites;
    private FirebaseAuth mAuth;
    private DataListener<Recipe> recipeListener;
    private Recipe recipe;
    private DataListener<List<Recipe>> favoritesListener;
    private List<Recipe> favoriteList;
    private DataListener<Boolean> recipeFavoriteListener;
    private ArrayList<Task> recipeTasks;
    private int taskNumber;
    private List<Measure> measures;
    private int measuresNumber;
    private String recipeId;

    @Inject
    public FirebaseRepository(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refRoot  = database.getReference("Users");
        refRecipes = database.getReference("Recipes");
        refTasks = database.getReference("Tasks");
        refMeasures = database.getReference("Measures");
        refIngredients = database.getReference("Ingredients");
        mAuth = FirebaseAuth.getInstance();
        refFavorites = refRoot.child(mAuth.getCurrentUser().getUid()).child("favorites");
    }

    public FirebaseAuth getAuth(){
        return mAuth;
    }

    public void getFavorites(ValueEventListener listener){
        refFavorites.addValueEventListener(listener);
    }

    public void getRecipeBasic(String key,ValueEventListener listener){
        refRecipes.child(key).addValueEventListener(listener);
    }

    public void getRecipeComplete(final String key, final DataListener<Recipe> listener){
        this.recipeListener = listener;
        refRecipes.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RecipeFB r = dataSnapshot.getValue(RecipeFB.class);
                recipe = new Recipe(key,r);
                loadRecipeFB();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void loadRecipeFB() {
        refRecipes.child(String.valueOf(recipe.id)).child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    getTaskFirebase(dataSnapshot.getChildren().iterator());
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void getMeasures(){
        refRecipes.child(String.valueOf(recipe.id)).child("measures").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    getMeasureFirebase(dataSnapshot.getChildren().iterator());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void getMeasureFirebase(final Iterator<DataSnapshot> iterator) {
        if(iterator.hasNext()) {
            String key = iterator.next().getKey();
            refMeasures.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MeasureFB measureFB = dataSnapshot.getValue(MeasureFB.class);
                    getIngredientFB(measureFB,iterator);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    recipeListener.onError(ErrorConstants.FIREBASE_ERROR);
                }
            });
        }else{
            recipeListener.onSuccess(recipe);
        }
    }

    private void getIngredientFB(MeasureFB measureFB, final Iterator<DataSnapshot> iterator) {
        refIngredients.child(measureFB.ingredientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Ingredient ingredient = dataSnapshot.getValue(Ingredient.class);
                Measure measure = new Measure();
                measure.ingredient = ingredient;
                recipe.measureIngredients.add(measure);
                getMeasureFirebase(iterator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });

    }

    private void getTaskFirebase(final Iterator<DataSnapshot> iterator) {
        if(iterator.hasNext()){
            String key = iterator.next().getKey();
            refTasks.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Task task = dataSnapshot.getValue(Task.class);
                    recipe.tasks.add(task);
                    getTaskFirebase(iterator);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    recipeListener.onError(ErrorConstants.FIREBASE_ERROR);
                }
            });
        }else{
            getMeasures();
        }

    }

    public void getRecipeFavorites(DataListener<List<Recipe>> listener){
        this.favoritesListener = listener;
        favoriteList = new ArrayList<>();
        refFavorites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadFavoriteRecipe(dataSnapshot.getChildren().iterator());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                favoritesListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void loadFavoriteRecipe(final Iterator<DataSnapshot> iterator) {
        if(iterator.hasNext()){
            final String key = iterator.next().getKey();
            getRecipeBasic(key, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RecipeFB recipeFB = dataSnapshot.getValue(RecipeFB.class);
                    Recipe recipe = new Recipe(key,recipeFB);
                    favoriteList.add(recipe);
                    loadFavoriteRecipe(iterator);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    favoritesListener.onError(ErrorConstants.FIREBASE_ERROR);
                }
            });
        }else{
            favoritesListener.onSuccess(favoriteList);
        }
    }

    public void setFirebaseFavorite(Recipe recipe, DataListener<Boolean> listener){
        this.recipe = recipe;
        this.recipeFavoriteListener = listener;

        recipeId = String.valueOf(recipe.id);

        recipeTasks = recipe.getTasks();
        taskNumber = recipeTasks.size();

        measures = recipe.getMeasures();
        measuresNumber = measures.size();

        refFavorites.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    deleteRecipeFavorite();
                } else {
                    saveRecipeFavorite();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    public void deleteRecipeFavorite() {
        refFavorites.child(recipeId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                recipeFavoriteListener.onSuccess(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }


    public void saveRecipeFavorite(){
        refRecipes.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    saveRecipeInUser();
                }else{
                    saveTasksFB(recipeTasks.get(0),0);
                    saveIngredientsFB(measures.get(0),0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeFavoriteListener.onError(ErrorConstants.RECIPE_FAVORITE_ERROR);
            }
        });
    }

    private void saveRecipeInUser() {
        refFavorites.child(recipeId).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                recipeFavoriteListener.onSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
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
                    saveRecipeFB();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
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
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
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
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveRecipeFB() {
        refRecipes.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    createRecipeInFB();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void createRecipeInFB() {
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
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveMeasuresInRecipe() {
        refRecipes.child(recipeId).child("measures").setValue(recipe.getMeasureListId()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    private void saveTasksInRecipe() {
        refRecipes.child(recipeId).child("tasks").setValue(recipe.getTaskListId()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recipeFavoriteListener.onError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }


}
