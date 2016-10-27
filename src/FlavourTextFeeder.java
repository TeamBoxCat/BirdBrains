import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FlavourTextFeeder {
    Random random = new Random();
    private ArrayList<String> trumpList = new ArrayList<>();
    private ArrayList<String> hillaryList = new ArrayList<>();

    public FlavourTextFeeder(){
        try{
            loadNames("src/data/TrumpQuotes.txt", trumpList);
            loadNames("src/data/HillaryQuotes.txt", hillaryList);
        }
        catch(Exception e){System.out.println("wasnt bothered");}
    }

    private void loadNames(String fileName, ArrayList<String> arrayList) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                arrayList.add(line);
                line = bufferedReader.readLine();
            }
        }
        finally {
            bufferedReader.close();
        }
    }

    public String getQuote(int candidate) {
        if (candidate == 0) {
            return hillaryList.get(random.nextInt(hillaryList.size()));
        }
        return trumpList.get(random.nextInt(trumpList.size()));
    }
}
