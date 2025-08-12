package modele_math.Constraints;

import java.util.ArrayList;
import modele_math.Instance.Instance_TOPTW_TDS;
import modele_math.Instance.Period;
import modele_math.Instance.Vertex;

public class Constraints {

    Instance_TOPTW_TDS instance_TOPTW_TDS = null;    
    Integer vehicles = null;
    Integer periods = null;
    ArrayList<Vertex> vertexs = null;
    
    public Constraints(Instance_TOPTW_TDS _instance, Integer _vehicles, Integer _periods) {
        instance_TOPTW_TDS = _instance;
        vehicles = _vehicles;
        periods = _periods;
        vertexs = instance_TOPTW_TDS.vertexs;
    }

    // SOMME Si * Fi_t * Yi_t_p - node 0 non compris
    public String get_objective_func() {
        ArrayList<String> objective_func_list = new ArrayList<>();

        for (int p = 1; p <= vehicles; p++){
            for (int i = 1; i < vertexs.size(); i++){
                for (int t = 0; t < periods; t++){
                    Double risk_per_point = vertexs.get(i).max_score * vertexs.get(i).recommendation_factors.get(t);
                    String line =  String.format("%.2f", risk_per_point) + " " + "y" + i + "_" + t + "_" + p;
                    objective_func_list.add(line);                    
                }
            }
        }

        String objective_func = String.join(" + ", objective_func_list);
        return objective_func;
    }

    public String get_sec_constraint() {
        String second_constraint = "";

        for (int p = 1; p <= vehicles; p++){

            ArrayList<String> second_constraint_list_x_1_j = new ArrayList<>();
            ArrayList<String> second_constraint_list_x_i_1 = new ArrayList<>();

            for (int j = 1; j < vertexs.size(); j++){
                for (int t = 0; t < periods; t++){
                    String x_0_j = "x" + 0 + "_" + j + "_" + t + "_" + p;
                    String x_i_0 = "x" + j + "_" + 0 + "_" + t + "_" + p;
                    second_constraint_list_x_1_j.add(x_0_j);
                    second_constraint_list_x_i_1.add(x_i_0);
                }
            }

            String second_constraint_x_1_j = String.join(" + ", second_constraint_list_x_1_j) + " = " + 1;
            String second_constraint_x_i_1 = String.join(" + ", second_constraint_list_x_i_1) + " = " + 1;

            second_constraint += second_constraint_x_1_j + "\n" + second_constraint_x_i_1 + "\n";
        } 

        return second_constraint;
    }

    public String get_third_constraint() {
        String third_constraint = "";

        for (int p = 1; p <= vehicles; p++){

            for (int k = 1; k < vertexs.size(); k++){
                String[] result_x = Third_constraint.get_x_constraint(vertexs, k, periods, p);

                String result_x_i_k = result_x[0];
                String result_x_k_j = result_x[1];
                
                String result_y = Third_constraint.get_y_constraint(k, periods, p);

                result_x_i_k += " - " + result_y + " = " + 0;
                result_x_k_j += " - " + result_y + " = " + 0;

                third_constraint += result_x_i_k + "\n" + result_x_k_j + "\n";
            }
        } 
        return third_constraint;
    }

    public String get_fourth_constraint() {
        String fourth_constraint = ""; 

        for (int i = 1; i < vertexs.size(); i++){
            ArrayList<String> fourth_constraint_list = new ArrayList<>();
            for (int p = 1; p <= vehicles; p++){
                for (int t = 0; t < periods; t++){
                    String line = "y" + i + "_" + t + "_" + p;
                    fourth_constraint_list.add(line);
                }
            }
            String constraint = String.join(" + ", fourth_constraint_list) + " <= " + 1;
            fourth_constraint += constraint + "\n";
        } 
        
        return fourth_constraint;
    }


    public String get_fifth_constraint() {

        //  The time budget for each path (Tmax) equals the closing time of the starting node.
        Integer Tmax = vertexs.get(0).end_tw;

        // matrix distance
        ArrayList<ArrayList<Integer>> distance_matrix = instance_TOPTW_TDS.distance_matrix;

        String fifth_constraint = "";

        for (int p = 1; p <= vehicles; p++){
            String fifth_constraint_service_time =  Fifth_constraint.get_inner_constraint_service_time(vertexs, periods, p);
            String fifth_constraint_travel_time =  Fifth_constraint.get_inner_constraint_travel_time(vertexs, periods, p, distance_matrix);
            String fifth_constraint_inner = fifth_constraint_service_time + " + " +  fifth_constraint_travel_time + " <= " + Tmax;
            fifth_constraint += fifth_constraint_inner + "\n";
        }

        return fifth_constraint;
    }


