package ru.nikitavov.avenir.web.controller.rest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitavov.avenir.database.model.entity.Camera;
import ru.nikitavov.avenir.database.model.entity.CameraOffense;
import ru.nikitavov.avenir.database.model.entity.DetectionOffender;
import ru.nikitavov.avenir.database.repository.realisation.CameraOffenseRepository;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PageableResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.violation.FindsViolationRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.violation.ViolationShortInfoResponse;
import ru.nikitavov.avenir.web.util.SortUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("violation")
public class ViolationController {

    ExampleMatcher cameraMatcher = ExampleMatcher.matchingAny()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    private final CameraOffenseRepository cameraOffenseRepository;

    @GetMapping()
    public ResponseEntity<MessageWrapper<PageableResponse<List<ViolationShortInfoResponse>>>> getViolations(FindsViolationRequest request) {
        CameraOffense detectionElementExample = CameraOffense.builder().violationType(request.filter()).offender(DetectionOffender.builder().name(request.filter()).address(request.filter()).build()).camera(Camera.builder().id(request.cameraId()).build()).build();
        Example<CameraOffense> example = Example.of(detectionElementExample, cameraMatcher);
        Page<CameraOffense> cameras = cameraOffenseRepository.findAll(example,
                PageRequest.of(request.page(), request.size(), SortUtil.parseSort(request.sort())));

        List<ViolationShortInfoResponse> list = cameras.get()
                .map(offense -> new ViolationShortInfoResponse(offense.getId(), offense.getViolationType(), offense.getOffender().getAddress(), offense.getDateDetect(), offense.getCamera().getAddress(), offense.getCamera().getId()))
                .toList();

        return ResponseEntity.ok(MessageWrapper.create(new PageableResponse<>(list, cameras.getTotalPages(), cameras.getTotalElements())));


    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWrapper<ViolationShortInfoResponse>> getCamera(@PathVariable Integer id) {
        Optional<CameraOffense> optionalCameraOffense = cameraOffenseRepository.findById(id);
        if (optionalCameraOffense.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CameraOffense offense = optionalCameraOffense.get();

        ViolationShortInfoResponse userInfoResponse = new ViolationShortInfoResponse(offense.getId(), offense.getViolationType(), offense.getOffender().getAddress(), offense.getDateDetect(), offense.getCamera().getAddress(), offense.getCamera().getId());

        return ResponseEntity.ok(new MessageWrapper<>(userInfoResponse));
    }
}
