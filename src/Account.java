import java.util.ArrayList;

public class Account {

    private String name;
    private String accountId;
    private UserAccount owner;
    private ArrayList<Transaction> transactions;

    public Account(String name, String accountId, UserAccount owner, ArrayList<Transaction> transactions) {
        this.name = name;
        this.accountId = accountId;
        this.owner = owner;
        this.transactions = transactions;
    }
}
