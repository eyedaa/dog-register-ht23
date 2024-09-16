import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader {



    private static ArrayList<InputStream> streams = new ArrayList<>();
    private  InputStream inputStream;

    private final Scanner scanner;


    // Konstruktor 1: utan parameter, anrop den andra konstruktor med 'System.in' som argument.
    public InputReader() {
        this(System.in);
    }

    // Konstruktor 2: med 'inputstream' som parameter och initialiserar 'Scanner
    public InputReader(InputStream inputStreamer) {
        // IllegalStateException
        //if(inputStreamer == inputStream && inputStreamer != null)
        if (streams.contains(inputStreamer)) { // Kontroll av redan existerade ström
            throw new IllegalStateException("Error: Input is already in use"); // om den finnsi listan skrivs detta ut.
        } else {
            this.scanner = new Scanner(inputStreamer); // Skapar en ny Scanner kopplad till inputStream för att läsa data
            inputStream = inputStreamer; // Tilldelar den inkommande inputStream till instansvariabeln inputStream
            streams.add(inputStreamer); // lagrars i instansvaribeln 'strem'.
        }
    }

    public int readInt(String prompt) {
        System.out.print(prompt + "?> ");
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;

    }

    public String readString(String prompt) {
        System.out.print(prompt + "?> ");
        //String input = scanner.nextLine();
        return scanner.nextLine();
    }

    public double readDouble(String prompt) {
        System.out.print(prompt + "?> ");
        double input = scanner.nextDouble();
        scanner.nextLine();

        return input;
    }
}
