/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datagenerator;

import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Robertas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        int task = 1;
        TimestampGenerator generator = null;
        switch(task){
            case 1:
                int chipFrom = 21;
                int chipTo = 21;
                int tmPerChipFrom = 100;
                int tmPerChipTo = 100;
                String dateFrom = "2012-09-12 10:00:00";
                String dateTo = "2012-09-12 10:59:00";

                generator = new TimestampGenerator(chipFrom, chipTo, tmPerChipFrom, tmPerChipTo, dateFrom, dateTo);
            break;
        }
        
        List data = generator.generate();
        File f = new File("start1.txt");
        f.write(data);
    }
}
