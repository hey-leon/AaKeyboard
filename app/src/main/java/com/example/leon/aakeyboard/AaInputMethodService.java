package com.example.leon.aakeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.List;

/**
 * Created by leon on 10/06/2015.
 */
public class AaInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    /**
     * These fields hold the Keyboards and KeyboardView in the current version
     * of Aa Keyboard.
     */
    private AaKeyboardView aaKeyboardView;

    private AlphaAaKeyboard alphaKeyboard;
    private AaKeyboard symbolKeyboard;
    private AaKeyboard altSymbolKeyboard;
    private AaKeyboard emojiKeyboard;

    /**
     * This field is used to find the unicode for each emoji key.
     */
    private AaEmojiCodes aaEmojiCodes =  new AaEmojiCodes();

    //TRIGGERS/WORDS
    //todo add trigger word functionality


    /**
     * This field is used to remeber which symbol to show.
     */
    private boolean whichSymbols;

    /**
     * This Override of onCreateInputView() for the InputMethodService (IMS)
     * handles loading the AaKeyboards, and AakeyboardView and is called on
     * inbound intent or an IMS.
     *
     * @return - The AaKeyboardView with the default AaKebard (alpha).
     * by leon pearce
     */
    @Override
    public View onCreateInputView() {
        //INITIALISE VIEW AND KEYBOARDS
        aaKeyboardView = (AaKeyboardView) getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        alphaKeyboard = new AlphaAaKeyboard(this, R.xml.alpha_layout);
        symbolKeyboard = new AaKeyboard(this, R.xml.symbol_layout);
        altSymbolKeyboard = new AaKeyboard(this, R.xml.alt_symbol_layout);
        emojiKeyboard = new AaKeyboard(this, R.xml.emoji_layout);
        //SET LISTENER AND DEFAULT KEYBOARD
        aaKeyboardView.setKeyboard(alphaKeyboard);
        aaKeyboardView.setOnKeyboardActionListener(this);

        return aaKeyboardView;
    }

    //todo onFinishInputView


    //LISTENERS METHODS
    @Override
    //todo
    public void onPress(int primaryCode) {
        if(primaryCode < 0){
            aaKeyboardView.setPreviewEnabled(false);
        }
    }

    @Override
    //todo
    public void onRelease(int primaryCode) {
        if(primaryCode < 0){
            aaKeyboardView.setPreviewEnabled(true);
        }
    }

    /**
     * This method is called on all key presses and handles the current keycode
     * offered by user input.
     *
     * @param primaryCode - This is the current keycode offered by user input.
     * @param keyCodes - This array includes all other key codes in order
     *                 from after the primary keycode.
     * by leon pearce
     */
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

        //HANDLE CONTROL KEYS
        switch (primaryCode) {
            case AaKeyboard.KEYCODE_ENTER:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                break;
            case AaKeyboard.KEYCODE_BACKSPACE:
                onKeyBackspace(ic);
                break;
            case AaKeyboard.KEYCODE_ALT_SYMBOLS:
                whichSymbols = !whichSymbols;
                changeSymbols(whichSymbols);
                break;
            case AaKeyboard.KEYCODE_SHIFTED_CAPS:
                if(alphaKeyboard.getWhichCaps() == AlphaAaKeyboard.SHIFTED_CAPS
                        || alphaKeyboard.getWhichCaps() == AlphaAaKeyboard.STICKY_CAPS){
                    alphaKeyboard.setWhichCaps(AlphaAaKeyboard.NOT_CAPS);
                    aaKeyboardView.setShifted(false);
                }else{
                    alphaKeyboard.setWhichCaps(AlphaAaKeyboard.SHIFTED_CAPS);
                    aaKeyboardView.setShifted(true);
                }
                break;
            case AaKeyboard.KEYCODE_STICKY_CAPS:
                alphaKeyboard.setWhichCaps(AlphaAaKeyboard.STICKY_CAPS);
                aaKeyboardView.setShifted(true);
                break;
            case AaKeyboard.KEYCODE_NOT_CAPS:
                alphaKeyboard.setWhichCaps(AlphaAaKeyboard.NOT_CAPS);
                aaKeyboardView.setShifted(false);
                break;
            case AaKeyboard.KEYCODE_ALPHA:
                alphaKeyboard.setWhichCaps(AlphaAaKeyboard.NOT_CAPS);
                aaKeyboardView.setKeyboard(alphaKeyboard);
                break;
            case AaKeyboard.KEYCODE_EMOJI:
                aaKeyboardView.setKeyboard(emojiKeyboard);
                break;
            case AaKeyboard.KEYCODE_SYMBOL:
                whichSymbols = false;
                aaKeyboardView.setKeyboard(symbolKeyboard);
                break;
            default:
                //HANDLES EITHER KEY TYPES THAT ARENT CONTROL KEY
                onEmojiKey(primaryCode, ic);
                onRegularKey(primaryCode, ic);

                //HANDLES SHIFT STATE IF ALPHA KEYBOARD
                ReleaseShiftedCaps();

                break;
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    /**
     * This method handles all regular primaryCodes (Characters & Symbols from the onKey method.
     * The method takes the current input connection and sends it the char or symbol.
     *
     * @param primaryCode - The char or symbols unicode value.
     * @param ic - The input connection for the char or symbol to be sent to.
     * by leon pearce
     */
    private void onRegularKey(int primaryCode, InputConnection ic) {
        if (primaryCode >= 0) {
            char code = (char) primaryCode;
            //IF UPPERCASE
            if(aaKeyboardView.isShifted()) {
                code = Character.toUpperCase(code);
            }
            //COMMIT CHARACTER
            ic.commitText(String.valueOf(code), 1);
        }
    }

    /**
     * This method handles all emoji primaryCodes from the onKey method. The method takes
     * the current input connection and sends it the emoji.
     *
     * @param primaryCode - The emojis unicode value
     * @param ic - The input connection for the char or symbol to be sent to.
     * by leon pearce
     */
    private void onEmojiKey(int primaryCode, InputConnection ic) {
        if (primaryCode <= -100) {
            ic.commitText(String.valueOf(Character.toChars(aaEmojiCodes.getEmojiCode(primaryCode))), 1);
        }
    }

    /**
     * This Method checks if the last character was an emoji or a regular character and handles
     * the correct deletion in the input connection.
     *
     * @param ic - The input connection to delete the last character from.
     * by leon pearce
     */
    private void onKeyBackspace(InputConnection ic) {
        String lastChar = (String) ic.getTextBeforeCursor(1, 0);
        if(!lastChar.equals("")){
            if (isValidCharacter(lastChar.charAt(0))) {
                ic.deleteSurroundingText(1, 0);
            } else {
                ic.deleteSurroundingText(2, 0);
            }
        }
    }

    /**
     * This method is used to release a keyboard from its shifted caps state.
     * by leon pearce
     */
    private void ReleaseShiftedCaps() {
        if(aaKeyboardView.getKeyboard() == alphaKeyboard){
            AlphaAaKeyboard currentKeyboard = alphaKeyboard;
            if(currentKeyboard.getWhichCaps() == AlphaAaKeyboard.SHIFTED_CAPS){
                aaKeyboardView.setShifted(false);
            }
        }
    }

    //HELPER METHODS

    /**
     * This method is used to validate if a character is an emoji or a regular character.
     * The method checks if it belongs to any of the characters on any of the keyboards
     * and returns true if this is the case.
     *
     * @param character - The character to be checked.
     * @return - true if it is a valid character.
     * by leon pearce
     */
    private boolean isValidCharacter(char character) {

        //Test if alpha key
        List<Keyboard.Key> keys = alphaKeyboard.getKeys();
        if (isCharOnKey(character, keys)) return true;

        //Test if alpha key shifted
        if (isCharOnKeyShifted(character, keys)) return true;

        //Test if symbol
        keys = symbolKeyboard.getKeys();
        if (isCharOnKey(character, keys)) return true;

        //Test if alt symbol
        keys = altSymbolKeyboard.getKeys();
        if (isCharOnKey(character, keys)) return true;

        //test if emoji
        keys = emojiKeyboard.getKeys();
        if(isCharOnEmojiKey(character, keys)) return true;


        return false;

    }

    /**
     * This method checks if a character is off an AaKeyboard that is shifted (not including emoji characters).
     *
     * @param character - The character in question.
     * @param keys - The keys of the keyboard to check from.
     *
     * @return - True if character is from keyboard.
     * by leon pearce
     */
    private boolean isCharOnKeyShifted(char character, List<Keyboard.Key> keys) {
        //todo change the ascii shift key to the correct value
        final int ASCII_SHIFT = -32;

        for (Keyboard.Key key : keys) {
            for (int code : key.codes) {
                if (character == code + ASCII_SHIFT)
                    return true;
            }
        }
        return false;
    }

    /**
     * This method checks if a character is off an AaKeyboard (not including emoji characters).
     *
     * @param character - The character in question.
     * @param keys - The keys of the keyboard to check from.
     *
     * @return - True if character is from keyboard.
     * by leon pearce
     */
    private boolean isCharOnKey(char character, List<Keyboard.Key> keys) {
        for (Keyboard.Key key : keys) {
            for (int code : key.codes) {
                if (character == code)
                    return true;
            }
        }
        return false;
    }

    /**
     * This method checks if a character is off an AaKeyboard (for emoji characters).
     *
     * @param character - The character in question.
     * @param keys - The keys of the keyboard to check from.
     *
     * @return - True if character is from keyboard.
     * by leon pearce
     */
    private boolean isCharOnEmojiKey(char character, List<Keyboard.Key> keys) {
        for (Keyboard.Key key : keys) {
            for (int code : key.codes) {
                if(character == aaEmojiCodes.getEmojiCode(code))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method sets the keyboard to the correct symbols.
     *
     * @param whichSymbols - the symbol state.
     * by leon pearce
     */
    private void changeSymbols(boolean whichSymbols) {
        if(whichSymbols){
            aaKeyboardView.setKeyboard(symbolKeyboard);
        }else{
            aaKeyboardView.setKeyboard(altSymbolKeyboard);
        }
    }


}