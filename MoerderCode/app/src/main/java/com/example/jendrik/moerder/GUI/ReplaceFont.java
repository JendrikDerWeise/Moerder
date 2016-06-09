package com.example.jendrik.moerder.GUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by rahel on 09.06.16.
 */
public class ReplaceFont {



        public static void replaceDefaultFont(Context context, String thereplaceFont, String newFont){
            Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), newFont);
            replaceFont(thereplaceFont, customFontTypeface);
            Log.d("hello","replaceDefaultFont");
        }

        private static void replaceFont(String thereplaceFont, Typeface customFontTypeface) {
            try {
                Log.d("hello","replaceFont");
                Field myfield = Typeface.class.getDeclaredField(thereplaceFont);
                myfield.setAccessible(true);
                myfield.set(null, customFontTypeface);
                Log.d("hello","replaceFontunten");

            } catch (NoSuchFieldException e) {
                    e.printStackTrace();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

}
