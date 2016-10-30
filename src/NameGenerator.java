import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class NameGenerator {
    Random random = new Random();
    private ArrayList<String> suffixList = new ArrayList<>();
    private ArrayList<String> prefixList = new ArrayList<>();

    public NameGenerator() throws Exception{
        loadNames("./data/suffixes.txt", suffixList);
        loadNames("./data/prefixes.txt", prefixList);
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

    public String getName() {
        if (random.nextInt(6) == 3) {
            return "@" + getPrefix().toLowerCase();
        }
        return "@" + checkMakeLower(getPrefix() + getSeparator() +getNoun());
    }

    private String getNoun() {
        return suffixList.get(random.nextInt(suffixList.size()));
    }

    private String getPrefix() {
        return prefixList.get(random.nextInt(prefixList.size()));
    }

    private String getSeparator() {
        if (random.nextBoolean()) {
            return "_";
        }
        return "";
    }

    private String checkMakeLower(String name) {
        if (random.nextBoolean() && hasChar(name)) {
            return name.toLowerCase();
        }
        return name;
    }

    private boolean hasChar(String name) {
        return name.contains("_");
    }
}
