package interview2026.mockprep.streams;

public class Trade {
    String id;
    String status;
    double amount;

    public Trade(String id, String status, double amount) {
        this.id = id;
        this.status = status;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public double getAmount() { return amount; }
}
