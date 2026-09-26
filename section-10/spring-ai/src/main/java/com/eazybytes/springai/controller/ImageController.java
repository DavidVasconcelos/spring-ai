package com.eazybytes.springai.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

  private final ImageModel imageModel;

  private static final DateTimeFormatter FILE_NAME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

  public ImageController(ImageModel imageModel) {
    this.imageModel = imageModel;
  }

  @GetMapping("/image")
  String generateImage(@RequestParam("message") String message) throws IOException {
    ImageResponse imageResponse = imageModel.call(new ImagePrompt(message));
    return saveImage(imageResponse, "image");
  }

  @GetMapping("/image-options")
  String generateImageWithOptions(@RequestParam("message") String message) throws IOException {
    ImageResponse imageResponse = imageModel.call(new ImagePrompt(message,
        OpenAiImageOptions.builder()
            .n(1)
            .model("gpt-image-2.5-flare")
            .build()));

    return saveImage(imageResponse, "image-options");
  }

  private String saveImage(ImageResponse imageResponse, String filePrefix) throws IOException {
    String b64Json = imageResponse.getResults().getFirst().getOutput().getB64Json();
    byte[] imageBytes = Base64.getDecoder().decode(b64Json);
    Path path = Paths.get(buildFileName(filePrefix));
    Files.createDirectories(path.getParent());
    Files.write(path, imageBytes);

    return "Image saved successfully to " + path.toAbsolutePath().normalize();
  }

  private String buildFileName(String fileName) {
    return "images/" + fileName + "-" + LocalDateTime.now().format(FILE_NAME_FORMATTER) + ".png";
  }
}