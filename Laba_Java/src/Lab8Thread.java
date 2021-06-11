

public class Lab8Thread extends Thread {
    private static DataTable shared;
    private int type;

    public Lab8Thread(int i, DataTable sharedData)
    {
        shared = sharedData;
        type=i;
    }

    void Op1()
    {
        shared.lines = new XMLImporter().ReadData().lines;
    }

    void Op2()
    {
        shared.lines.add(new DataTable.Line(new Object[]{
                "x", "threading", "testing", "attempt", "one", "."}));
    }

    void Op3()
    {
        new PDFExporter(shared);
        new HTMLExporter(shared);
    }

    public void run() {
        if (type==1) {
            synchronized (shared) {
                Op1();
                shared.notifyAll();
            }
        }
        if (type==2) {
            synchronized (shared) {
                if(shared.lines.size()==0)
                    try {
                        shared.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                Op2();
                shared.notifyAll();
            }
        }

        if (type==3) {
            synchronized (shared) {
                try {
                    shared.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Op3();
            }
        }
    }
}