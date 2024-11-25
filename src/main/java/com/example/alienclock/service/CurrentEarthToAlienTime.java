import java.time.Instant;

public class CurrentEarthToAlienTime {

    // Constants
    private static final double ALIEN_SEC_TO_EARTH_SEC = 0.5;
    static final int TOTAL_ALIEN_DAY_IN_A_YEAR = 770;
    final static int ALIEN_YEAR_IN_1970 = 2804;
    final static long SECONDS_IN_AN_ALIEN_DAY = 36L * 90L * 90L;
    final static long SECONDS_IN_AN_ALIEN_YEAR = TOTAL_ALIEN_DAY_IN_A_YEAR * SECONDS_IN_AN_ALIEN_DAY;
    private static final int[] ALIEN_DAYS_IN_MONTH = {44, 42, 48, 40, 48, 44, 40, 44, 42, 40, 40, 42, 44, 48, 42, 40, 44, 38};
    static final long SYNC_ALIEN_SECONDS = calculateSyncAlienSeconds();

    // Instance Variables
    private long currentAlienSecond;
    private int alienYear;
    private int alienMonth;
    private int alienDay;
    private int alienHour;
    private int alienMinute;
    private int alienSecond;

    

    // Constructor
    public CurrentEarthToAlienTime() {
        long earthSeconds = getCurrentTotalEarthSeconds();
        this.currentAlienSecond = calculateAlienSecondsFromEarthSeconds(earthSeconds);

        // Calculate and store results
        this.alienYear = calculateAlienYear();
        long remainingSecondsAfterYear = remainingSecondsAfterYear();
        this.alienMonth = calculateAlienMonth(remainingSecondsAfterYear);
        long remainingSecondsAfterMonth = remainingSecondsAfterMonth(remainingSecondsAfterYear);
        this.alienDay = calculateAlienDay(remainingSecondsAfterMonth);
        long remainingSecondsAfterDay = remainingSecondsAfterDay(remainingSecondsAfterMonth);
        this.alienHour = calculateAlienHour(remainingSecondsAfterDay);
        this.alienMinute = calculateAlienMinute(remainingSecondsAfterDay);
        this.alienSecond = calculateAlienSecond(remainingSecondsAfterDay);
    }

    public static void main(String[] args) {
        
       
        CurrentEarthToAlienTime converter = new CurrentEarthToAlienTime();

        // Output Results
        System.out.printf("Final Alien Time: %d-%02d-%02d %02d:%02d:%02d\n",
                converter.getAlienYear(),
                converter.getAlienMonth(),
                converter.getAlienDay(),
                converter.getAlienHour(),
                converter.getAlienMinute(),
                converter.getAlienSecond());
    }

    // Getters for Alien Time Components
    public int getAlienYear() 
    {
        return alienYear;
    }

    public int getAlienMonth() 
    {
        return alienMonth;
    }

    public int getAlienDay() 
    {
        return alienDay;
    }

    public int getAlienHour() 
    {
        return alienHour;
    }

    public int getAlienMinute() 
    {
        return alienMinute;
    }

    public int getAlienSecond() 
    {
        return alienSecond;
    }

///
/// 

// use instant time api
     public long getCurrentTotalEarthSeconds()
    {

            long currentEarthSecond = 0;
            try
            {
            currentEarthSecond = Instant.now().getEpochSecond();
            
            //currentEarthSecond = Instant.parse("1971-01-01T06:00:00Z").getEpochSecond(); //testing
            System.out.println(currentEarthSecond); // testing
            
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return currentEarthSecond; 
    
    }


//Methods for calculation

    // Calculate Alien seconds from Earth seconds
    private long calculateAlienSecondsFromEarthSeconds(long earthSeconds) {
        long elapsedAlienSeconds = (long) (earthSeconds / ALIEN_SEC_TO_EARTH_SEC);
        return SYNC_ALIEN_SECONDS + elapsedAlienSeconds;
    }

    // Calculate Alien Year
    private int calculateAlienYear() {
        return ALIEN_YEAR_IN_1970 + (int) Math.ceil((double) currentAlienSecond / SECONDS_IN_AN_ALIEN_YEAR);
    }

    // Remaining Seconds After Year
    private long remainingSecondsAfterYear() {
        return currentAlienSecond % SECONDS_IN_AN_ALIEN_YEAR;
    }

    // Calculate Alien Month
    private int calculateAlienMonth(long remainingSeconds) 
    {
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

    // Remaining Seconds After Month
    private long remainingSecondsAfterMonth(long remainingSeconds) 
    {
        for (int days : ALIEN_DAYS_IN_MONTH) 
        {
        
            long secondsInMonth = days * SECONDS_IN_AN_ALIEN_DAY;
        
            if (remainingSeconds < secondsInMonth) {
        
                break;
        
            }
        
            remainingSeconds -= secondsInMonth;
        
        }
        
        return remainingSeconds;
    
    }

    
    // Calculate Alien Day
    
    private int calculateAlienDay(long remainingSeconds) 
    {
    
        return 1 + (int) (remainingSeconds / SECONDS_IN_AN_ALIEN_DAY);
    
    }

    
    // Remaining Seconds After Day
    
    private long remainingSecondsAfterDay(long remainingSeconds) {
    
        return remainingSeconds % SECONDS_IN_AN_ALIEN_DAY;
    }

    // Calculate Alien Hour
    
    private int calculateAlienHour(long remainingSeconds) {
    
        return (int) (remainingSeconds / 3600);
    
    }

    // Calculate Alien Minute
    
    private int calculateAlienMinute(long remainingSeconds) {
    
        return (int) ((remainingSeconds % 3600) / 90);
    
    }

    // Calculate Alien Second
    
    private int calculateAlienSecond(long remainingSeconds) {
    
        return (int) (remainingSeconds % 90);
    
    }






    // Sync Alien Seconds Calculation
    
    private static long calculateSyncAlienSeconds() {
    
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
