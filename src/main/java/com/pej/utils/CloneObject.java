package com.pej.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
public class CloneObject {
	
	public static Object cloner(Object obj, Object clone){
        try{
            //Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloner(field.get(obj), clone));
                    }
                }
            }
            return clone;
        }catch(Exception e){
        	System.out.println("Erreur de clonage: "+e.getMessage());
            return null;
        }
    }
}
