/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datagenerator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Robertas
 */
public class TimestampGenerator implements Generator {

    //private String chipnumber = "";
    //private String timestamp = "";
    //private String originalStr = "";
    private int chipFrom = 0;
    private int chipTo = 0;
    private int numberOfChips = 0;
    private Date timeFrom;
    private Date timeTo;
    private int timestampsPerChipFrom = 0;
    private int timestampsPerChipTo = 0;

    public TimestampGenerator(int chipFrom, int chipTo, int tmPerChipFrom, int tmPerChipTo, String timeFrom_, String timeTo_) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.chipFrom = chipFrom;
        this.chipTo = chipTo;
        this.numberOfChips = this.chipTo - this.chipFrom + 1;
        this.timestampsPerChipFrom = tmPerChipFrom;
        this.timestampsPerChipTo = tmPerChipTo;

        this.timeFrom = df.parse(timeFrom_);
        this.timeTo = df.parse(timeTo_);
    }


    public List generate() {
        long[] timestamps;
        List lines = new ArrayList();

        int maxNumberOfTimestampsPerChip = this.timestampsPerChipTo + 1;
        int arraySize = (this.numberOfChips) * (maxNumberOfTimestampsPerChip);
        
        int last = 0;
        int j = 0;

        // we may have double keys. We need just to iterate through, however.
        Map<Long, Integer> data = new HashMap<Long, Integer>();
        timestamps = this.makeArrayOfTimestamps(arraySize);
        
        for(int i = this.chipFrom; i < this.chipFrom + this.numberOfChips; i++) {
            int numberOfTimestamps = new Random().nextInt(this.timestampsPerChipTo - this.timestampsPerChipFrom + 1) + this.timestampsPerChipFrom;
            for(j = last; j  < last + numberOfTimestamps; j++) {
                data.put(timestamps[j], i);
            }
            last = j;
        }

        // sort map by the key - seconds
        // add to lines array
        Map<Long,Integer> sortedMap =  this.sortByComparator(data);
        for (Map.Entry<Long, Integer> item : sortedMap.entrySet()) {
          Long key = item.getKey();
          Integer value = item.getValue();
          lines.add(this.makeLine(key, value));
        }
        return lines;
    }

    private String addZerosToLeft(int number) {
        String ln = "";
        int remainder = 0;

        ln = Integer.toString(number);
        remainder = 6 - ln.length();

        if (remainder > 0) {
            for (int i = 0; i < remainder; i++) {
                ln = "0" + ln;
            }
        }
        return ln;
    }

    private String toTime(long milliseconds){
        Date fullTime = new Date(milliseconds);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.S");
        String time = new StringBuilder( timeFormat.format( fullTime ) ).toString();
        if(time.length() >= 11){
            time = time.substring(0, 10);
        } else if(time.length() == 8){
            time = time + ".0";
        }

        return time;
    }

    private long[] makeArrayOfTimestamps(int numberOfTimestamps){
        long startTime = this.timeFrom.getTime();
        long endTime = this.timeTo.getTime();
        long timeDiff = (endTime - startTime);
        long times[] = new long[numberOfTimestamps];
        Random r = new Random();
        for(int j = 0; j < numberOfTimestamps; j++){
            long randomTime = (long)(r.nextDouble() * timeDiff) + startTime;
            times[j] = randomTime;
        }
        return times;
    }

    private Integer[] makeArrayOfChips(){
        int numberOfChips = this.chipTo - this.chipFrom + 1;
        Integer[] chipArr = new Integer[numberOfChips];
        for(int i = 0; i < numberOfChips; i++){
            chipArr[i] = this.chipFrom + i;
        }
        return chipArr;
    }

    private String makeLine(long timestamp, int chip){
        String formatedTime = this.toTime(timestamp);
        return (addZerosToLeft(chip) + " " + formatedTime + " " + "S0"+addZerosToLeft(chip)+"L0013030");
    }

    private Map sortByComparator(Map data) {
        List list = new LinkedList(data.entrySet());

        //sort list based on comparator
        Collections.sort(list, new Comparator() {
             public int compare(Object o1, Object o2) {
	           return ((Comparable) ((Map.Entry) (o1)).getKey())
	           .compareTo(((Map.Entry) (o2)).getKey());
             }
	});

        //put sorted list into map again
	Map sortedMap = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	     Map.Entry entry = (Map.Entry)it.next();
	     sortedMap.put(entry.getKey(), entry.getValue());
	}
	return sortedMap;
    }
}
