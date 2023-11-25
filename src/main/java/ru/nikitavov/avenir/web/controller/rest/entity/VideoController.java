package ru.nikitavov.avenir.web.controller.rest.entity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitavov.avenir.database.model.entity.TaskML;
import ru.nikitavov.avenir.database.repository.realisation.TaskMLRepository;
import ru.nikitavov.avenir.web.message.realization.enitiy.video.MlEndRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/video")
public class VideoController {

    private final TaskMLRepository taskMLRepository;

    public VideoController(TaskMLRepository taskMLRepository) {
        this.taskMLRepository = taskMLRepository;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> processVideo(@RequestPart("file") MultipartFile file) throws Exception {
        String[] split = file.getOriginalFilename().split("\\.");

        boolean isVideo = false;

        String prefix = split[1];
        if (prefix.equals("mp4")) isVideo = true;

        else if (!prefix.equals("jpg") || !prefix.equals("jpeg"))
            return ResponseEntity.badRequest().build();

        UUID uuid = UUID.randomUUID();
        Path tempFile = Files.createTempFile("temp_", "_" + uuid);
        TaskML taskML = taskMLRepository.save(TaskML.builder().dateCreate(new Date()).originalName(file.getOriginalFilename())
                .isVideo(isVideo).uuid(uuid).path(tempFile.toAbsolutePath().toString()).build());

        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);


        // Сохраните содержимое MultipartFile в временный файл

        // Здесь можно выполнить какие-либо операции с временным файлом, если необходимо

        // Удалите временный файл после использования
//        Files.delete(tempFile);


        String pythonServerUrl = "http://91.223.199.62:50121/check";

        // Создаем экземпляр RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Собираем URL с данными для GET-запроса
        String fullUrl = pythonServerUrl + "?file_url=" + taskML.getPath() + "&is_video=" + isVideo + "&task_id=" + taskML.getId();

        // Отправляем GET-запрос
        ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) throws IOException {
        TaskML taskML = taskMLRepository.findById(id).get();
        String path = taskML.getPath();
        Path filePath = Paths.get(path);

        byte[] fileContent = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", taskML.getOriginalName());

        return new ResponseEntity<>(fileContent, headers, org.springframework.http.HttpStatus.OK);
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> processVideo(@RequestBody MlEndRequest request) {
        return ResponseEntity.ok().build();
    }

}
