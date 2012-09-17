/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datagenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Robertas
 */
public class File {

    private String fileName = "";

    public File(String name){
        this.fileName = name;
    }

    public void write(List data){
        try {
            FileWriter fstream = new FileWriter(this.fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            Iterator iterator = data.iterator();
            while(iterator.hasNext()){
                out.write(iterator.next().toString() + "\n");
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
