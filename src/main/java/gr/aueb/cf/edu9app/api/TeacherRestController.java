package gr.aueb.cf.edu9app.api;

import gr.aueb.cf.edu9app.core.exceptions.*;
import gr.aueb.cf.edu9app.dto.TeacherInsertDTO;
import gr.aueb.cf.edu9app.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.edu9app.service.ITeacherService;
import gr.aueb.cf.edu9app.validator.TeacherInsertValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherRestController {

    private final ITeacherService teacherService;
    private final TeacherInsertValidator teacherInsertValidator;

    @PostMapping
    public ResponseEntity<TeacherReadOnlyDTO> saveTeacher(
            @Valid @RequestBody TeacherInsertDTO teacherInsertDTO,
            BindingResult bindingResult) throws EntityAlreadyExistsException,
            EntityInvalidArgumentException, ValidationException {

        teacherInsertValidator.validate(teacherInsertDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Teacher", "Invalid teacher data", bindingResult);
        }

        TeacherReadOnlyDTO teacherReadOnlyDTO = teacherService.saveTeacher(teacherInsertDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(teacherReadOnlyDTO.uuid())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(teacherReadOnlyDTO);
    }

    @PostMapping("/{uuid}/amka-file")
    public ResponseEntity<Void> uploadAmkaFile(
            @PathVariable UUID uuid,
            @RequestParam("amkaFile") MultipartFile amkaFile)
    throws EntityNotFoundException, FileUploadException {
        teacherService.saveAmkaFile(uuid, amkaFile);
        return ResponseEntity.noContent().build();
    }
}
