package com.example.leon.aakeyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by leon on 13/06/2015.
 */
public class AaKeyboardView extends KeyboardView {

    /**
     * This field is used to identify which emoji are to be drawn on an emoji key
     */
    private AaEmojiCodes aaEmojiCodes = new AaEmojiCodes();


    /**
     * This field is used to remember which lettercase to show.
     */
    private int whichCase = LOWERCASE;
    public static final int LOWERCASE = 1;
    public static final int UPPERCASE = 2;
    public static final int STICKYCASE = 3;


    //SAME AS PARENT CONSTRUCTORS - SEE KEYBOARDVIEW
    public AaKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AaKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AaKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     * This method overrides the KeyboardView onDraw to draw emoji characters on emoji keys.
     *
     * @param canvas - The key view.
     * by leon pearce.
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint painter = new Paint();
        painter.setTextAlign(Paint.Align.CENTER);
        painter.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for(Keyboard.Key key: keys) {
                int primaryCode = key.codes[0];
                if(primaryCode <= -100){
                    painter.setTextSize(64);
                    painter.setColor(Color.BLACK);
                    canvas.drawText(String.valueOf(Character.toChars(aaEmojiCodes.getEmojiCode(primaryCode))),
                            key.x + (key.width/2), key.y + (key.height/2) + 25, painter);
                }
                if(primaryCode == AaKeyboard.KEYCODE_SHIFT_CAPS){
                    painter.setTextSize(42);
                    if(whichCase == LOWERCASE){
                        painter.setColor(getResources().getColor(R.color.DarkGrey));
                    }else if(whichCase == UPPERCASE){
                        painter.setColor(getResources().getColor(R.color.Green));
                    }else if(whichCase == STICKYCASE){
                        painter.setColor(getResources().getColor(R.color.Green));
                        painter.setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    }
                    canvas.drawText("CAPS",
                            key.x + (key.width/2), key.y + (key.height/2) + 17, painter);
                }
        }

    }

    /**
     * This method is a helper to shift lettercase to lower.
     *
     * by leon pearce
     */
    public void shiftCaseToLower() {
        whichCase = LOWERCASE;
        setShifted(false);
    }

    /**
     * This method is a helper to shift lettercase to upper.
     *
     * by leon pearce
     */
    public void shiftCaseToUpper() {
        whichCase = UPPERCASE;
        setShifted(true);
    }

    /**
     * This method is a helper to shift lettercase to sticky.
     *
     * by leon pearce
     */
    public void shiftCaseToSticky() {
        whichCase = STICKYCASE;
        setShifted(true);
    }

    public int getCase() {
        return whichCase;
    }
}

