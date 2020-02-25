import java.util.*;
public class ModelChecker{

    public ModelChecker(){

    }


    
    public ArrayList<ArrayList<Integer>> kb = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Integer> symbols = new ArrayList<Integer>();
    public void runProblem(int problem){
        switch(problem){
            case 1:
                runProblem1();
                break;
            case 2:
                //run problem 
                runProblem2();
                break;
            case 3:
                //run problem 3
                runProblem3();
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
            case 2:
                kb.clear();
                symbols.clear();
                //Starting Knowledgebase:
                //R1:
                kb.add(new ArrayList<Integer>(Arrays.asList(-1)));
                //R2:
                kb.add(new ArrayList<Integer>(Arrays.asList(7,-2,-3)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-7,2)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-7,3)));
                //R3:
                kb.add(new ArrayList<Integer>(Arrays.asList(8, -1, -4, -5)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-8, 1)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-8, 4)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-8, 5)));
                //R7:
                kb.add(new ArrayList<Integer>(Arrays.asList(9, -1, -4, -6)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-9, 1)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-9, 4)));
                kb.add(new ArrayList<Integer>(Arrays.asList(-9, 6)));
                //initial perception R4:
                kb.add(new ArrayList<Integer>(Arrays.asList(-7)));
                for(int i = 1; i < 10; i++){
                    symbols.add(i);
                }
                break;
            case 3:
                kb.clear();
                symbols.clear();
                //mythical = 1
                //immortal = -2 ( not mortal)
                //mortal = 2
                //mammal = 3
                //horned = 4
                //magical = 5
                //1 ->-x2
                kb.add(new ArrayList<Integer>(Arrays.asList(-1, -2)));
                //-1 -> (2 and 3)
                kb.add(new ArrayList<Integer>(Arrays.asList(1, 2)));
                kb.add(new ArrayList<Integer>(Arrays.asList(1, 3)));
                //(-2 or 3) -> 4
                kb.add(new ArrayList<Integer>(Arrays.asList(4, 2)));
                kb.add(new ArrayList<Integer>(Arrays.asList(4, -3)));
                //4 -> 5
                kb.add(new ArrayList<Integer>(Arrays.asList(-4, 5)));
                for(int i = 1; i < 6; i++){
                    symbols.add(i);
                }
                break;     
        }
    }


    
    void runProblem1(){
        int Q = 2;
        makeKnowledgeBase(1);
        boolean result = TT_entails(Q);
        System.out.println(result);
    }

    void runProblem2(){
        
        makeKnowledgeBase(2);
        //have nice printouts 
        System.out.println("Checking if KB entails not P(1,2)...");
        boolean not_p12 = TT_entails(-2);
        System.out.println(not_p12);

        makeKnowledgeBase(2);
        System.out.println("Checking if KB entails not P(2,1)...");
        boolean not_p21 = TT_entails(-3);
        System.out.println(not_p21);

        makeKnowledgeBase(2);
        System.out.println("Checking if KB entails P(2,2)...");
        boolean p22 = TT_entails(4);
        System.out.println(p22);

        makeKnowledgeBase(2);
        System.out.println("Checking if KB entails not P(2,2)...");
        boolean not_p22 = TT_entails(-4);
        System.out.println(not_p22);
    }

    void runProblem3(){
        int mythical = 1;
        int magical = 5;
        int horned = 4;
        makeKnowledgeBase(3);
        boolean result1 = TT_entails(mythical); //F
       /* makeKnowledgeBase(3);
        boolean result2 = TT_entails(magical); //T
        makeKnowledgeBase(3);
        boolean result3 = TT_entails(horned); //T*/
        System.out.println(result1);
        //System.out.println(result2);
        //System.out.println(result3);
    }

    boolean TT_entails(int alpha){
        HashMap<Integer,Boolean> model = new HashMap<Integer,Boolean>();
        return TT_checkAll(kb, alpha, symbols, model);
    }

    ArrayList<HashMap<Integer,Boolean>> penis = new ArrayList<HashMap<Integer,Boolean>>();
    boolean TT_checkAll(ArrayList<ArrayList<Integer>> kbase, int alpha, ArrayList<Integer> sym, HashMap<Integer,Boolean> model){
        penis.add(model);
        if(sym.isEmpty()){
            boolean pl = PL_true(kb,model);
            if(pl){
                System.out.println("PL is t");
                return PL_true(alpha, model);
            }else{
               // System.out.println(model.toString());
                return true; //when kb is false, always return true
            }
        }
        else{
            Integer P = sym.remove(0); //{1,2,3,4,5,6,8,9}
            System.out.println("Just removed sym: " + P);
            return(
                TT_checkAll(kbase, alpha, sym, union(model, P, Boolean.TRUE)) && 
                TT_checkAll(kbase, alpha, sym, union(model, P, Boolean.FALSE))
            );
        }
    }

    public Boolean PL_true(int alpha, HashMap<Integer,Boolean> model){
        System.out.println("TESTING ALPAHA");
        System.out.println(model.toString());
        return atomic_eval((Integer) alpha, model);
    }

    public Boolean PL_true(ArrayList<ArrayList<Integer>> kbase,HashMap<Integer,Boolean> model){
        Boolean result = Boolean.TRUE;
        for(int i = 0; i < kbase.size(); i++){
            ArrayList<Integer> clause = kbase.get(i);
            result = result && pl_helper(clause,model);
        }
        return result;
    }
    private Boolean pl_helper(ArrayList<Integer> clause, HashMap<Integer, Boolean> model){
        Boolean result = Boolean.FALSE;

        /*System.out.println("CLAUSE: ");
        System.out.println(clause.toString());
        System.out.println("MODEL: ");
        System.out.println(model.toString());*/

        for(int i = 0; i < clause.size(); i++){
            int x = clause.get(i);
            Boolean b = model.get(Math.abs(x));
            if(b != null){
                boolean bo = atomic_eval(clause.get(i), model);
                result = result || bo;
                
            }
        }
        return result;
    }

    private Boolean atomic_eval(Integer atom, HashMap<Integer, Boolean> model){
        Boolean result = null; 
        if(atom < 0){
            Boolean assignment = model.get(Math.abs(atom));
            if(assignment != null){
               //System.out.println("here");
                if(assignment.booleanValue() == true){
                    result = Boolean.FALSE;
                }else{ 
                    result = Boolean.TRUE;
                }
                return result;
            }
        }
        return model.get(atom);
        
    }

    private <T,E> HashMap<T,E> union(HashMap<T,E> map, T key, E b){
        HashMap<T,E> toReturn = new HashMap<T,E>();
        toReturn.putAll(map);
        toReturn.put(key,b);
        return toReturn;
    }


    public static void main(String[] args){
        ModelChecker mc = new ModelChecker();
         mc.runProblem(3);
       for(HashMap<Integer,Boolean> map : mc.penis){
           System.out.println(map.toString());
       }
       //Boolean[] arr = new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
       // ArrayList<Boolean> model = new ArrayList<Boolean>(Arrays.asList(arr));
     //   System.out.println(mc.PL_true(1, model));
    }





} 