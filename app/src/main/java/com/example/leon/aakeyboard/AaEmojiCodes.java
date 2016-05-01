package com.example.leon.aakeyboard;

/**
 * Created by leon on 12/06/2015.
 */
public class AaEmojiCodes {

    private int mArraySize;

    //todo
    int[] emojiCodes = {
            //ROW 1
            0x263a, 0x1F611, 0x1F61B, 0x1F600, 0x1F602, 0x1F618, 0x1F634, 0x1F637, 0x1F636, 0x1F64C,
            //ROW 2
            0x1F697, 0x1F6B2, 0x2601, 0x2614, 0x26C4, 0x26BE, 0x26F3, 0x26FA, 0x1F30A, 0x1F308,
            //ROW 3
            0x1F31B, 0x1F314, 0x1F320, 0x1F334, 0x1F335, 0x1F33B, 0x1F342, 0x1F33D, 0x1F344, 0x1F345,
            //ROW 4
            0x1F346, 0x1F347, 0x1F349, 0x1F34C, 0x1F353, 0x1F355, 0x1F354, 0x1F35F, 0x1F356, 0x1F363
    };

    public AaEmojiCodes(){
        mArraySize = emojiCodes.length;
    }

    //todo
    public int getEmojiCode(int code){

        //muliply by -1 to convert code to positive
        code *= -1;
        //subtract 100 to find emoji code
        code -= 100;

        if(code >= 0 && code < mArraySize){
            return emojiCodes[code];
        }else{
            return -1;
        }

    }
}
