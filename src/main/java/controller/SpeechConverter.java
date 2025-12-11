package controller;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import model.ContentModel;
import model.SessionModel;
import model.StrategyFactory;
import org.json.JSONObject;
import service.ErrorHandler;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

public class SpeechConverter  {
    private ContentModel model;
    private static final String DEFAULT_EXTENSION = ".json";
    File defaultDirectory = new File("saves");
    private boolean toggle = false;
    private Configuration configuration = new Configuration();
    private LiveSpeechRecognizer recognizer;
    public Player player;
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

    public void textToSpeech(){
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(model.getOutputText())
                    .build();

            // Build the voice request
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US") // Language code of the voice
                    .setSsmlGender(SsmlVoiceGender.FEMALE) // Gender of the voice
                    .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3) // Audio encoding (e.g., MP3)
                    .build();

            // Perform the text-to-speech request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            //Write the response to the output file
            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContents.toByteArray());
            }
            FileInputStream fis = new FileInputStream("output.mp3"); // Replace with your MP3 file path
            player = new Player(fis);
            player.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    public void setToggle(boolean toggle){this.toggle = toggle;}
    public void setToggle(){toggle = !toggle;}
    public boolean getToggle(){return toggle;}


}
