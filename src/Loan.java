import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loan {

    Connex connex = new Connex();
    Connection connection;

    public Loan(int account, double amount, int term, int loanType) throws SQLException {
        connection = connex.connects();
        int userScore = getUserScore(account);
        double scoreFee = getFeeScore(userScore);
        double termFee = getFeeTerm(term);
        double totalFee = scoreFee + termFee;


    }

    private int getUserScore(int account) throws SQLException {
        int userScore = 0;

        String sql = "SELECT userScore from useraccount WHERE accountId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, account);
        ResultSet result = stmt.executeQuery();
        if (result.next())
            userScore = result.getInt(1);

        return userScore;
    }

    private double getFeeScore(int score){
        double loanFee;

        if (score < 650)
            loanFee = 0.035;
        else if (score < 750)
            loanFee = 0.025;
        else if (score < 810)
            loanFee = 0.015;
        else
            loanFee = 0.005;

        return loanFee;
    }

    private double getFeeTerm(int months)
    {
        double feeTerm;

        if (months < 12)
            feeTerm = 0.04;
        else if (months < 24)
            feeTerm = 0.05;
        else if (months < 48)
            feeTerm = 0.06;
        else if (months < 120)
            feeTerm = 0.07;
        else
            feeTerm = 0.08;

        return feeTerm;
    }
}
