package week10.mySolution;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise2 {
    
    /*
    - Read and replicate the examples in: https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html
    */
    public static void main() {
        Person kenny = new Person("Kenny Brink", 42, "North Pole", "Santa Claus");
        System.out.println(kenny + "\n");
        kenny.invert();
        System.out.println(kenny + "\n");
        
        ImmutablePerson otherKenny = new ImmutablePerson("Kenny Brink", 
                                                         42,
                                                         "North Korea",
                                                         "Dictator");
        System.out.println(otherKenny + "\n");
        // Invert now returns a new object instead of changing 
        // the object that the method is called on.
        ImmutablePerson other = otherKenny.invert();
        System.out.println(other);
    }
    
    private static class Person {
        
        /*
        * This is my take on a class that is mutable and have to use synchronized
        * Everywhere.
        */
        
        private String name, address, profession;
        private int age;
        
        public Person(String name, int age, String address, String profession) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.profession = profession;
        }
        
        public synchronized void setName(String name) {
            this.name = name;
        }
        
        public synchronized void setAge(int age) {
            this.age = age;
        }
        
        public synchronized void setAddress(String address) {
            this.address = address;
        }
        
        public synchronized void setProfession(String profession) {
            this.profession = profession;
        }
        
        public synchronized void invert() {
            name = new StringBuilder(name).reverse().toString();
            address = new StringBuilder(address).reverse().toString();
            profession = new StringBuilder(profession).reverse().toString();
            age = -age;
        }
        
        @Override
        public String toString() {
            return "Name: " + name + "\nAge: " + age + 
                    "\nLives at: " + address + "\nTheir profession is: " + profession;
        }
    }
    
    private static class ImmutablePerson {
        
        /*
        * An immutable version of person.
        * Notice all methods is still thread-safe without having 
        * to synchronize on them. (Obviously we have removed all setters.)
        */
        
        private final String name, address, profession;
        private final int age;
        
        public ImmutablePerson(String name, int age, String address, String profession) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.profession = profession;
        }
        
        public ImmutablePerson invert() {
            String tempName = new StringBuilder(name).reverse().toString();
            String tempAddress = new StringBuilder(address).reverse().toString();
            String tempProfession = new StringBuilder(profession).reverse().toString();
            int tempAge = -age;
            return new ImmutablePerson(tempName, tempAge, tempAddress, tempProfession);
        }
        
        @Override
        public String toString() {
            return "Name: " + name + "\nAge: " + age + 
                    "\nLives at: " + address + "\nTheir profession is: " + profession;
        }
    }
}
