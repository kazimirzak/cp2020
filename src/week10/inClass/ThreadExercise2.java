package week10.inClass;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadExercise2 {
    
    /*
    - Read and replicate the examples in: https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html
    */
    public static void main() {
        
    }
    
    private static class MutablePerson {
        
        private String name;
        private int age;
        
        public MutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public synchronized void set(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public synchronized void invert() {
            StringBuilder sb = new StringBuilder(name);
            name = sb.reverse().toString();
            this.age = -this.age;
        }
    }
    
    private static class ImmutablePerson {
        
        private final String name;
        private final int age;
        
        public ImmutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public ImmutablePerson invert() {
            StringBuilder sb = new StringBuilder(name);
            String tempName = sb.reverse().toString();
            int tempAge = -age;
            return new ImmutablePerson(tempName, tempAge);
        }
    }
}
