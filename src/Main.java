public class Main {

    public static void main(String[] args) throws Exception {
        Test[] tests1 = {
                new Test("a [b] c","a[ b] c"),
                new Test("[a] [b] c","[a ][b ]c"),
                new Test("a b [c]","a b[ c]"),
                new Test("a[b] c","a[b] c"),
                new Test("[a]b c","[a]b c"),
                new Test("a [b]c","a [b]c"),
                new Test("a b[c]","a b[c]"),
                //a b c d,a c d, a d
                new Test("a [[b] c] d","a[[ b] c] d"),
                new Test("a [b] [c] d","a[ b][ c] d"),
                new Test("[[b] c] a","[[b ]c ]a"),
                new Test("a [[a b[c]] c]","a[[ a b[c]] c]"),
                new Test("a (b[c]|d)","a (b[c]|d)"),
                new Test("[a (b|c)]d","[a (b|c)]d"),
                new Test("(a|b) [c]","(a|b)[ c]"),
                new Test("[a] (b|c)","[a ](b|c)"),
                new Test("[a] [[b] c] d","[a ][[b ]c ]d"),
                new Test("[a] [[b] c]","error"),
                new Test("a [[b] c]","a[[ b] c]"),
                new Test("a [[b] c [d]]","a[[ b] c[ d]]"),
                //premier espace doit etre post shifté et transformé en espace optionel ?
                //a,a c,a b c,b c
                //graphe d'implication de présence ? genre présence de a implique espace avant b, mais pas d'espace si pas de a

        };
        Test[] tests2 = {
                //a b c d,a c d, a d
                new Test("a [[b] c]","a[[ b] c]"),
                new Test("a [[b] c [d]]","a[[ b] c[ d]]"),        };
        int fail = 0;
        int succ = 0;
        for (Test test : tests1) {
            System.out.println("Testing "+test.test+" expecting "+test.result);
            SimpleRegex.Tree<SimpleRegex.PatternToken> expected = SimpleRegex.treefy(test.result);
            boolean success = false;
            try{
                SimpleRegex.Tree<SimpleRegex.PatternToken> tested = SimpleRegex.compile(SimpleRegex.treefy(test.test));
                success = tested.equals(expected);
                System.out.println(success +" "+ test.test+" -> "+SimpleRegex.convert(tested)+" \n");
            }catch(SimpleRegex.PatternAllOptionalException e){
                System.out.println("Pattern is all optional !");
                if(test.result.equals("error"))
                    success=true;
            }

            if(success)
                succ++;
            else
                fail++;
        }
        System.out.println("Fails : "+fail+", Success:"+succ);
    }

    public static class Test {
        public String test;
        public String result;
        public Test(String test, String result) {
            this.test = test;
            this.result = result;
        }
    }

}



