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
                kb.add(new ArrayList<Integer>(Arrays.asList(-7,2,3)));
                kb.add(new ArrayList<Integer>(Arrays.asList(7,-2)));
                kb.add(new ArrayList<Integer>(Arrays.asList(7,-3)));
                //R3:
                kb.add(new ArrayList<Integer>(Arrays.asList(-8, 1, 4, 5)));
                kb.add(new ArrayList<Integer>(Arrays.asList(8, -1)));
                kb.add(new ArrayList<Integer>(Arrays.asList(8, -4)));
                kb.add(new ArrayList<Integer>(Arrays.asList(8, -5)));
                //R7:
                kb.add(new ArrayList<Integer>(Arrays.asList(-9, 1, 4, 6)));
                kb.add(new ArrayList<Integer>(Arrays.asList(9, -1)));
                kb.add(new ArrayList<Integer>(Arrays.asList(9, -4)));
                kb.add(new ArrayList<Integer>(Arrays.asList(9, -6)));
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
        System.out.println("\nRunning Problem 1:\n");
        int Q = 2;
        makeKnowledgeBase(1);
        boolean result = TT_entails(new ArrayList<Integer>(Arrays.asList(Q)));
        System.out.println("Checking if KB entails Q...");
        System.out.println(result);
    }

    void runProblem2(){
        System.out.println("\nRunning Problem 2:\n");

        makeKnowledgeBase(2);
        System.out.println("Checking if KB entails not P(1,2)...");
        boolean not_p12 = TT_entails(new ArrayList<Integer>(Arrays.asList(-2)));
        System.out.println(not_p12);

        System.out.println("Checking if KB entails not P(2,1)...");
        boolean not_p21 = TT_entails(new ArrayList<Integer>(Arrays.asList(-3)));
        System.out.println(not_p21);

        System.out.println("Checking if KB entails P(2,2)...");
        boolean p22 = TT_entails(new ArrayList<Integer>(Arrays.asList(4)));
        System.out.println(p22);

        System.out.println("Checking if KB entails not P(2,2)...");
        boolean not_p22 = TT_entails(new ArrayList<Integer>(Arrays.asList(-4)));
        System.out.println(not_p22);

        System.out.println("Adding Perception R5: B(2,1)");
        ArrayList<Integer> r5 = new ArrayList<Integer>(Arrays.asList(8));
        kb.add(r5);
        
        System.out.println("Checking if KB now entails P(2,2) OR P(3,1)...");
        boolean p22_or_p31 = TT_entails(new ArrayList<Integer>(Arrays.asList(4,5)));
        System.out.println(p22_or_p31);
        
        System.out.println("Checking if KB entails P(2,2)...");
        p22 = TT_entails(new ArrayList<Integer>(Arrays.asList(4)));
        System.out.println(p22);    
        
        System.out.println("Checking if KB entails not P(2,2)...");
        not_p22 = TT_entails(new ArrayList<Integer>(Arrays.asList(-4)));
        System.out.println(not_p22);
        
        System.out.println("Checking if KB entails P(3,1)...");
        boolean p31 = TT_entails(new ArrayList<Integer>(Arrays.asList(5)));
        System.out.println(p31);
        
        System.out.println("Checking if KB entails not P(3,1)...");
        boolean not_p31 = TT_entails(new ArrayList<Integer>(Arrays.asList(-5)));
        System.out.println(not_p31);
        
        System.out.println("Adding Perception R6: not B(1,2)");
        ArrayList<Integer> r6 = new ArrayList<Integer>(Arrays.asList(-9));
        kb.add(r6);

        System.out.println("Checking if KB entails not P(2,2)...");
        not_p22 = TT_entails(new ArrayList<Integer>(Arrays.asList(-4)));
        System.out.println(not_p22);
        
        System.out.println("Checking if KB entails P(3,1)...");
        p31 = TT_entails(new ArrayList<Integer>(Arrays.asList(5)));
        System.out.println(p31); 
    }
    
    void runProblem3(){
        System.out.println("\nRunning Problem 3:\n");
        int mythical = 1;
        int magical = 5;
        int horned = 4;
        makeKnowledgeBase(3);
        boolean result1 = TT_entails(new ArrayList<Integer>(Arrays.asList(mythical)));
        makeKnowledgeBase(3);
        boolean result2 = TT_entails(new ArrayList<Integer>(Arrays.asList(magical)));
        makeKnowledgeBase(3);
        boolean result3 = TT_entails(new ArrayList<Integer>(Arrays.asList(horned)));
        System.out.println("Checking if KB entails Mythical...");
        System.out.println(result1);
        System.out.println("Checking if KB entails Magical...");
        System.out.println(result2);
        System.out.println("Checking if KB entails Horned...");
        System.out.println(result3);
    }

    boolean TT_entails(ArrayList<Integer> alpha){
        HashMap<Integer,Boolean> model = new HashMap<Integer,Boolean>();
        ArrayList<Integer> s = new ArrayList<Integer>(symbols);
        ArrayList<ArrayList<Integer>> kbase = new ArrayList<ArrayList<Integer>>(kb);
        return TT_checkAll(kbase, alpha, s, model);
    }

    ArrayList<HashMap<Integer,Boolean>> penis = new ArrayList<HashMap<Integer,Boolean>>();
    boolean TT_checkAll(ArrayList<ArrayList<Integer>> kbase, ArrayList<Integer> alpha, ArrayList<Integer> sym, HashMap<Integer,Boolean> model){
        if(sym.isEmpty()){
            penis.add(model);
            boolean pl = PL_true_kb(kb,model);
            if(pl){
                return PL_true(alpha, model);
            }else{
                return true; //when kb is false, always return true
            }
        }
        else{ 
            Integer P = sym.get(0);
            ArrayList<Integer> rest = new ArrayList<Integer>(sym.subList(1, sym.size()));
            return (TT_checkAll(kbase, alpha, rest, union(model, P, Boolean.TRUE)) && TT_checkAll(kbase, alpha, rest, union(model, P, Boolean.FALSE))) ;    
        }
    }

    public Boolean PL_true(ArrayList<Integer> alpha, HashMap<Integer,Boolean> model){
        return pl_helper(alpha, model);
    }

    public Boolean PL_true_kb(ArrayList<ArrayList<Integer>> sentence,HashMap<Integer,Boolean> model){
        Boolean result = Boolean.TRUE;
        for(int i = 0; i < sentence.size(); i++){
            ArrayList<Integer> clause = sentence.get(i);
            result = result && pl_helper(clause,model);
        }
        return result;
    }
    
    private Boolean pl_helper(ArrayList<Integer> clause, HashMap<Integer, Boolean> model){
        Boolean result = Boolean.FALSE;
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
                if(assignment.booleanValue() == true){
                    return Boolean.FALSE;
                }else{ 
                    return Boolean.TRUE;
                }
            }
        }
        return model.get(atom);
    }

    private <T,E> HashMap<T,E> union(HashMap<T,E> map, T key, E b){
        HashMap<T,E> toReturn = new HashMap<T,E>();
        map.put(key,b);

        toReturn.putAll(map);
        return toReturn;
    }

    public static void main(String[] args){
        ModelChecker mc = new ModelChecker();
        for(int i = 1; i < 4; i++){
            mc.runProblem(i);
        }
    }
} 