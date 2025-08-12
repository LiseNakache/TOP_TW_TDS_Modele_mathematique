/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modele_math;
import modele_math.Instance.Instance_TOPTW_TDS;
/**
 *
 * @author LiseNakacheadmin
 */
public class TOP_TW_TimeDependentScore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path_instance = "data/instances/25/C101_TD.txt"; //file to read
        String path_lp = "data/lp_files/25/C101_TD_p2.lp"; //file to create
        Instance_TOPTW_TDS instance = new Instance_TOPTW_TDS();
        instance.read_file_points(path_instance); //tp create the points to visit
        Math_model file = new Math_model();
        file.model(instance, path_lp, 2); //to call the constraints and create the lp file
    }
}