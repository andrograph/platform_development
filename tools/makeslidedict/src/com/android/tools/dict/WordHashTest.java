package com.android.tools.dict;


public class WordHashTest {
    
    public static void main(String[]args){
        new WordHashTest().testResampling();
    }
    
    public void testResampling() {
        double[]y = {15.0,15.0};
        double[]t = {1.0};
        double[]ry = WordHash.resample(y, t);
        
        WordHash.pr(ry);
    }

}
