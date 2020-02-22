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

    boolean TT_entails(int alpha){
        System.out.println(symbols.toString());
        ArrayList<Boolean> model = new ArrayList<Boolean>(Collections.nCopies(symbols.size(), null));
        return TT_checkAll(kb, alpha, symbols, model);
    }

    boolean TT_checkAll(ArrayList<ArrayList<Integer>> kbase, int alpha, ArrayList<Integer> sym, ArrayList<Boolean> model){
        if(sym.isEmpty()){
            boolean pl = PL_true(kb,model);
            System.out.println(model.toString());
            if(pl){
                System.out.println("PL is t");
                return PL_true(alpha, model);
            }else{
                System.out.println("PL is f");
                return true; //when kb is false, always return true
            }
        }
        else{
            Integer P = sym.remove(0);
            //sym is now = to rest.
            ArrayList<Boolean> m1 = new ArrayList<Boolean>();
            m1 = deepCopy(model);
            m1.add(P-1, Boolean.TRUE);
            ArrayList<Boolean> m2 = new ArrayList<Boolean>();
            m2 = deepCopy(model);
            m2.add(P-1, Boolean.FALSE);
            return(
                TT_checkAll(kbase, alpha, sym, m1) && 
                TT_checkAll(kbase, alpha, sym, m2)
            );
        }
    }

    Boolean PL_true(int alpha, ArrayList<Boolean> model){
        return atomic_eval((Integer) alpha, model);
    }

    Boolean PL_true(ArrayList<ArrayList<Integer>> kbase, ArrayList<Boolean> model){
        Boolean result = Boolean.TRUE;
        for(int i = 0; i < kbase.size(); i++){
            ArrayList<Integer> clause = kbase.get(i);
            result = result && pl_helper(clause,model);
        }
        return result;
    }
    private Boolean pl_helper(ArrayList<Integer> clause, ArrayList<Boolean> model){
        Boolean result = Boolean.FALSE;
        for(int i = 0; i < clause.size(); i++){
            if(model.get(Math.abs(clause.get(i)) - 1) != null){
                result = result || atomic_eval(clause.get(i), model);
            }
        }
        return result;
    }

    private Boolean atomic_eval(Integer atom, ArrayList<Boolean> model){
        Boolean result = null; 
        if(atom < 0){
            Boolean assignment = model.get(Math.abs(atom) -1);
            if(assignment.booleanValue() == true){
                result = Boolean.FALSE;
            }else{ 
                result = Boolean.TRUE;
            }
            return result;
        }else{
            return model.get(atom - 1);
        }
    }

    private <T> ArrayList<T> deepCopy(ArrayList<T> list){
        ArrayList<T> toReturn = new ArrayList<T>(list.size());
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) != null){
                toReturn.add(i, list.get(i));
            }
        }
        return toReturn;
    }


    public static void main(String[] args){
        ModelChecker mc = new ModelChecker();
        mc.runProblem(1);
    }





} 