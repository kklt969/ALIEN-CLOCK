import java.util.*;
import java.time.*;
import java.io.*;


public class timeconversion
{

    private static final double  ALIEN_SEC_TO_EARTH_SEC = 0.5 ;
    
    static final int TOTAL_ALIEN_DAY_IN_A_YEAR = 770;
    final static int ALIEN_YEAR_IN_1970 = 2804;
    final static long SECONDS_IN_AN_ALIEN_DAY = 36L * 90L * 90L; 
    final static long SECONDS_IN_AN_ALIEN_YEAR = 770 * SECONDS_IN_AN_ALIEN_DAY;
    private static final int[] ALIEN_DAYS_IN_MONTH = {44, 42, 48, 40, 48, 44, 40, 44, 42, 40, 40, 42, 44, 48, 42, 40, 44, 38}; //Days per month
       // Sync point for Alien seconds on 1970-01-01T00:00:00 UTC
       static final long SYNC_ALIEN_SECONDS = calculateSyncAlienSeconds();

    public static void main(String args[])
            {
                
        long currentAlienSecond = currentTotalAlienSeconds();

        System.out.println((31320000%SECONDS_IN_AN_ALIEN_YEAR));
                // Test: Calculate Alien Year
        int alienYear = calculateAlienYear(currentAlienSecond);

        System.out.println("Alien Year: " + alienYear);

        // Test: Remaining Seconds After Year
        long remainingSecondsAfterYear = remainingSecondsAfterYear(currentAlienSecond);

        System.out.println("Remaining Seconds After Year: " + remainingSecondsAfterYear);

        // Test: Calculate Alien Month
        
        int alienMonth = calculateAlienMonth(remainingSecondsAfterYear);
        
        System.out.println("Alien Month: " + alienMonth);


        // Test: Remaining Seconds After Month
        
        long remainingSecondsAfterMonth = remainingSecondsAfterMonth(remainingSecondsAfterYear);
        
        System.out.println("Remaining Seconds After Month: " + remainingSecondsAfterMonth);

        // Test: Calculate Alien Day
        int alienDay = calculateAlienDay(remainingSecondsAfterMonth);
        System.out.println("Alien Day: " + alienDay);

        // Test: Remaining Seconds After Day
        long remainingSecondsAfterDay = remainingSecondsAfterDay(remainingSecondsAfterMonth);
        System.out.println("Remaining Seconds After Day: " + remainingSecondsAfterDay);

        // Test: Calculate Alien Hour
        int alienHour = calculateAlienHour(remainingSecondsAfterDay);
        System.out.println("Alien Hour: " + alienHour);

        // Test: Calculate Alien Minute
        int alienMinute = calculateAlienMinute(remainingSecondsAfterDay);
        System.out.println("Alien Minute: " + alienMinute);

        // Test: Calculate Alien Second
        int alienSecond = calculateAlienSecond(remainingSecondsAfterDay);
        System.out.println("Alien Second: " + alienSecond);

        // Final Output: Full Alien Time
        System.out.printf("Final Alien Time: %d-%02d-%02d %02d:%02d:%02d\n",
                alienYear, alienMonth, alienDay, alienHour, alienMinute, alienSecond);
            }
            
            
    public static long getCurrentTotalEarthSeconds()
        {

                long currentEarthSecond = 0;
                try
                {
                //currentEarthSecond = Instant.now().getEpochSecond();
                
                currentEarthSecond = Instant.parse("1971-01-01T06:00:00Z").getEpochSecond(); //testing
                System.out.println(currentEarthSecond);
                
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                return currentEarthSecond; 
        
        }

    
    public static long currentTotalAlienSeconds()
    {
                // Add sync Alien seconds to the Alien seconds derived from Earth time
        long earthSecondsSince1970 = getCurrentTotalEarthSeconds();
        long elapsedAlienSeconds = (long) (earthSecondsSince1970 / ALIEN_SEC_TO_EARTH_SEC);
        return SYNC_ALIEN_SECONDS + elapsedAlienSeconds;
    }
            
            
    public static int calculateAlienYear(long totalAlienSeconds)
    { 
                System.out.println(totalAlienSeconds);
                return ALIEN_YEAR_IN_1970 + (int) Math.ceil((double) totalAlienSeconds / SECONDS_IN_AN_ALIEN_YEAR);

    }
        
    
    public static long remainingSecondsAfterYear(long totalAlienSeconds) {
                //return 145800;
                return totalAlienSeconds % ( SECONDS_IN_AN_ALIEN_YEAR);
    }
    
    public static int calculateAlienMonth(long remainingSeconds) {
            int alienMonth = 1;
            for (int days : ALIEN_DAYS_IN_MONTH) {
                 long secondsInMonth = days * SECONDS_IN_AN_ALIEN_DAY;
                    if (remainingSeconds < secondsInMonth) {
                        break;
                    }
                    remainingSeconds -= secondsInMonth;
                    alienMonth++;
                }
                return alienMonth;
            }
 
            public static long remainingSecondsAfterMonth(long remainingSeconds) {
                for (int days : ALIEN_DAYS_IN_MONTH) {
                    long secondsInMonth = days * SECONDS_IN_AN_ALIEN_DAY;
                    if (remainingSeconds < secondsInMonth) {
                        break;
                    }
                    remainingSeconds -= secondsInMonth;
                }
                return remainingSeconds;
            }
            
            // Function to calculate Alien day
            public static int calculateAlienDay(long remainingSeconds) {
                return 1 + (int) (remainingSeconds / SECONDS_IN_AN_ALIEN_DAY);
            }
            
            // Function to calculate remaining seconds after determining the day
            public static long remainingSecondsAfterDay(long remainingSeconds) {
                return remainingSeconds % SECONDS_IN_AN_ALIEN_DAY;
            }
            
            // Function to calculate Alien hour
            public static int calculateAlienHour(long remainingSeconds) {
                return (int) (remainingSeconds / 3600);
            }
            
            // Function to calculate Alien minute
            public static int calculateAlienMinute(long remainingSeconds) {
                return (int) ((remainingSeconds % 3600) / 90);
            }
            
            // Function to calculate Alien second
            public static int calculateAlienSecond(long remainingSeconds) {
                return (int) (remainingSeconds % 90);
            }

    

            public static long calculateSyncAlienSeconds() {
                int syncYear = 2804;
                int syncMonth = 18;
                int syncDay = 31;
                int syncHour = 2;
                int syncMinute = 2;
                int syncSecond = 88;
        
                long syncSeconds = (syncYear - 1) * SECONDS_IN_AN_ALIEN_YEAR;
        
                for (int i = 0; i < syncMonth - 1; i++) {
                    syncSeconds += ALIEN_DAYS_IN_MONTH[i] * SECONDS_IN_AN_ALIEN_DAY;
                }
        
                syncSeconds += (syncDay - 1) * SECONDS_IN_AN_ALIEN_DAY;
                syncSeconds += syncHour * (90L * 90L);
                syncSeconds += syncMinute * 90L;
                syncSeconds += syncSecond;
        
                return syncSeconds;
            }





   
}



