package gr.aueb.cf.edu9app.api;

import gr.aueb.cf.edu9app.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.edu9app.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.edu9app.core.exceptions.ValidationException;
import gr.aueb.cf.edu9app.dto.TeacherInsertDTO;
import gr.aueb.cf.edu9app.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.edu9app.service.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherRestController {

    private final ITeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherReadOnlyDTO> saveTeacher(
            @Valid @RequestBody TeacherInsertDTO teacherInsertDTO,
            BindingResult bindingResult) throws EntityAlreadyExistsException,
            EntityInvalidArgumentException, ValidationException {

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
}
