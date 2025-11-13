package controller;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import model.ContentModel;
import model.SessionModel;
import model.StrategyFactory;
import org.json.JSONObject;
import service.ErrorHandler;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class SpeechConverter  {

    private ContentModel model;
    private static final String DEFAULT_EXTENSION = ".json";
    File defaultDirectory = new File("saves");
    private boolean toggle = false;
    private Configuration configuration = new Configuration();
    private LiveSpeechRecognizer recognizer;
    public SpeechConverter(ContentModel model) throws IOException {
        this.model = model;
        // Set path to acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to dictionary.
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        // Set language model.
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        recognizer = new LiveSpeechRecognizer(configuration);
        recognizer.startRecognition(true);
        recognizer.stopRecognition();
    }

    public void speechToText() throws LineUnavailableException, IOException {
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(false);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null && toggle) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
            model.appendInputText(result.getHypothesis());
        }
        // Pause recognition process. It can be resumed then with startRecognition(false).
        recognizer.stopRecognition();
    }

    public void setToggle(boolean toggle){this.toggle = toggle;}
    public void setToggle(){toggle = !toggle;}
    public boolean getToggle(){return toggle;}


}
