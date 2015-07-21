package com.example.leon.aakeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

/**
 * Created by leon on 25/06/2015.
 */
public class AaKeyboard extends Keyboard {

    //CONTROL KEYCODES
    public static final int KEYCODE_ENTER = -1;
    public static final int KEYCODE_BACKSPACE = -2;
    public static final int KEYCODE_ALT_SYMBOLS = -3;
    public static final int KEYCODE_SHIFTED_CAPS = -4;
    public static final int KEYCODE_STICKY_CAPS = -5;
    public static final int KEYCODE_NOT_CAPS = -6;
    public static final int KEYCODE_ALPHA = -7;
    public static final int KEYCODE_SYMBOL = -8;
    public static final int KEYCODE_EMOJI = -9;



    //SAME AS PARENT CONSTRUCTORS - SEE KEYBOARD
    public AaKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    public AaKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public AaKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public AaKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

}
