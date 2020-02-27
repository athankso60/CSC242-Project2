/** This class is responsible for parsing a dimacs file into a list of clauses */
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
public class DimacsReader{

    
    public int numClauses = 0;
    public int maxVariable = 0;
    ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
    public DimacsReader(){

    }

    public void readFile(String path){
        try {
            File myObj = new File(path);
            Path p = myObj.toPath();
              String content = Files.readString(p, StandardCharsets.US_ASCII);
              String[] contentArr = content.split("\\s0\\s+"); //delimit the content by a 0 seperated by white space on each side
              /** parse header part */
              String header = contentArr[0];
              String[] headerArr = header.split("\n");
              for(String str : headerArr){ //organize header part
                  if(str.charAt(0) == 'p'){
                    System.out.println(str);
                    String[] pArr = str.split(" ");
                    maxVariable = Integer.parseInt(pArr[2].trim());
                    numClauses = Integer.parseInt(pArr[3].trim());
                    System.out.println("Num clauses: " + numClauses + "\n max Variable: " + maxVariable);

                   // System.out.println(pArr[4]);
                  }else if(str.charAt(0) != 'c'){
                    String[] clauseArr = str.trim().split("\\s+");
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    for(int i = 0; i < clauseArr.length; i++){
                      //System.out.println(clauseArr[i]);
                      int num = Integer.parseInt(clauseArr[i].trim());
                      list.add(num);
                    }
                    clauses.add(list);
                  }
                  
              }
              for(int i = 1; i < contentArr.length; i++){
                String str = contentArr[i];
                if(str.charAt(0) != 'c'){
                  String[] clauseArr = str.trim().split("\\s+");
                  ArrayList<Integer> list = new ArrayList<Integer>();
                  for(int j = 0; j < clauseArr.length; j++){
                    int num = Integer.parseInt(clauseArr[j].trim());
                    list.add(num);
                  }
                  clauses.add(list);
                }
                
              }
             // System.out.println(contentArr[0]);
             // System.out.println(content);
            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public ArrayList<ArrayList<Integer>> getClauses(){
      return clauses;
    }


    public static void main(String args[]){
        DimacsReader dr = new DimacsReader();
       dr.readFile("CSC242_Project_2_cnf/nqueens/nqueens_4.cnf");
       //dr.readFile("problem1.cnf");
        for(ArrayList<Integer> list : dr.clauses){
          System.out.println("\nClause: ");
          for(Integer n: list){
            System.out.print(n + " ");
          }


        }
    }
}
