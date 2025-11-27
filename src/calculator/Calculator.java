package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * A basic calculator application that provides a GUI for performing arithmetic
 * and scientific calculations.
 * <p>This class contains inner classes implementing ActionListener to handle button actions
 * for each calculator function.
 * <p>The calculator uses the Swing library to manage GUI elements and action events.
 * @author David Liu
 */
public class Calculator {
    private JPanel mainPanel;
    private JTextField textField;
    private JButton signBtn;
    private JButton eightBtn;
    private JButton factorialBtn;
    private JButton divideBtn;
    private JButton mRecallBtn;
    private JButton mAddBtn;
    private JButton mSubBtn;
    private JButton powerBtn;
    private JButton logBtn;
    private JButton sqrBtn;
    private JButton nineBtn;
    private JButton mulBtn;
    private JButton fiveBtn;
    private JButton sixBtn;
    private JButton minusBtn;
    private JButton twoBtn;
    private JButton threeBtn;
    private JButton addBtn;
    private JButton digitBtn;
    private JButton equalBtn;
    private JButton mClearBtn;
    private JButton reciprocalBtn;
    private JButton clearBtn;
    private JButton sevenBtn;
    private JButton fourBtn;
    private JButton oneBtn;
    private JButton zeroBtn;
    private double memory; // for memory operations

    /**
     * NumberButtonListener Handles number button actions and updates the display with the clicked number, inheriting ActionListener
     */
    public static class NumberButtonListener implements ActionListener {
        private final JTextField display;
        private final String number;

        public NumberButtonListener(JTextField display, String number) {
            this.display = display;
            this.number = number;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (display.getText().equals("0")) {
                display.setText(number);  // Replace "0" with the number clicked
            }
            else {
                display.setText(display.getText() + number);  // Append number clicked
            }
        }
    }

    /**
     * DecimalButtonListener Handles the decimal point button action, inheriting ActionListener
     */
    public static class DecimalButtonListener implements ActionListener {
        private final JTextField display;

