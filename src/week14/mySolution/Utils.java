package week14.mySolution;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */

public class Utils
{
	public static void doAndMeasure( Runnable runnable )
	{
		long t1 = System.currentTimeMillis();
		runnable.run();
		long t2 = System.currentTimeMillis();
		System.out.println( "Elapsed time: " + (t2-t1) + "ms" );	
	}
	
	public static void benchmark( Runnable runnable )
	{
		runnable.run();
		long t1 = System.currentTimeMillis();
		runnable.run();
		long t2 = System.currentTimeMillis();
		System.out.println( "Elapsed time: " + (t2-t1) + "ms" );	
	}
	
	public static void repeat( int times, Runnable runnable )
	{
		for( int i = 0; i < times; i++ ) {
			runnable.run();
		}
	}
}
