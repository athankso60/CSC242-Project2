import java.util.*;
public class Satisfier{

    ArrayList<ArrayList<Integer>> clauses;
    int numVariables;
    int numClauses;
    HashSet<HashMap<Integer,Boolean>> explored = new HashSet<HashMap<Integer,Boolean>>();
    public Satisfier(String filename){
        DimacsReader dc = new DimacsReader();
        dc.readFile(filename);
        clauses = dc.getClauses();
        numVariables = dc.maxVariable;
        numClauses = dc.numClauses;
    }

    /** @return a model assignment that is random */
    private HashMap<Integer,Boolean> getRandomAssignment(){
        HashMap<Integer,Boolean> toReturn = new HashMap<Integer,Boolean>();
        for(int i = 1; i < numVariables+1; i++){
            Boolean b = getRandomBoolean();
            toReturn.put((Integer) i, b);
        }
        if(explored.contains(toReturn)){
            getRandomAssignment();
        }
        return toReturn;
    }

    /** Generates boolean with equal probability of T/F */
    private Boolean getRandomBoolean(){
        if(Math.random() < 0.5){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /** @return the index for the literal that if flipped,
     *  would yield the highest number of
     * satisfied clauses
     */
    private Integer maxHeuristic(HashMap<Integer,Boolean> T){
        //get # of clauses that T currently accepts
        int maxValue = 0;
        Integer toReturn = (Integer) 0;
        for(Map.Entry<Integer,Boolean> entry : T.entrySet()){
            //get # of clauses that would be accepted if T.get(i) were flipped
            HashMap<Integer,Boolean> map = flip(T, entry.getKey());
            int temp = getNumAcceptingClauses(map);
            if(temp > maxValue){
               // System.out.println("Finding new max val");
                maxValue = temp;
                toReturn = entry.getKey();
                
            }
        }
        //System.out.println("Key is now: " + toReturn);
        //System.out.println("Maxvalue is now: " + maxValue);
        return toReturn;

    }

    private HashMap<Integer,Boolean> flip(HashMap<Integer,Boolean> map, Integer index){ 
        HashMap<Integer,Boolean> toReturn = new HashMap<Integer,Boolean>();
        toReturn.putAll(map);
        Boolean b = map.get(index);
        toReturn.put(index, !b);
        return toReturn;
    }


    /** @return the number of satisfied clauses given model */
    private int getNumAcceptingClauses(HashMap<Integer,Boolean> model){
        int result = 0;
        for(int i = 0; i < numClauses; i++){
            ArrayList<Integer> clause = clauses.get(i);
            if(ModelChecker.pl_helper(clause,model)){
                result += 1;
            }
        }
       // System.out.println(result);
        return result;

    }

    


    /** @return a map of symbols and truth assignments or an empty map*/
    HashMap<Integer,Boolean> GSAT(int maxFlips, int maxTries, boolean tracing){
        //tracing will print info every 50 times to avoid screen clutter
        for(int i = 0; i < maxTries; i++){
            HashMap<Integer,Boolean> T  = getRandomAssignment();
            if(tracing && i % 100 == 0){
                System.out.println("Generated " + i+1 + "th random assignment...");
                System.out.println(T.toString());
            }
            for(int j = 0 ; j < maxFlips; j++){
                if(ModelChecker.PL_true_kb(clauses, T)){
                    return T;
                }
                Integer p = maxHeuristic(T);
                T = flip(T,p);
                if(tracing){
                    System.out.println("According to heuristic, index " + p + " flipped.");
                    System.out.println("Model is now: ");
                    System.out.println(T.toString());
                }
               // System.out.println(T.toString());
            }
        }
        System.out.println("No Satisfying Assignment Found.");
        return null;
    }

    public static void runSAT(){
        System.out.println("\n\nWelcome to the GSAT portion of our project.");

        Scanner scan = new Scanner(System.in);
        System.out.println("What problem do you want to test? (Type number)");
        System.out.println("1: Problem 1\n2: Quinn.cnf\n3:Aim-50-1_6-yes1-4.cnf\n4: nqueens of n=4\n5: nqueens of n=8\n6: nqueens of n=12\n7: nqueens of n = 16 *Warning may take a long time or not work\n8: Quit satisfier");
        int choice = scan.nextInt();
        while(choice != 8){
            System.out.println("What value of MAX-FLIPS would you like?\nSuggested values:10-100 for a quick test, 1000 or above for a more comprehensive test");
            int mf = scan.nextInt();
            System.out.println("What value of MAX-TRIES would you like?\nSuggested values:10-100 for a quick test, 1000 or above for a more comprehensive test");
            int mt = scan.nextInt();
            System.out.println("Would you like to enable tracing? (warning: tracing may lead to a lot of text and worse performance.)\n Type y/n");
            String yn = scan.next();
            boolean tracing = (yn.equals("y")) ? true : false;
            HashMap<Integer,Boolean> result = null;
            switch(choice){

                case 1:
                    Satisfier p1 = new Satisfier("problem1.cnf");
                    result = p1.GSAT(mf, mt, tracing); 
                    break;
                case 2:
                    Satisfier p2 = new Satisfier("CSC242_Project_2_cnf/cnf/quinn.cnf.txt");
                    result = p2.GSAT(mf, mt, tracing);
                    break;
                case 3:
                    Satisfier p3 = new Satisfier("CSC242_Project_2_cnf/cnf/aim-50-1_6-yes1-4.cnf.txt");
                    result = p3.GSAT(mf, mt, tracing);
                case 4:
                    Satisfier p4 = new Satisfier("CSC242_Project_2_cnf/nqueens/nqueens_4.cnf");
                    result = p4.GSAT(mf, mt, tracing);
                    break;
                case 5:
                    Satisfier p5 = new Satisfier("CSC242_Project_2_cnf/nqueens/nqueens_8.cnf");
                    result = p5.GSAT(mf, mt, tracing);
                    break;
                case 6:
                    Satisfier p6 = new Satisfier("CSC242_Project_2_cnf/nqueens/nqueens_12.cnf");
                    result = p6.GSAT(mf, mt, tracing);
                    break;
                case 7:
                    Satisfier p7 = new Satisfier("CSC242_Project_2_cnf/nqueens/nqueens_16.cnf");
                    result = p7.GSAT(mf, mt, tracing);
                    break;
                default:
                    System.out.println("Not a valid input. Try again.");
            }
            if(result == null){
                System.out.println("Aw man! GSAT didn't figure out a satisfying model. Try again, this happens with a randomized hill-climbing local search sometimes.");
            }
            else{
                System.out.println("Found satisfying model!!!!");
                System.out.println("Printing model assignment, variable number followed by truth value...");
                for(Map.Entry<Integer,Boolean> entry: result.entrySet()){
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }   
            }
            
            System.out.println("What problem do you want to test? (Type number)");
            System.out.println("1: Problem 1\n2: Quinn.cnf\n3:Aim-50-1_6-yes1-4.cnf\n4: nqueens of n=4\n5: Quit satisfier");
            choice = scan.nextInt();
        }

        System.out.println("Sorry to see you go!");
        
    }

    public static void main(String[] args){
      /* // Satisfier s = new Satisfier("problem1.cnf");
      //W0ROKS
     //  Satisfier s = new Satisfier("CSC242_Project_2_cnf/nqueens/nqueens_4.cnf");
     // Satisfier s = new Satisfier("CSC242_Project_2_cnf/cnf/quinn.cnf.txt");
        Satisfier s = new Satisfier("CSC242_Project_2_cnf/cnf/aim-50-1_6-yes1-4.cnf.txt");
      //quinn works for 1000/5000 each rarely?
        HashMap<Integer,Boolean> result = s.GSAT(5000,5000,true); 
        if(result != null){
            System.out.println("Found Assignment!!!");
            System.out.println(result.toString());
        }else{
            System.out.println("Returned null");
        }*/
        Satisfier.runSAT();
    }


}