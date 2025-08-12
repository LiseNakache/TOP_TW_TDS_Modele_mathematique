package modele_math.Instance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Instance_TOPTW_TDS {
    public ArrayList<Vertex> vertexs = new ArrayList<>(); //list of the points
    public ArrayList<ArrayList<Integer>> distance_matrix = new ArrayList<>(); //matrix of euclidian distances
    public ArrayList<Period> time_periods = new ArrayList<>();

    public void read_file_points(String name_file){
        int lineNumber = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(name_file));

            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;

                line = line.replaceAll("\t", " ");

                if (lineNumber > 2 && lineNumber <= 6) {
                    String[] line_info = line.split(" ");
                    Period period = new Period(lineNumber-3, Integer.valueOf(line_info[0]), Integer.valueOf(line_info[1]));
                    time_periods.add(period);
                }

                if (lineNumber > 6) {
                    String[] line_info = line.split(" ");
                    ArrayList<Integer> vertex_info = new ArrayList<>();
                    ArrayList<Double> recommandation_factors = new ArrayList<>();

                    for (int i = 0; i < line_info.length; i++) {
                        if (i >= 0 && i <= 6) {
                            vertex_info.add(Integer.valueOf(line_info[i]));
                        } else {
                            recommandation_factors.add(Double.valueOf(line_info[i]));
                        } 
                    }

                    // Get the information
                    Integer node_i = vertex_info.get(0);
                    Integer x_coordinate = vertex_info.get(1);
                    Integer y_coordinate = vertex_info.get(2);
                    Integer max_score_visiting_i = vertex_info.get(3);
                    Integer opening_time_window = vertex_info.get(4);
                    Integer closing_time_window = vertex_info.get(5);
                    Integer service_time_duration = vertex_info.get(6);

                    Vertex vertex = new Vertex(node_i, x_coordinate, y_coordinate, max_score_visiting_i, opening_time_window, closing_time_window, service_time_duration, recommandation_factors);
                    this.vertexs.add(vertex); 
                }  
            }

            br.close();

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error in the file: ");
            System.out.println(e);
        }

        this.check_distances();
    }
    
    //fill the matrix of distances
    public void check_distances(){
        
        /*
         * Use euclidian distance for the matrix
         */
       
        
        for (int i = 0; i < vertexs.size(); i++) {
            Vertex node_i = vertexs.get(i);
             ArrayList<Integer> inner = new ArrayList<>();
            for (int j = 0; j < vertexs.size(); j++) {
                Vertex node_j = vertexs.get(j);
                if (i == j) {
                    inner.add(0);
                } else {
                    Integer distance = (int) Math.ceil(Math.sqrt(Math.pow(node_j.x - node_i.x, 2) + Math.pow(node_j.y - node_i.y, 2)));
                    inner.add(distance);
                }  
            }
            distance_matrix.add(inner);
        }
        // this.show_matrix_distances(distance_matrix); //optional, used just to test
    }  
    
    //display the matrix of distances
    public void show_matrix_distances(ArrayList<ArrayList<Integer>> distance_matrix){
        for (ArrayList<Integer> row : distance_matrix) {
            for (Integer value : row) {
                System.out.println(value);
                // System.out.printf("%6.2f ", value);  // formatted to 2 decimal places, right-aligned
            }
        } 
    }
}


