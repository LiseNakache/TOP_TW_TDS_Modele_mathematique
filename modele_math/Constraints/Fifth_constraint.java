package modele_math.Constraints;


import java.util.ArrayList;
import modele_math.Instance.Vertex;

public class Fifth_constraint {

    // SOMME Vi * Yi_t_p - node 0 non compris
    public static String get_inner_constraint_service_time(ArrayList<Vertex> vertexs, Integer periods, Integer p){
        
        ArrayList<String> fifth_constraint_list_one = new ArrayList<>();

        for (int i = 1; i < vertexs.size(); i++){
            for (int t = 0; t < periods; t++){
                String first_part = vertexs.get(i).service_time + " " + "y" + i + "_" + t + "_" + p;        
                fifth_constraint_list_one.add(first_part);
            }
        }

        String fifth_constraint_one = String.join(" + ", fifth_constraint_list_one);
        return fifth_constraint_one;
    } 


    // SOMME Ti_j * Xi_j_t_p - i != j
    public static String get_inner_constraint_travel_time(ArrayList<Vertex> vertexs, Integer periods, Integer p, ArrayList<ArrayList<Integer>> distance_matrix){

        ArrayList<String> fifth_constraint_list_two = new ArrayList<>();

        for (int i = 0; i < vertexs.size(); i++){
            for (int j = 0; j < vertexs.size(); j++){
                if (i != j) {
                    for (int t = 0; t < periods; t++){
                        String second_part = distance_matrix.get(i).get(j) + " " + "x" + i + "_" + j + "_" + t + "_" + p;
                        fifth_constraint_list_two.add(second_part);
                    }
                }
            }
        }

       
        String fifth_constraint_two = String.join(" + ", fifth_constraint_list_two);
        return fifth_constraint_two;
    } 

}