      public String get_sixth_constraint() {
        // matrix distance
        ArrayList<ArrayList<Integer>> distance_matrix = instance_TOPTW_TDS.distance_matrix;

        ArrayList<String> sixth_constraint_list = new ArrayList();
        
        Integer big_M = 1000000;

        for (int p = 1; p <= vehicles; p++) {
            for (int i = 0; i < vertexs.size(); i++) {
                for (int j = 1; j < vertexs.size(); j++) {
                    if (i != j) {
                        for (int t = 0; t < periods; t++){
                            String s_i_p = "s" + i + "_" + p;
                            String s_j_p = "s" + j + "_" + p;
                
                            Integer distance = distance_matrix.get(i).get(0); //distance to go back to the depot, from i to 0

                            if(j<vertexs.size())
                                distance = distance_matrix.get(i).get(j); //distance to go to another point, from i to j

                            Integer service_time = vertexs.get(i).service_time;
                            String x =  "x" + i + "_" + j + "_" + t + "_" + p;    

                            String constraint = s_i_p + " - " + s_j_p + " + " + big_M + " " + x + " <= " + (big_M - distance - service_time);

                            sixth_constraint_list.add(constraint);
                        }
                    }  
                }
            }
        }
        
        String sixth_constraint = String.join("\n", sixth_constraint_list);

        return sixth_constraint;

    }


    public String get_seventh_constraint() {

        ArrayList<String> seventh_constraint_list = new ArrayList();
        
        for (int p = 1; p <= vehicles; p++) {
            for (int i = 0; i < vertexs.size(); i++) {
                for (int t = 0; t < periods; t++){
                    String s_i_p = "s" + i + "_" + p;
                    String y = "y" + i + "_" + t + "_" + p;
                    Integer starting_time = vertexs.get(i).begining_tw;

                    String constraint = starting_time + " " + y + " - " + s_i_p + " <= 0 ";
                    seventh_constraint_list.add(constraint);
                }
            }
        }
        
        String seventh_constraint = String.join("\n", seventh_constraint_list);

        return seventh_constraint;
    }

    public String get_eighth_constraint() {

        ArrayList<String> eighth_constraint_list = new ArrayList();
        
        for (int p = 1; p <= vehicles; p++) {
            for (int i = 0; i < vertexs.size(); i++) {
                    String s_i_p = "s" + i + "_" + p;
                    Integer ending_time = vertexs.get(i).end_tw;
                    String constraint = s_i_p + " <= " + ending_time;
                    eighth_constraint_list.add(constraint);
            }
        }
        
        String eighth_constraint = String.join("\n", eighth_constraint_list);

        return eighth_constraint;
    }

    public String get_ninth_constraint() {
        
        ArrayList<Period> time_periods = instance_TOPTW_TDS.time_periods; //time periods

        ArrayList<String> ninth_constraint_list = new ArrayList();
        
        for (int p = 1; p <= vehicles; p++) {
            for (int i = 0; i < vertexs.size(); i++) {
                for (int t = 0; t < periods; t++){
                    String s_i_p = "s" + i + "_" + p;
                    String y = "y" + i + "_" + t + "_" + p;
                    Integer starting_time = time_periods.get(t).starting_time;

                    String constraint = starting_time + " " + y + " - " + s_i_p + " <= 0 ";
                    ninth_constraint_list.add(constraint);
                }
            }
        }
        
        String ninth_constraint = String.join("\n", ninth_constraint_list);

        return ninth_constraint;
    }

    public String get_tenth_constraint() {
        
        ArrayList<Period> time_periods = instance_TOPTW_TDS.time_periods; //time periods

        ArrayList<String> tenth_constraint_list = new ArrayList();

        Integer big_M = 1000000;
        
        for (int p = 1; p <= vehicles; p++) {
            for (int i = 0; i < vertexs.size(); i++) {
                for (int t = 0; t < periods; t++){
                    String s_i_p = "s" + i + "_" + p;
                    String y = "y" + i + "_" + t + "_" + p;
                    Integer ending_time = time_periods.get(t).ending_time;
                    String constraint = s_i_p + " + " + (big_M -  ending_time) + " " + y + " <= " + (big_M);
                    tenth_constraint_list.add(constraint);
                }
            }
        }
        
        String tenth_constraint = String.join("\n", tenth_constraint_list);

        return tenth_constraint;
    }
}
