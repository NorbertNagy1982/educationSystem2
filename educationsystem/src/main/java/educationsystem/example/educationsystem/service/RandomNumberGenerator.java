package educationsystem.example.educationsystem.service;

import java.util.Random;

public class RandomNumberGenerator {

    static Random r = new Random();

    private RandomNumberGenerator(){};


    public static int rng(int min, int max){
        return r.nextInt((max - min) + 1) + min;
    }


}
