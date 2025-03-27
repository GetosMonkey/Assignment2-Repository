# Maryam Abrahams
# 27 March 2025 
# Graphing program 

import matplotlib.pyplot as plt

def load_data(filename): 

    n_values = []

    insert_min = []
    min_count_i = []
    insert_avg = []
    avg_count_i = []
    insert_max = []
    max_count_i = []
    insert_theo = []

    search_min = []
    min_count_s = []
    search_avg = []
    avg_count_s = []
    search_max = []
    max_count_s = []
    search_theo = []

    with open(filename, 'r') as file: 

        header = file.readline()

        # Parsing each line
        for line in file: 
            parts = [x.strip() for x in line. split ("|")]
            n_values.append(int(parts[0]))

            insert_min.append(float(parts[0]))
            min_count_i.append(int(parts[1]))
            insert_avg.append(float(parts[2]))
            avg_count_i.append(int(parts[3]))
            insert_max.append(float(parts[4]))
            max_count_i.append(int(parts[5]))
            insert_theo.append(float(parts[6]))

            search_min.append(float(parts[7]))
            min_count_s.append(int(parts[8]))
            search_avg.append(float(parts[9]))
            avg_count_s.append(int(parts[10]))
            search_max.append(float(parts[11]))
            max_count_s.append(int(parts[12]))
            search_theo.append(float(parts[13]))

            return n_values, insert_min, min_count_i, insert_avg, avg_count_i, insert_max , max_count_i , insert_theo , search_min , min_count_s , search_avg , avg_count_s, search_max , max_count_s, search_theo


def plot_data(n_values, insert_min, min_count_i, insert_avg, avg_count_i, insert_max , max_count_i , insert_theo , search_min , min_count_s , search_avg , avg_count_s, search_max , max_count_s, search_theo): 
    
    # Plot Insert Min, Avg, Max
    plt.figure(figsize=(10, 6))
    plt.plot(n_values, insert_min, label="Insert Min", marker='o')
    plt.plot(n_values, insert_avg, label="Insert Avg", marker='o')
    plt.plot(n_values, insert_max, label="Insert Max", marker='o')
    plt.plot(n_values, insert_theo, label = "Theoretical", marker= 'o')
    plt.xlabel('n (Size)')
    plt.ylabel('Insert Comparison Count')
    plt.title('Insert Comparison Averages vs n')
    plt.legend()
    plt.grid(True)
    plt.show()

    # Plot Search Min, Avg, Max
    plt.figure(figsize=(10, 6))
    plt.plot(n_values, search_min, label="Search Min", marker='o')
    plt.plot(n_values, search_avg, label="Search Avg", marker='o')
    plt.plot(n_values, search_max, label="Search Max", marker='o')
    plt.plot(n_values, search_theo, label = "Theoretical", marker= 'o')
    plt.xlabel('n (Size)')
    plt.ylabel('Search Comparison Count')
    plt.title('Search Comparison Averages vs n')
    plt.legend()
    plt.grid(True)
    plt.show()

    # Plot Insert counts 

    plt.figure(figsize=(10, 6))
    plt.plot(n_values, min_count_i, label="Insert Min", marker='o')
    plt.plot(n_values, avg_count_i , label="Insert Avg", marker='o')
    plt.plot(n_values, max_count_i, label="Insert Max", marker='o')
    plt.xlabel('n (Size)')
    plt.ylabel('Insert Comparison Count')
    plt.title('Insert Comparison Counts vs n')
    plt.legend()
    plt.grid(True)
    plt.show()

    # Plot Search counts 

    plt.figure(figsize=(10, 6))
    plt.plot(n_values, min_count_s, label="Search Min", marker='o')
    plt.plot(n_values, avg_count_s, label="Search Avg", marker='o')
    plt.plot(n_values, max_count_s, label="Search Max", marker='o')
    plt.xlabel('n (Size)')
    plt.ylabel('Search Comparison Count')
    plt.title('Search Comparison Counts vs n')
    plt.legend()
    plt.grid(True)
    plt.show()

    def main(): 

        filename ='/home/abrmar043/Assignment 2 - Project/textfiles/results.txt'
        n_values, insert_min, min_count_i, insert_avg, avg_count_i, insert_max , max_count_i , insert_theo , search_min , min_count_s , search_avg , avg_count_s, search_max , max_count_s, search_theo = load_data(filename)

        # Plotting the data

        plot_data(n_values, insert_min, min_count_i, insert_avg, avg_count_i, insert_max , max_count_i , insert_theo , search_min , min_count_s , search_avg , avg_count_s, search_max , max_count_s, search_theo)

        if __name__ == "__main__": 
            main()

