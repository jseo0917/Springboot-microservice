package net.java.organizationService.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.java.organizationService.dto.OrganizationDto;
import net.java.organizationService.entity.Organization;
import net.java.organizationService.repository.OrganizationRepository;
import net.java.organizationService.service.OrganizationService;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    
    private OrganizationRepository organizationRepository;
    private ModelMapper modelMapper;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        
        Organization organization = modelMapper.map(organizationDto, Organization.class);

        organizationRepository.save(organization);

        OrganizationDto savedOrganizationDto = modelMapper.map(organization, OrganizationDto.class);

        return savedOrganizationDto;
    }

    @Override
    public OrganizationDto getOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByOrganizationCode(code);
        
        
        if(organization == null){
            System.out.println("null");
            return null;
        }

        OrganizationDto organizationDto = modelMapper.map(organization, OrganizationDto.class);
    
        return organizationDto;
    }
}
