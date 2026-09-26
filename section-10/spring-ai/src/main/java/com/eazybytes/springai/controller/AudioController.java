package com.eazybytes.springai.controller;

import com.openai.models.audio.AudioResponseFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.audio.tts.TextToSpeechOptions;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions.Voice;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioController {

  private final TranscriptionModel transcriptionModel;
  private final TextToSpeechModel textToSpeechModel;

  private static final DateTimeFormatter FILE_NAME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

  public AudioController(TranscriptionModel transcriptionModel,
      TextToSpeechModel textToSpeechModel) {
    this.transcriptionModel = transcriptionModel;
    this.textToSpeechModel = textToSpeechModel;
  }

  @GetMapping("/transcribe")
  String transcribe(@Value("classpath:SpringAI.mp3") Resource audioFile) {
    AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioFile);
    AudioTranscriptionResponse response = transcriptionModel.call(prompt);
    return response.getResult().getOutput();
  }

  @GetMapping("/transcribe-options")
  String transcribeWithOptions(@Value("classpath:SpringAI.mp3") Resource audioFile) {
    AudioTranscriptionResponse response = transcriptionModel.call(
        new AudioTranscriptionPrompt(audioFile, OpenAiAudioTranscriptionOptions.builder()
            .prompt("Talking about Spring AI")
            .language("en")
            .temperature(0.5f)
            .responseFormat(AudioResponseFormat.VTT)
            .model("whisper-1")
            .build()));
    return response.getResult().getOutput();
  }

  @GetMapping("/speech")
  String speech(@RequestParam("message") String message) throws IOException {
    byte[] audioBytes = textToSpeechModel.call(message);
    Path path = Paths.get(buildFileName("speech"));
    Files.createDirectories(path.getParent());
    Files.write(path, audioBytes);
    return "MP3 saved. successfully to " + path.toAbsolutePath();
  }

  @GetMapping("/speech-options")
  String speechWithOptions(@RequestParam("message") String message) throws IOException {
    TextToSpeechResponse speechResponse = textToSpeechModel.call(
        new TextToSpeechPrompt(message, TextToSpeechOptions
            .builder()
            .voice(Voice.BALLAD.getValue())
            .speed(1.0)
            .format(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3.getValue())
            .model("gpt-4o-mini-tts")
            .build()));
    Path path = Paths.get(buildFileName("speech-options"));
    Files.createDirectories(path.getParent());
    Files.write(path, speechResponse.getResult().getOutput());
    return "MP3 saved. successfully to " + path.toAbsolutePath();
  }

  private String buildFileName(String fileName) {
    return "audios/" + fileName + "-" + LocalDateTime.now().format(FILE_NAME_FORMATTER) + ".mp3";
  }
}
