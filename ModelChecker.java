import java.util.*;
public class ModelChecker{

    public ModelChecker(){

    }


    
    public ArrayList<ArrayList<Integer>> kb = new ArrayList<ArrayList<Integer>>();
    private HashSet<Integer> symbols = new HashSet<Integer>();
    public void runProblem(int problem){
        switch(problem){
            case 1:
                runProblem1();
                break;
            case 2:
                //run problem 2
                break;
            case 3:
                //run problem 3
                break;
            default:
                System.out.println("Problem not implemented");

        }

    }

    void makeKnowledgeBase(int problem){
        switch(problem){
            case 1: 
            //construct kb as p = 1, q =2 
            //conjunctive normal form for p->q:
            // (-p v q) ^ P => Q
            kb.clear();
            symbols.clear();
            kb.add(new ArrayList<Integer>(Arrays.asList(1)));
            kb.add(new ArrayList<Integer>(Arrays.asList(-1, 2)));
            symbols.add(1);
            symbols.add(2);
            break;
        }
    }


    
    void runProblem1(){
        int P = 1;
        int Q = 2;
        makeKnowledgeBase(1);
        boolean result = TT_entails(Q);
        System.out.println(result);
    }

    Boolean TT_entails(int alpha){
        
    }


} 