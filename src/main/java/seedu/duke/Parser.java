package seedu.duke;

public class Parser {
    protected String userInput;

    public static class EndProgramException extends Exception {

    }

    public Parser(String userInput) {
        this.userInput = userInput;
    }

    public void handleUserInput() throws EndProgramException, ExpensesException {
        String[] tokens = userInput.split(" ", 2);

        String command = tokens[0].toLowerCase().trim();
        String argument = "";
        if (tokens.length > 1) {
            argument = tokens[1].trim();
        }

        switch (command) {
        case "bye":
            throw new EndProgramException();
        case "exit":
            GroupCommand.exitGroup();
            break;
        case "help":
            // Help code here
            Help.printHelp();
            break;
        case "create":
            GroupCommand.createGroup(argument);
            break;
        case "member":
            GroupCommand.addMember(argument);
            break;
        case "expense":
            try {
                String[] removeExpenseTag = argument.split("/amount");
                if (removeExpenseTag.length == 1) {
                    throw new ExpensesException("No description for expenses! Add /amount /paid /user");
                }
                String[] extractAmount = removeExpenseTag[1].split("/paid");
                String amount = extractAmount[0];
                amount = removeWhitespaces(amount);

                try {
                    float totalAmount = Float.parseFloat(amount);
                    String[] extractPayer = extractAmount[1].split("/user");
                    String payerName = extractPayer[0];
                    Expense newTransaction = new Expense(payerName,totalAmount,extractPayer);
                } catch (NumberFormatException e) {
                    System.out.println("Re-enter expense with amount as a proper number.");
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Empty /amount, /paid or /user. Add expenses using the correct format.");
            }
            break;
        case "list":
            // List code here
            break;
        case "balance":
            // Balance code here
            break;
        default:
            // Default clause
            break;
        }
    }
    private String removeWhitespaces(String item) {
        String itemWithoutWhitespaces = item.replaceAll("\\s+", " ").trim();
        return itemWithoutWhitespaces;
    }
}
