public class Main {

    public static void main(String[] args) throws Exception {
        Test[] tests = {
                new Test("a [b] c","a[ b] c"),
                new Test("[a] [b] c","[a ][b ]c"),
                new Test("a b [c]","a b[ c]"),
                new Test("a[b] c","a[b] c"),
                new Test("[a]b c","[a]b c"),
                new Test("a [b]c","a [b]c"),
                new Test("a b[c]","a b[c]"),
                new Test("a [[b] c] d","a[[b ]c] d"),
                new Test("a [b] [c] d","a[ b][ c] d")
        };
        int fail = 0;
        int succ = 0;
        for (Test test : tests) {
            SimpleRegex.Tree<SimpleRegex.PatternToken> tested = SimpleRegex.compile(SimpleRegex.treefy(test.test));
            SimpleRegex.Tree<SimpleRegex.PatternToken> expected = SimpleRegex.treefy(test.result);
            boolean success = tested.equals(expected);
            if(success)
                succ++;
            else
                fail++;
            System.out.println(success +" "+ test.test+" -> "+SimpleRegex.convert(tested));
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



