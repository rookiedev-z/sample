package net.gittab.basic.jvm;

/**
 * Hello.
 *
 * @author rookiedev 2020/10/13 15:05
 **/
public class Hello {

    public static void main(String[] args) throws Exception {
        int num1 = 8;
        int num2 = 4;
        Hello hello = new Hello();
        String[] operators = {"+", "-", "*", "/"};
        for (int i = 0; i < operators.length; i++) {
            int result = hello.calculate(num1, num2, operators[i]);
            System.out.println("the result of the operator '" + operators[i] + "' is " + result);
        }
    }

    private int calculate(int a, int b, String operator) throws Exception {
        if("+".equals(operator)){
            return a + b;
        }

        if("-".equals(operator)){
            return a - b;
        }

        if("*".equals(operator)){
            return a * b;
        }

        if("/".equals(operator)){
            if(b == 0){
                throw new Exception("Divisor cannot be 0");
            }
            return a / b;
        }
        throw new Exception("Illegal operator");
    }
}
