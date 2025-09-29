
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lohnrechner extends JFrame {

    private JRadioButton basicRadio, seniorRadio;
    private JTextField stundenField, vertretungenField, zusatzField, wochenendeField, geteilteKurseField;
    private JComboBox<String> einheitBox;
    private JLabel resultLabel;

    public Lohnrechner() {
        setTitle("Lohnrechner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLayout(new GridLayout(9, 2, 5, 5));

        // Hintergrundfarbe fürs Fenster
        getContentPane().setBackground(new Color(230, 240, 255));

        // Tarif-Auswahl
        JLabel tarifLabel = new JLabel("Tarif:");
        tarifLabel.setForeground(Color.DARK_GRAY);
        add(tarifLabel);

        JPanel tarifPanel = new JPanel();
        tarifPanel.setBackground(new Color(200, 220, 255));
        basicRadio = new JRadioButton("Basic (15 €/h)", true);
        seniorRadio = new JRadioButton("Senior (18 €/h)");
        ButtonGroup tarifGroup = new ButtonGroup();
        tarifGroup.add(basicRadio);
        tarifGroup.add(seniorRadio);
        tarifPanel.add(basicRadio);
        tarifPanel.add(seniorRadio);
        add(tarifPanel);

        // Stunden/Kurse
        add(new JLabel("Anzahl:"));
        stundenField = new JTextField();
        add(stundenField);

        add(new JLabel("Einheit:"));
        einheitBox = new JComboBox<>(new String[]{"Stunden", "Kurse"});
        add(einheitBox);

        // Vertretungen
        add(new JLabel("Vertretungen:"));
        vertretungenField = new JTextField();
        add(vertretungenField);

        // Zusatzaufgaben
        add(new JLabel("Zusatzaufgaben:"));
        zusatzField = new JTextField();
        add(zusatzField);

        // Wochenendstunden
        add(new JLabel("Wochenendstunden:"));
        wochenendeField = new JTextField();
        add(wochenendeField);

        // Geteilte Kurse
        add(new JLabel("Aufgeteilten Kurse:"));
        geteilteKurseField = new JTextField();
        add(geteilteKurseField);

        // Button
        JButton berechnenButton = new JButton("Berechnen");
        berechnenButton.setBackground(new Color(70, 130, 180));
        berechnenButton.setForeground(Color.WHITE);
        add(berechnenButton);

        // Ergebnis
        resultLabel = new JLabel("Gesamtlohn: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(new Color(0, 100, 0));
        add(resultLabel);

        // ActionListener
        berechnenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                berechneLohn();
            }
        });

        setVisible(true);
    }

    private void berechneLohn() {
        try {
            // Tarif
            double stundenlohn = basicRadio.isSelected() ? 15.0 : 18.0;

            // Anzahl Stunden/Kurse
            int anzahl = Integer.parseInt(stundenField.getText());
            String einheit = (String) einheitBox.getSelectedItem();

            double lohn = 0.0;
            if (einheit.equals("Stunden")) {
                lohn = anzahl * stundenlohn;
            } else { // Kurse
                lohn = anzahl * (stundenlohn * 0.75);
            }

            // Vertretungen
            int vertretungen = Integer.parseInt(vertretungenField.getText());
            double bonus = 0.0;
            if (vertretungen >= 7) {
                bonus = vertretungen * 10;
            } else if (vertretungen == 6) {
                bonus = 60;
            } else if (vertretungen >= 3) {
                bonus = 30;
            }

            // Zusatzaufgaben
            int zusatz = Integer.parseInt(zusatzField.getText());
            double zusatzBonus = zusatz * 3.5;

            // Wochenendstunden
            int wochenende = Integer.parseInt(wochenendeField.getText());
            double weekendBonus = wochenende * 2;

            // Geteilte Kurse
            int geteilte = Integer.parseInt(geteilteKurseField.getText());
            double geteiltBonus = geteilte * 3 * (stundenlohn / 2);

            // Gesamt
            double gesamt = lohn + bonus + zusatzBonus + weekendBonus + geteiltBonus;
            resultLabel.setText("Gesamtlohn: " + String.format("%.2f €", gesamt));

        } catch (NumberFormatException ex) {
            resultLabel.setText("Bitte gültige Zahlen eingeben.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lohnrechner::new);
    }
}
