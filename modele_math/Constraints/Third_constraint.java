package modele_math.Constraints;

import java.util.ArrayList;
import modele_math.Instance.Vertex;

public class Third_constraint {

    public static String[] get_x_constraint(ArrayList<Vertex> vertexs, Integer k, Integer periods, Integer p) {

        String third_constraint_x_i_k = ""; // this line is to create a new equation for each k
        String third_constraint_x_k_j = ""; // this line is to create a new equation for each k

        ArrayList<String> third_constraint_list_x_i_k = new ArrayList<>(); // this line is to create a new list for each k
        ArrayList<String> third_constraint_list_x_k_j = new ArrayList<>(); // this line is to create a new equation for each k

        for (int i = 0; i < vertexs.size(); i++){ //ici on boucle sur i, avec le meme k
            if (i != k) {
                for (int t = 0; t < periods; t++){
                    String x_i_k = "x" + i + "_" + k + "_" + t + "_" + p;
                    String x_k_j = "x" + k + "_" + i + "_" + t + "_" + p;
                    third_constraint_list_x_i_k.add(x_i_k);
                    third_constraint_list_x_k_j.add(x_k_j);
                }
            }
        } 
        third_constraint_x_i_k += String.join(" + ", third_constraint_list_x_i_k); //  Xi_k_t_p
        third_constraint_x_k_j += String.join(" + ", third_constraint_list_x_k_j); //  Xk_j_t_p

        return new String[] {third_constraint_x_i_k, third_constraint_x_k_j};
    }

    public static String get_y_constraint(Integer k, Integer periods, Integer p) {
        ArrayList<String> third_constraint_list_y = new ArrayList<>();
        String third_constraint_y = ""; // this line is to create a new equation for each k

        for (int t = 0; t < periods; t++){
            String y = "y" + k + "_" + t + "_" + p;
            third_constraint_list_y.add(y);
        }

        third_constraint_y += String.join(" - ", third_constraint_list_y);  //  Yk_t_p
        return third_constraint_y;
    }
}