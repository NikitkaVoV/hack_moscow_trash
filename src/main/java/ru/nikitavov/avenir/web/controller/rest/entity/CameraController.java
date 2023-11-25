package ru.nikitavov.avenir.web.controller.rest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitavov.avenir.database.model.entity.Camera;
import ru.nikitavov.avenir.database.model.entity.CameraOffense;
import ru.nikitavov.avenir.database.repository.realisation.CameraRepository;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.enitiy.camera.CameraInfoResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.camera.CameraShortInfoResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.FindsRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PageableResponse;
import ru.nikitavov.avenir.web.util.SortUtil;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("camera")
public class CameraController {

    private final CameraRepository cameraRepository;

    ExampleMatcher cameraMatcher = ExampleMatcher.matchingAny()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    @GetMapping
    public ResponseEntity<MessageWrapper<PageableResponse<List<CameraShortInfoResponse>>>> getCameras(FindsRequest request) {
        Camera cameraExample = Camera.builder().name(request.filter()).address(request.filter()).build();
        Example<Camera> example = Example.of(cameraExample, cameraMatcher);
        Page<Camera> cameras = cameraRepository.findAll(example,
                PageRequest.of(request.page(), request.size(), SortUtil.parseSort(request.sort())));

        List<CameraShortInfoResponse> list = cameras.get()
                .map(camera -> new CameraShortInfoResponse(camera.getId(), camera.getName(), camera.getAddress(), camera.getCameraOffenses().size(), new Coordinate(camera.getPoint().getLat(), camera.getPoint().getLon())))
                .toList();

        return ResponseEntity.ok(MessageWrapper.create(new PageableResponse<>(list, cameras.getTotalPages(), cameras.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWrapper<CameraInfoResponse>> getCamera(@PathVariable Integer id) {
        Optional<Camera> cameraOptional = cameraRepository.findById(id);
        if (cameraOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Camera camera = cameraOptional.get();

        Set<CameraOffense> cameraOffenses = camera.getCameraOffenses();
        CameraInfoResponse cameraInfoResponse;

        if (cameraOffenses.isEmpty()) {
            cameraInfoResponse = new CameraInfoResponse(camera.getId(), camera.getName(), camera.getAddress(), camera.getCameraUrl(), 0,
                    null, "", new CameraInfoResponse.CameraDetectionElementResponse[0], new Coordinate(camera.getPoint().getLat(), camera.getPoint().getLon()));
        } else {
            CameraOffense cameraOffense = cameraOffenses.stream().max(Comparator.comparingInt(CameraOffense::getId)).get();

            cameraInfoResponse = new CameraInfoResponse(camera.getId(), camera.getName(), camera.getAddress(), camera.getCameraUrl(), cameraOffenses.size(),
                    cameraOffense.getDateDetect(), cameraOffense.getOffender().getName(), new CameraInfoResponse.CameraDetectionElementResponse[0], new Coordinate(camera.getPoint().getLat(), camera.getPoint().getLon()));

        }

        return ResponseEntity.ok(new MessageWrapper<>(cameraInfoResponse));
    }
}
