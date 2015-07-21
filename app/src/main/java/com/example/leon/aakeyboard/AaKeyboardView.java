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
                int code = key.codes[0];
                if(code <= -100){
                    painter.setTextSize(64);
                    painter.setColor(Color.BLACK);
                    canvas.drawText(String.valueOf(Character.toChars(aaEmojiCodes.getEmojiCode(code))),
                            key.x + (key.width/2), key.y + (key.height/2) + 25, painter);
                }
                if(code == -4){
                    painter.setTextSize(42);
                    if(getKeyboard().isShifted() == true){
                        painter.setColor(getResources().getColor(R.color.Green));
                    }else{
                        painter.setColor(getResources().getColor(R.color.DarkGrey));
                    }
                    canvas.drawText("CAPS",
                            key.x + (key.width/2), key.y + (key.height/2) + 17, painter);
                }
        }

    }

}

