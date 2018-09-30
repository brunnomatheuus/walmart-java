package br.com.bruno;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult4;



public class Teste {
	
	@State
    public static class TesteEstoque {
        public final EstoqueProduto estoque = new EstoqueProduto(2, 100);
    }


    @JCStressTest
    @Description("Teste pra testar concorrencia ao comprar um mesmo produto")
    @Outcome(id="[90, 70, 40, 0]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor2 - actor3 - actor4")
    @Outcome(id="[90, 70, 0, 30]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor2 - actor4 - actor3")
    @Outcome(id="[90, 0, 20, 50]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor4 - actor3 - actor2")
    @Outcome(id="[90, 0, 60, 20]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor3 - actor4 - actor2")
    @Outcome(id="[90, 40, 60, 0]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor3 - actor2 - actor4")
    @Outcome(id="[90, 30, 0, 50]", expect = Expect.ACCEPTABLE, desc = "actor1 - actor4 - actor3 - actor2")
    @Outcome(id="[70, 80, 40, 0]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor1 - actor3 - actor4")
    @Outcome(id="[40, 80, 50, 0]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor3 - actor1 - actor4")
    @Outcome(id="[70, 80, 0, 30]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor1 - actor4 - actor3")
    @Outcome(id="[0, 80, 50, 10]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor3 - actor4 - actor1")
    @Outcome(id="[0, 80, 10, 40]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor4 - actor3 - actor1")
    @Outcome(id="[30, 80, 0, 40]", expect = Expect.ACCEPTABLE, desc = "actor2 - actor4 - actor1 - actor3")
    @Outcome(id="[0, 10, 70, 30]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor4 - actor2 - actor1")
    @Outcome(id="[60, 0, 70, 20]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor1 - actor4 - actor2")
    @Outcome(id="[20, 0, 70, 30]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor4 - actor1 - actor2")
    @Outcome(id="[40, 50, 70, 0]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor2 - actor1 - actor4")
    @Outcome(id="[0, 50, 70, 10]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor2 - actor4 - actor1")
    @Outcome(id="[60, 40, 70, 0]", expect = Expect.ACCEPTABLE, desc = "actor3 - actor1 - actor2 - actor4")
    @Outcome(id="[50, 0, 20, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor1 - actor3 - actor2")
    @Outcome(id="[20, 0, 30, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor3 - actor1 - actor2")
    @Outcome(id="[30, 40, 0, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor2 - actor1 - actor3")
    @Outcome(id="[0, 10, 30, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor3 - actor2 - actor1")
    @Outcome(id="[0, 40, 10, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor2 - actor3 - actor1")
    @Outcome(id="[50, 30, 0, 60]", expect = Expect.ACCEPTABLE, desc = "actor4 - actor1 - actor2 - actor3")
    public static class StressTest {

        @Actor
        public void actor1(TesteEstoque estoque, LongResult4 r1) {
            r1.r1 = estoque.estoque.comprar(0, 10);
        }

        @Actor
        public void actor2(TesteEstoque estoque, LongResult4 r1) {
        	r1.r2 = estoque.estoque.comprar(0, 20);
        }
        
        @Actor
        public void actor3(TesteEstoque estoque, LongResult4 r1) {
        	r1.r3 = estoque.estoque.comprar(0, 30);
        }
        
        @Actor
        public void actor4(TesteEstoque estoque, LongResult4 r1) {
        	r1.r4 = estoque.estoque.comprar(0, 40);
        }
    }
}
