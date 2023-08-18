import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchDataWithDialog {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fetched Data Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);

            fetchAndDisplayData(textArea);

            JOptionPane.showMessageDialog(frame, "Data fetching is done!");
        });
    }

    private static void fetchAndDisplayData(JTextArea textArea) {
      
    }
}
