package net.java.organizationService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.java.organizationService.dto.OrganizationDto;
import net.java.organizationService.service.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationController {
    private OrganizationService organizationService;    

    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganzation(@RequestBody OrganizationDto organizationDto){
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDto), HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("code") String code){        
        return new ResponseEntity<>(organizationService.getOrganizationByCode(code), HttpStatus.OK);
    }
}
