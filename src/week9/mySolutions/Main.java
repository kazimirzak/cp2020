package week9.mySolutions;



import java.io.IOException;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Week 9");
        try {
            StreamExercise1.main();
            //StreamExercise2.main();
            //StreamExercise3.main();
            //StreamExercise4.main();
            //StreamExercise5.main();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
