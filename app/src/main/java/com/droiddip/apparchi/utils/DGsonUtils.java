package com.droiddip.apparchi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class DGsonUtils {

    private DGsonUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static String ObjectTOStringJSON(Object object) {
        String result = "";
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            result = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object StringJSONTOObject(String stringaJSON, Class<?> classe) {
        Object object = null;
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            object = gson.fromJson(stringaJSON, classe);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
