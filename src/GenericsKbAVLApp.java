import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The GenericsKbAVLApp class is responsible for managing a knowledge base 
 * using an AVL Tree. It provides functionality to load data from a file, 
 * store it in a balanced tree, and retrieve statements based on key searches.
 * 
 * Features:
 * - Load knowledge base from a file and store it in an AVL Tree.
 * - Automatically balances the tree for efficient search operations.
 * - Search for terms and retrieve associated statements.
 * - Parse a list of keys and output corresponding statements to a file.
 * 
 * @author Maryam Abrahams
 * @version 1.0
 * @since 2025-03-23
 */


 
public class GenericsKbAVLApp{ 

        AVLTree tree = new AVLTree(); 
        int x = 0; 
        Parsefile file; 
        public List<String> keys = new ArrayList<>();; 

        
        // Constructor

        public GenericsKbAVLApp(){}
//___________________________________________________________________________________

        // 1) Loading in a knowledge base:

        public void setFile(Parsefile path){
            file = path; 
        }

        public void convertToTree(Parsefile path){

            if (file == null){
                System.out.println("Please first load a knowledge base. \n");
                return; }

            
            List<String[]> list = path.getDatabase(); 

            //inserting into my tree 
            for (String[] i : list){
                String key = i[0]; 
                String sentence = i[1]; 
                double CS = Double.parseDouble(i[2]); 

                tree.insert(key, sentence, CS); 

            }

            System.out.println("Knowledge base successfully loaded."); 
        }

//___________________________________________________________________________________

        // 2) Parsing and printing our file: 

        public void createFile(String keyfile){

            if (tree.isEmpty()){
                System.out.println("The database is empty. Please populate it first.");
                return;
            }

                // Method to parse the file and populate the database

                    String line = "";

                    try{

                        try (BufferedReader br = new BufferedReader(new FileReader(keyfile))) {

                            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt")); 

                            while((line = br.readLine()) != null){

                                // Search and output implementation:

                                Node tuple = tree.searchByKey(line); 

                                if (tuple != null) {

                                    bw.write(tuple.key + ": " + tuple.sentence + "(" + tuple.confidenceScore + ")");
                                    System.out.println(tuple.key + ": " + tuple.sentence + "(" + tuple.confidenceScore + ")"); 

                                } else { 
                                    
                                    bw.write("Term not found: " + line);}
                                    System.out.println("Term not found: " + line); 

                                bw.newLine(); 
                                //__________________________________
                            }
                            bw.close(); 
                        }

                    } catch (FileNotFoundException e){ 
                        e.printStackTrace();
                    } catch (IOException e){ 
                        e.printStackTrace();
                    }
        }

//___________________________________________________________________________________
   
        public static void main(String [] args){

            int choice = 0; 
            Scanner sc = new Scanner(System.in); 
            GenericsKbAVLApp main = new GenericsKbAVLApp(); 

            System.out.println("Welcome to GenericsKb :)"); 

            while (choice != 3) {
                
                //updates if the confidence score is higher than an existing one 
                System.out.println("""

                        Choose an action from the menu:
                        1. Load a knowledge base from a file.
                        2. Search for terms in the file and print to an output file.
                        3. Quit
    
                        Enter your choice: 
                        """);
    
                        choice = sc.nextInt();
                        sc.nextLine(); //fixing future sc bugs by consuming trailing \n 
    
                    switch(choice){

                    case 1:

                        System.out.println("Enter the name of the file directory: "); 
                        String filename = sc.nextLine(); 
                        Parsefile path = new Parsefile(filename); 
                        main.setFile(path); 

                        main.convertToTree(path); 
                        
                        break; 

                    case 2:

                        String keyfile = ""; 
                        System.out.println("Please enter the name of the file of list of keys you want to parse: "); 
                        keyfile = sc.nextLine();

                        main.createFile(keyfile); 
                        
                        break;

                }}

                System.out.print("\nThanks for Using GenericsKb :)");
                sc.close();

        }

}