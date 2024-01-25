package it.beachill.api.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> pippo() {
        return ResponseEntity.ok("SI SONO AUTORIZZATO PER PIPPO()");
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('superadmin:delete')")
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("SI SONO AUTORIZZATO PER DELETE()");
    }

}
