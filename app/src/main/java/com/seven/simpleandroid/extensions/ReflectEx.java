package com.seven.simpleandroid.extensions;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public class ReflectEx {
    public static <T> void setField(T obj, @NonNull String field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class mClass = obj.getClass();
        Field mFiled = mClass.getDeclaredField(field);
        mFiled.setAccessible(true);
        mFiled.set(obj, value);
    }
    
    public static <V> V getField(Object obj, @NonNull String field) throws NoSuchFieldException, IllegalAccessException {
        Class mClass = obj.getClass();
        Field mFiled = mClass.getDeclaredField(field);
        mFiled.setAccessible(true);
        
        return (V) mFiled.get(obj);
    }
}
