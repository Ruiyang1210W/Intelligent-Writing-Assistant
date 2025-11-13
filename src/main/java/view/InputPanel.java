package view;

import controller.SpeechConverter;
import model.ContentModel;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Panel for user input
 * Implements Observer Pattern to listen to model changes
 */
public class InputPanel extends JPanel implements PropertyChangeListener{

    private ContentModel model;
    private SpeechConverter speechConverter;
    private JTextArea inputArea;
    private JLabel charCountLabel;
    private JButton voiceInput;

    public InputPanel(ContentModel model, SpeechConverter speechConverter) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.speechConverter = speechConverter;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Input"));

        initializeComponents();
    }

    private void initializeComponents() {
        // Text area for input
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Listen to text changes and update model
        inputArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                model.setInputText(inputArea.getText());
                updateCharCount();
            }
        });

        /*SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(true){
                    String newText = (String) model.getInputText();
                    if (!inputArea.getText().equals(newText)) {
                        inputArea.setText(newText);
                        updateCharCount();
                    }
                }
            }
        };
        worker.execute(); */
        JPanel footer = new JPanel(new BorderLayout());
        voiceInput = new JButton("microphone");
        voiceInput.addActionListener(e -> {
            speechConverter.setToggle();
            try {
                textToSpeechToggle();
            } catch (LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        voiceInput.setHorizontalAlignment(SwingConstants.LEFT);
        voiceInput.setSize(10,10);
        footer.add(voiceInput, BorderLayout.WEST);

        // Character count label
        charCountLabel = new JLabel("Characters: 0");
        charCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        footer.add(charCountLabel, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(inputArea);
        add(scrollPane);
    }

    /**
     * Update character count label
     */
    private void updateCharCount() {
        int count = inputArea.getText().length();
        charCountLabel.setText("Characters: " + count);
    }

    /**
     * Observer Pattern - respond to model changes
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("inputText".equals(evt.getPropertyName())) {
            String newText = (String) evt.getNewValue();
            if (!inputArea.getText().equals(newText)) {
                inputArea.setText(newText);
                updateCharCount();
            }
        }
    }

    public void textToSpeechToggle() throws LineUnavailableException, IOException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (speechConverter.getToggle()) {
                    // start speech recognition from microphone
                    System.out.println("Microphone ON");
                    voiceInput.setText("Stop Listening");
                    try {
                        speechConverter.speechToText();
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Logic to STOP speech recognition
                    System.out.println("Microphone OFF");
                    voiceInput.setText("Start Listening");
                    // Signal the audio capture thread to stop
                }
            }
        });
        backgroundThread.start();
    }


}

