/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele_math.Instance;

import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class Vertex {
  int node;
  int x;
  int y;
  public int max_score;
  public int begining_tw;
  public int end_tw;
  public int service_time;
  public ArrayList<Double> recommendation_factors;

  // constructor method
  public Vertex(int node, int x, int y, int max_score, int begining_tw, int end_tw, int service_time, ArrayList<Double> recommendation_factors) {
    this.node = node;
    this.x = x;
    this.y = y;
    this.max_score = max_score;
    this.begining_tw = begining_tw;
    this.end_tw = end_tw;
    this.service_time = service_time;
    this.recommendation_factors = recommendation_factors;
  }
}
