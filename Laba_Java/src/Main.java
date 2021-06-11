public class Main {
    public static void main(String[] args) {
        DataTable dat = new DataTable();
        new Lab8Thread(1, dat).start();
        new Lab8Thread(2, dat).start();
        new Lab8Thread(3, dat).start();
        Login rg = new Login("Login");
    }
}