import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // Loads .env from the current working directory

        String apiKey = dotenv.get("OPENAI_API_KEY");

        System.out.println("API Key: " + apiKey);
    }

}
