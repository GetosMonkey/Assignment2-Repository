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
    public static final String output_file = "results"; 

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in); 
        System.out.println("Please enter the directory to your full dataset:");
        String datasetFile = sc.nextLine(); 

        Parsefile fullDataset = new Parsefile(datasetFile); 

        List<String[]>  database = fullDataset.getDatabase(); 
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(datasetFile))){
            bw.write("n,insert_min,insert_avg,insert_max,search_min,search_avg,search_max,theoretical_log2n\n"); 
                
                for (int n : trial_sizes){
                    runForSize(n, database, bw); 
                }

        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("experiment completed. Results saved to " + output_file); 
        sc.close(); 
    }

    // running experiment: 

    private static void runForSize(int n, List<String[]> database, BufferedWriter bw) throws IOException {
        List<Integer> insertCounts = new ArrayList<>();
        List<Integer> searchCounts = new ArrayList<>();
        
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
            insertCounts.add(tree.getInsertComparisonCount() / n); // Avg per insertion
            searchCounts.add(tree.getSearchComparisonCount() / queries.size()); // Avg per search
        }
        
        // Calculate statistics
        double theoretical = Math.log(n) / Math.log(2);
        bw.write(String.format("%d,%d,%.2f,%d,%d,%.2f,%d,%.2f\n",
            n,
            Collections.min(insertCounts),
            insertCounts.stream().mapToInt(Integer::intValue).average().orElse(0),
            Collections.max(insertCounts),
            Collections.min(searchCounts),
            searchCounts.stream().mapToInt(Integer::intValue).average().orElse(0),
            Collections.max(searchCounts),
            theoretical));
    }

    public static List<String[]> getRandomSubset(List<String[]> fullData, int n){
        List<String[]> copy = new ArrayList<>(fullData); 
        Collections.shuffle(copy, new Random(System.currentTimeMillis())); 
        return copy.subList(0, Math.min(n, copy.size())); 
    }
}
    

