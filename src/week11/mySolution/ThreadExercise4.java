package week11.mySolution;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise4 {
    
    /*
    - Write the example from Listing 4.2 in the book.
    - Add a method that returns a reference to the internal field mySet.
    - Use the new method from concurrent threads to create unsafe access to mySet.
    */
    
    public static void main() {
        // We create the set and create two different threads
        // that both tries to add a person *iterations* times
        // We can then check once both threads finish if we have 
        // iterations * 2 number of items in the set. 
        // If we dont we have introduced race-conditions to the set
        // even though the implementation of PersonSet should be thread-safe?
        PersonSet mySet = new PersonSet();
        final long iterations = 1_000;
        Thread t1 = new Thread(() -> {
            Set<Person> set = mySet.getSet();
            for(int i = 0; i < iterations; i++) {
                set.add(new Person("Barack Obama", i));
            }
        });
        Thread t2 = new Thread(()-> {
           Set<Person> set = mySet.getSet();
           for(int i = 0; i < iterations; i++) {
               set.add(new Person("Oprah Winfrey", i));
           }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(mySet.getSet().size() != iterations * 2) {
            System.out.println("Race-conditions! " + mySet.getSet().size() + " != " + (iterations * 2));
        } else {
            System.out.println("No raceconditions! succesfully added " + (iterations * 2) + " items");
        }
    }
    
    /*
    * A simple implementation of a class that represents a person.
    */
    private static class Person {
        private final String name;
        private final int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
    
    /*
    * The listing 4.2 in the book with the new method added.
    * It is never a good idea to let a variable escape like this.
    * If this is done we can not control the access to set the set anymore
    * and the user might introduce race-conditions / use the set without being thread-safe.
    */
    
    private static class PersonSet {
        private final Set<Person> mySet = new HashSet<>();
        
        public synchronized void addPerson(Person p) {
            mySet.add(p);
        }
        
        public synchronized boolean containsPerson(Person p) {
            return mySet.contains(p);
        }
        
        public synchronized Set<Person> getSet() {
            return mySet;
        }
    }
}