        public DecimalButtonListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // making sure that only one decimal point is added
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }
    }


    /**
     * SignListener Handles the sign change button action, inherits ActionListener
     */
    public static class SignListener implements ActionListener {
        private final JTextField display;

        public SignListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double value = Double.parseDouble(display.getText());
            value = -value;

            // typecasting x.0 double values to integer value
            // same process used for other operations
            if (value == (int) value) {
                display.setText(Integer.toString((int) value));
            }
            else {
                display.setText(Double.toString(value));
            }
        }
    }

    /**
     * OperationListener Handles the basic arithmetic operations / operations using '=' (+-/* and ^), inherits ActionListener
     */
    public static class OperationListener implements ActionListener {
        private final JTextField display;
        private double firstOperand = 0;
        private String operator = "";
        private static final DecimalFormat decimalFormat = new DecimalFormat("#.#######################"); // for floating point issues

        public OperationListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // getting button pressed
            JButton source = (JButton) e.getSource();
            String op = source.getText();

            // stores the second operand when '=' is pressed, always occurs after 1st operand is stored
            if (op.equals("=")) {
                double secondOperand = Double.parseDouble(display.getText());
                double result = 0;

                // performs operation based on which operation was pressed (String operator)
                switch (operator) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "x^y":
                        // Negative base with a fractional exponent is undefined -> outputs NaN, handles this case
                        if (firstOperand < 0 && secondOperand != (int) secondOperand) {
                            display.setText("ERROR");
                            return;
                        }
                        result = Math.pow(firstOperand, secondOperand);
                        break;
                    case "/":
                        // cannot divide 2 numbers if second number is 0
                        if (secondOperand == 0) {
                            display.setText("ERROR");
                            return;
                        }
                        result = firstOperand / secondOperand;
                        break;
                }

                // Check if result is a whole number by typecasting
                if (result == (int) result) {
                    display.setText(String.valueOf((int) result)); // Display as integer
                }
                else {
                    display.setText(decimalFormat.format(result)); // Display as double
                }

            }
            // getting first operand + storing operation as default
            else {
                firstOperand = Double.parseDouble(display.getText());
                operator = op;
                display.setText("");  // Clear display for the next operand
            }
        }
    }

    /**
     * FactorialListener Handles the factorial button action, inherits ActionListener
     */
    public static class FactorialListener implements ActionListener {
        private final JTextField display;

        public FactorialListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int number = Integer.parseInt(display.getText()); // Factorial only works for integers
            if (number < 0 || number > 10) { // Restricting to non-negative and reasonable range
                display.setText("ERROR");
                return;
            }
            // factorial function, w/ base case i = 0 being 1.
            int result = 1;
            for (int i = 1; i <= number; i++) {
                result *= i;
            }
            display.setText(String.valueOf(result));
        }
    }


    /**
     * LogListener Handles the natural log button action, inherits ActionListener
     */
    public static class LogListener implements ActionListener {
        private final JTextField display;

        public LogListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double value = Double.parseDouble(display.getText());
            if (value <= 0) {
                display.setText("ERROR"); // undefined for zero or negative numbers
            }
            else {
                double result = Math.log(value);
                // typecasting
                if (result == (int) result) {
                    display.setText(String.valueOf((int) result)); // Display as integer
                }
                else {
                    display.setText(Double.toString(result)); // Display as double
                }
            }
        }
    }

    /**
     * ReciprocalListener Handles the reciprocal button action, inherits ActionListener
     */
    public static class ReciprocalListener implements ActionListener {
        private final JTextField display;

        public ReciprocalListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double value = Double.parseDouble(display.getText());
            // 1/0 not possible, display error
            if (value == 0) {
                display.setText("ERROR");
            }
            else {
                double result = 1 / value;
                // typecasting
                if ((result) == (int) result) {
                    display.setText(String.valueOf((int) result)); // Display as integer
                } else {
                    display.setText(Double.toString(result)); // Display as double
                }
            }
        }
    }

    /**
     * SquareListener Handles the square root button action, inherits ActionListener
     */
    public static class SquareListener implements ActionListener {
        private final JTextField display;

        public SquareListener(JTextField display) {
            this.display = display;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double value = Double.parseDouble(display.getText());
            // cannot square root a negative number
            if (value < 0) {
                display.setText("ERROR");
            }
            else {
                double result = Math.sqrt(value);
                // typecasting
                if (result == (int) result) {
                    display.setText(Integer.toString((int) result)); // Display as integer
                }
                else {
                    display.setText(Double.toString(result)); // Display as double
                }
            }
        }
    }



    /**
     * Constructs Calculator object and initializes Event Handlers for Each Button
     * Memory buttons are handled with 'memory' variable, other buttons handled with inheritance classes above
     */
    public Calculator() {
        // initialization
        if (textField.getText().isEmpty()) {
            textField.setText("0");
        }

        // Number buttons
        zeroBtn.addActionListener(new NumberButtonListener(textField, "0"));
        oneBtn.addActionListener(new NumberButtonListener(textField, "1"));
        twoBtn.addActionListener(new NumberButtonListener(textField, "2"));
        threeBtn.addActionListener(new NumberButtonListener(textField, "3"));
        fourBtn.addActionListener(new NumberButtonListener(textField, "4"));
        fiveBtn.addActionListener(new NumberButtonListener(textField, "5"));
        sixBtn.addActionListener(new NumberButtonListener(textField, "6"));
        sevenBtn.addActionListener(new NumberButtonListener(textField, "7"));
        eightBtn.addActionListener(new NumberButtonListener(textField, "8"));
        nineBtn.addActionListener(new NumberButtonListener(textField, "9"));

        // decimal point button
        digitBtn.addActionListener(new DecimalButtonListener(textField));

        // +/- button
        signBtn.addActionListener(new SignListener(textField)); // +/- button

        // operations needing "=" button
        OperationListener operationListener = new OperationListener(textField);
        addBtn.addActionListener(operationListener);
        minusBtn.addActionListener(operationListener);
        mulBtn.addActionListener(operationListener);
        divideBtn.addActionListener(operationListener);
        equalBtn.addActionListener(operationListener);
        powerBtn.addActionListener(operationListener);

        // additional operations (factorial, natural log, reciprocal, square root)
        factorialBtn.addActionListener(new FactorialListener(textField));
        logBtn.addActionListener(new LogListener(textField));
        reciprocalBtn.addActionListener(new ReciprocalListener(textField));
        sqrBtn.addActionListener(new SquareListener(textField));

        // MC
        mClearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memory = 0;
            }
        });

        // AC
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("0"); // clearing display, does not affect memory
            }
        });

        // M+
        mAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentValue = Double.parseDouble(textField.getText());
                memory += currentValue; // Add display value to memory
            }
        });

        // M-
        mSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double currentValue = Double.parseDouble(textField.getText());
                memory -= currentValue; // remove display value from memory
            }
        });

        // MR
        mRecallBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(Double.toString(memory));
                // typecasting so recall does not give double value of integer value
                if (memory == (int) memory) {
                    textField.setText(Integer.toString((int) memory));
                }
                else {
                    textField.setText(Double.toString(memory));
                }
            }
        });
    }

    /**
     * Test method for simulating button clicks in a sequence provided in assignment details
     * @param button presses buttons created by "Calculator.form" used for testing
     */
    public void test(String button) {
        switch (button) {
            case "0":
                zeroBtn.doClick();
                break;
            case "1":
                oneBtn.doClick();
                break;
            case "2":
                twoBtn.doClick();
                break;
            case "3":
                threeBtn.doClick();
                break;
            case "4":
                fourBtn.doClick();
                break;
            case "5":
                fiveBtn.doClick();
                break;
            case "6":
                sixBtn.doClick();
                break;
            case "7":
                sevenBtn.doClick();
                break;
            case "8":
                eightBtn.doClick();
                break;
            case "9":
                nineBtn.doClick();
                break;
            case "x!":
                factorialBtn.doClick();
                break;
            case "-/+":
                signBtn.doClick();
                break;
            case "AC":
                clearBtn.doClick();
                break;
            case "1/x":
                reciprocalBtn.doClick();
                break;
            case "sqr":
                sqrBtn.doClick();
                break;
            case "log":
                logBtn.doClick();
                break;
            case ".":
                digitBtn.doClick();
                break;
            case "+":
                addBtn.doClick();
                break;
            case "-":
                minusBtn.doClick();
                break;
            case "*":
                mulBtn.doClick();
                break;
            case "/":
                divideBtn.doClick();
                break;
            case "**":
                powerBtn.doClick();
                break;
            case "=":
                equalBtn.doClick();
                break;
            case "MR":
                mRecallBtn.doClick();
                break;
            case "MC":
                mClearBtn.doClick();
                break;
            case "M-":
                mSubBtn.doClick();
                break;
            case "M+":
                mAddBtn.doClick();
                break;
            case "txt":
                System.out.println("The result is: " + textField.getText());
                break;
            default:
                System.out.println("invalid input");
                break;
        }
    }

    /**
     * Main method to launch the calculator GUI and perform test operations.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 400);
        frame.setVisible(true);

        Calculator myCal = new Calculator();
    }
}