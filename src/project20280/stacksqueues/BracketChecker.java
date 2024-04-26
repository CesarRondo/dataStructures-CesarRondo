package project20280.stacksqueues;

/**
 * Cesar Rondo Rodriguez
 * 22779089
 */

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        // TODO
        ArrayStack<Character> stack = new ArrayStack<>();
        for(char c: input.toCharArray()){

            if(c=='[' || c=='{' || c=='('){
                stack.push(c);
            }
            if (stack.isEmpty()) {
                System.out.println("not correct");
                return;
            }
            char top = stack.pop();
            if(c==']' || c=='}' || c==')'){
                if (stack.isEmpty()) {
                    System.out.println("no! Missing right delimiter in: " + input);
                    return;
                }
                else if(c==')' && top!='('){
                    System.out.println("not correct");
                    return;
                }
                else if(c==']' && top!='['){
                    System.out.println("not correct");
                    return;
                }
                else if(c=='}' && top!='{'){
                    System.out.println("not correct");
                    return;
                }
            }

            if (!stack.isEmpty()) {
                System.out.println("not correct");
                return;
            }
            System.out.println("correct");
        }
    }


    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
        }
    }
}