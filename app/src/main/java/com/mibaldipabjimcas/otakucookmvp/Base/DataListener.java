package com.mibaldipabjimcas.otakucookmvp.Base;

/**
 * Created by Pablo Jiménez Casado on 29/07/2016.
 */

public interface DataListener<T> {

    void onSuccess(T data);

    void onError(int error);
}
