package com.eazybytes.springai.controller;

import com.openai.models.audio.AudioResponseFormat;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioController {

  private final TranscriptionModel transcriptionModel;

  public AudioController(TranscriptionModel transcriptionModel) {
    this.transcriptionModel = transcriptionModel;
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
}
