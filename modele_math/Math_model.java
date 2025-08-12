package modele_math;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import modele_math.Constraints.Constraints;
import modele_math.Instance.Instance_TOPTW_TDS;

public class Math_model {

    public void model(Instance_TOPTW_TDS _instance, String file_name, Integer vehicles) {
        
        // ---------------------------------
        Integer periods = 4;
        // ---------------------------------

        Constraints constraints = new Constraints(_instance, vehicles, periods);
        
        // Create all the constraints
        try {
            File dest = new File(file_name);
            FileWriter Output = new FileWriter(dest);

            BufferedWriter writer = new BufferedWriter(Output);  

            writer.write("\\Modele Lineaire : \n");  
         
            writer.write("Maximize" + "\n");
            writer.write(constraints.get_objective_func() + "\n" + "\n");

            writer.write("Subject To" + "\n");

            writer.write(constraints.get_sec_constraint() + "\n");

            writer.write(constraints.get_third_constraint() + "\n");

            writer.write(constraints.get_fourth_constraint() + "\n");

            writer.write(constraints.get_fifth_constraint() + "\n");

            writer.write(constraints.get_sixth_constraint() + "\n" + "\n");

            writer.write(constraints.get_seventh_constraint() + "\n"+ "\n");

            writer.write(constraints.get_eighth_constraint() + "\n" + "\n");

            writer.write(constraints.get_ninth_constraint() + "\n"+ "\n");

            writer.write(constraints.get_tenth_constraint() + "\n"+ "\n");

            writer.write("General" + "\n");

            // Integers si_p
            for (int p = 1; p <= vehicles; p++) {
                for (int i = 0; i < _instance.vertexs.size(); i++) {
                    writer.write("s" + i + "_" + p + "\n");    
                }
            }

            // Binaries xi_j_t_p
            writer.write("Binaries" + "\n");
            for (int p = 1; p <= vehicles; p++) {
                for (int i = 0; i< _instance.vertexs.size(); i++) {
                    for (int j = 0; j< _instance.vertexs.size(); j++) {
                        for (int t = 0; t< _instance.time_periods.size(); t++) {
                            if(i != j) {
                                writer.write("x" + i + "_" + j + "_" + t + "_" + p + "\n");
                            }
                        }
                    }
                }
            }
            
            // Binaries yi_t_p
            for (int p = 1; p <= vehicles; p++) {
                for (int i = 0; i< _instance.vertexs.size(); i++) {
                    for (int t = 0; t< _instance.time_periods.size(); t++) {
                        writer.write("y" + i + "_" + t + "_" + p + "\n");
                    }
                }
            }            

            writer.write("end");
            writer.close();
            Output.close();
            System.out.println("Génération du fichier : OK ");
            System.out.println("-----------------------------------");     

        }catch(Exception E) {
                System.out.println(" -> Erreur : " + E.getMessage());    
        }
    }
}