package gr.aueb.cf.edu9app.api;

import gr.aueb.cf.edu9app.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.edu9app.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.edu9app.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.edu9app.core.exceptions.ValidationException;
import gr.aueb.cf.edu9app.dto.UserInsertDTO;
import gr.aueb.cf.edu9app.dto.UserReadOnlyDTO;
import gr.aueb.cf.edu9app.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserReadOnlyDTO> registerUser(@Valid @RequestBody UserInsertDTO userInsertDTO,
                                                        BindingResult bindingResult)
            throws ValidationException, EntityAlreadyExistsException, EntityInvalidArgumentException {
        // TODO implement validator for business rules

        if (bindingResult.hasErrors()) {
            throw new ValidationException("User", "Invalid user data", bindingResult);
        }
        UserReadOnlyDTO userReadOnlyDTO = userService.saveUser(userInsertDTO);

//        URI location = URI.create("/api/v1/users/" + userReadOnlyDTO.uuid());

       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
               .path("/{uuid}")
               .buildAndExpand(userReadOnlyDTO.uuid()).toUri();

        return ResponseEntity.created(location).body(userReadOnlyDTO);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserReadOnlyDTO> getUserByUUID(@PathVariable UUID uuid)
        throws EntityNotFoundException {

        return ResponseEntity.ok(userService.getUserByUUIDDeletedFalse(uuid));

    }
}
