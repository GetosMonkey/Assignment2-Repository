import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to run my experiments
 * 
 * @author Maryam Abrahams
 * @version 1.0
 * @since 2025-03-23
 */

public class Experiment {

    // static variables 

    public static final int[] trial_sizes = {5, 50, 500, 1000, 2500, 5000, 7500, 10000, 25000, 50000}; 
    public static final int num_trials = 10; 
    public static String output_file = "";
    public static final String datasetFile = "/home/abrmar043/Assignment 2 - Project/textfiles/GenericsKB.txt"; 
    

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Please enter the name of the file you'd like to save your statistics to: "); 
        output_file = sc.nextLine() + ".txt"; 

        Parsefile fullDataset = new Parsefile(datasetFile); 

        List<String[]>  database = fullDataset.getDatabase(); 
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(output_file))){
            //bw.write("n,insert_min,insert_avg,insert_max,search_min,search_avg,search_max,theoretical_log2n\n"); 
            // reformatted header:

            bw.write(String.format("%-8s | %-12s | %-8s | %-12s | %-8s | %-12s | %-8s | %-12s | %-12s | %-8s | %-12s | %-8s | %-12s | %-8s | %-12s\n",
            "n", "Insert Min", "Count", "Insert Avg", "Count", "Insert Max", "Count", "Theoretical (i)",
            "Search Min", "Count", "Search Avg", "Count", "Search Max", "Count", "Theoretical (s)"));

                for (int n : trial_sizes){
                    runForSize(n, database, bw); 
                }

        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("experiment completed. Results saved to " + output_file); 
        
        sc.close(); 
    }

    // Running experiment: 

    public static void runForSize(int n, List<String[]> database, BufferedWriter bw) throws IOException {

        List<Integer> insertTotals = new ArrayList<>();
        List<Integer> searchTotals = new ArrayList<>();
        
        for (int trial = 0; trial < num_trials; trial++) {
            // Create random subset
            List<String[]> subset = getRandomSubset(database, n);
            
            // Run trial
            AVLTree tree = new AVLTree();
            tree.resetCounters();
            
            // Insert all data
            for (String[] entry : subset) {
                tree.insert(entry[0], entry[1], Double.parseDouble(entry[2]));
            }
            
            // Perform searches (use first 100 entries as queries)
            List<String[]> queries = subset.subList(0, Math.min(100, subset.size()));
            for (String[] query : queries) {
                tree.searchByKey(query[0]);
            }
            
            // Record results
            insertTotals.add(tree.getInsertComparisonCount());
            searchTotals.add(tree.getSearchComparisonCount()); 
        }
        
            // Calculating averages
            double avgInsert = insertTotals.stream().mapToInt(Integer::intValue).average().orElse(0) / n;
            double avgSearch = searchTotals.stream().mapToInt(Integer::intValue).average().orElse(0) / Math.min(100, n);
            double avgInsertCount = Math.round(Collections.min(insertTotals) + Collections.max(insertTotals)) /2; 
            double avgSearchCount = Math.round(Collections.min(searchTotals) + Collections.max(searchTotals)) /2; 
            
            // expecting a time-complexity of OlogN across the board
            double theoreticalS = Math.log(n) / Math.log(2);
            double theoreticalI = Math.log(n) / Math.log(2); 

            // reformatted information: n, mn, count, avg, count, mx, count, theoretical (repeat)
        
            bw.write(String.format("%-8d | %-12.2f | %-8d | %-12.2f | %-8d | %-12.2f | %-8d | %-15.2f | %-12.2f | %-8d | %-12.2f | %-8d | %-12.2f | %-8d | %-12.2f\n", 
            n,
            (float) Collections.min(insertTotals) / n,
                    Collections.min(insertTotals), 
            (float) avgInsert,
            (int)   avgInsertCount,  
            (float) Collections.max(insertTotals) / n,
                    Collections.max(insertTotals), 
            (float) theoreticalI,
            (float) Collections.min(searchTotals) / Math.min(100, n),
                    Collections.min(searchTotals), 
            (float) avgSearch,
            (int)   avgSearchCount,  
            (float) Collections.max(searchTotals) / Math.min(100, n),
                    Collections.max(searchTotals),  
            (float) theoreticalS));

             // testing:

            System.out.println("N: " + n + 
            " | Min Insert: " + Collections.min(insertTotals) + 
            " | Max Insert: " + Collections.max(insertTotals) + 
            " | Avg Insert: " + avgInsert +
            " | Min Insert: " + Collections.min(searchTotals) + 
            " | Max Insert: " + Collections.max(searchTotals) + 
            " | Avg Insert: " + avgSearch);

    }

    public static List<String[]> getRandomSubset(List<String[]> fullData, int n){
        List<String[]> copy = new ArrayList<>(fullData); 
        Collections.shuffle(copy, new Random(System.currentTimeMillis())); 
        return copy.subList(0, Math.min(n, copy.size())); 
    }
}
    
