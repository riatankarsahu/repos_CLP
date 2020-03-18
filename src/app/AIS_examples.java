package app;

import org.jacop.constraints.*;
import org.jacop.core.*;
import org.jacop.search.*;
import java.util.ArrayList;

public class AIS_examples 
{
    Store store = new Store();
    ArrayList<Var> vars = new ArrayList<Var>();

    public void Value()
    {
        IntVar x0 = new IntVar(store, "X0", 1, 1);
        IntVar x1 = new IntVar(store, "X1", 1, 1);
        IntVar x2 = new IntVar(store, "X2", 3, 3);
        IntVar x3 = new IntVar(store, "X3", 1, 3);
        IntVar x4 = new IntVar(store, "X4", 1, 1);
        IntVar[] val = {x0, x1, x2, x3, x4};
        IntVar count = new IntVar(store, "count", 2, 4);

        for (int i = 0; i < 5; i++)
            System.out.println(val[i]);
        System.out.println(count);

        store.impose(new Values(val, count));
        store.consistency();
        System.out.println("");

        for (int i = 0; i < 5; i++)
            System.out.println(val[i]);
        System.out.println(count);
    }

    public void Count() 
    {
        IntVar[] List = new IntVar[4];
        List[0] = new IntVar(store, "List_"+0, 0, 1);
        List[1] = new IntVar(store, "List_"+1, 0, 2);
        List[2] = new IntVar(store, "List_"+2, 2, 2);
        List[3] = new IntVar(store, "List_"+3, 3, 4);
        IntVar Var = new IntVar(store, "Var", 0, 4);//0,1 <---- // 2,2
        
        for (int i=0; i<4; i++) 
            System.out.println(List[i]); 
        System.out.println(Var);
        
        store.impose(new Count(List, Var, 2));
        store.consistency();
        System.out.println("");
        System.out.println("Count(List) : 2, Var=?");
        System.out.println("");
        for (int i=0; i<4; i++) 
            System.out.println(List[i]);        
        System.out.println(Var);

        System.out.println("");
        System.out.println("Alldifferent(List). Var=?");
        System.out.println("");
        
        store.impose(new Alldifferent(List));
        store.consistency();
        for (int i=0; i<4; i++) 
            System.out.println(List[i]);        
        System.out.println(Var);
        
        SelectChoicePoint select = new SimpleSelect(List, new SmallestDomain(), new IndomainMin());//Max
        Search search = new DepthFirstSearch();
        
        search.labeling(store, select);
        
        System.out.println(Var);
    }

    public void Assign()
    {
        IntVar[] x = new IntVar[4];
        IntVar[] y = new IntVar[4];
        for (int i=0; i<4; i++) 
        {
            x[i] = new IntVar(store, "x"+i, 0, 3);
            y[i] = new IntVar(store, "y"+i, 0, 3);
        }
        store.impose(new Assignment(x, y));
        store.consistency();
        for (int i=0; i<4; i++) 
        {
            System.out.println(x[i]); 
            System.out.println(y[i]); 
        }
        
        System.out.println("");
        System.out.println("x[1] = 3 ==> y[3] = 1");
        System.out.println("");
        store.impose(new XeqC(x[1], 3));
        store.consistency();
        for (int i=0; i<4; i++) 
        {
            System.out.println(x[i]); 
            System.out.println(y[i]); 
        }
        
        System.out.println("");
        System.out.println("y[1] = 2 ==> x[2] = 1");
        System.out.println("");
        store.impose(new XeqC(y[1], 2));
        store.consistency();
        for (int i=0; i<4; i++) 
        {
            System.out.println(x[i]); 
            System.out.println(y[i]); 
        }
    }

    public void MinMax()
    {
        IntVar a = new IntVar(store, "a", 1, 3);
        IntVar b = new IntVar(store, "b", 3, 5);
        IntVar c = new IntVar(store, "c", 5, 7);
        IntVar min = new IntVar(store, "min", 1, 9);//3
        //IntVar min = new IntVar(store, "min", 3, 19);
        IntVar[] vv = {a, b, c};        
                
        store.impose(new Min(vv, min));
        //store.impose(new Max(vv, min));
        
       //store.impose(new XgtC(a,1));
       //store.impose(new XgtC(c,6));
        
        IntVar[] v = {a, b, c, min};
        
        SelectChoicePoint select = new SimpleSelect(v, new SmallestDomain(), new IndomainMax());//Max
        Search search = new DepthFirstSearch();
         
        search.labeling(store, select, min);
        
        System.out.println(min);
    }

    public void Elem()
    {
        IntVar v = new IntVar(store, "v", 1, 50);
        IntVar i = new IntVar(store, "i", 1, 3);//5
        int[] el = {3, 44, 10};
        //int[] el = {3, 44, 10, 15, 45, 46};
        
        System.out.println(v);
        System.out.println(i);
        
        
        store.impose(new Element(i, el, v));
        store.consistency();
        System.out.println("");
        System.out.println("imposing Element(i,el,v):");
        System.out.println(v);
        System.out.println(i);
        
        
        store.impose(new XltC(v,40));
        store.consistency();
        System.out.println("");
        System.out.println("imposing XltC(v,40):");
        System.out.println(v);
        System.out.println(i);
    }

    public static void main(String[] args) 
    {
        new AIS_examples().Value();
        //new AIS_examples().Count();
        //new AIS_examples().Assign(); 
        //new AIS_examples().MinMax();
        //new AIS_examples().Elem();       
    }
}