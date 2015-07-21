package com.example.leon.aakeyboard;

import android.content.Context;

/**
 * Created by leon on 26/06/2015.
 */
public class AlphaAaKeyboard extends AaKeyboard {

    /**
     * This field is used to remember which case to use.
     */
    public static final int NOT_CAPS = 0;
    public static final int SHIFTED_CAPS = 1;
    public static final int STICKY_CAPS = 2;
    private int whichCaps = NOT_CAPS;


    //SAME AS PARENT CONSTRUCTORS - SEE KEYBOARD
    public AlphaAaKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    public AlphaAaKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public AlphaAaKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public AlphaAaKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    //ACCESSORS

    /**
     * This method is used to see which case the keyboard is in.
     *
     * @return - an int value corresponding to a constant
     * representing the keyboards case state.
     * by leon pearce
     */
    public int getWhichCaps(){
        return whichCaps;
    }

    /**
     * This method is used to set which case the kyboard is in.
     *
     * @param whichCaps - A constant value used to remember
     * the keyboard state.
     * by leon pearce.
     */
    public void setWhichCaps(int whichCaps){
        this.whichCaps = whichCaps;
    }

}
