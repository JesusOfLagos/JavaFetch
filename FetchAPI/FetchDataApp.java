import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class FetchDataApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("JSON Data Fetching");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton fetchButton = new JButton("Fetch Data");
        fetchButton.addActionListener(e -> {
            fetchAndDisplayData(textArea);
        });

        frame.getContentPane().add(fetchButton, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private static void fetchAndDisplayData(JTextArea textArea) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                } else {
                    result.append("Error: Unable to fetch data. Response code: ").append(responseCode);
                }
            } catch (IOException ex) {
                result.append("Error: ").append(ex.getMessage());
            }
            return result.toString();
        });

        executor.shutdown();

        try {
            String data = future.get();
            textArea.setText(data);
            if (data.startsWith("Error")) {
                JOptionPane.showMessageDialog(null, data, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data fetching completed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
