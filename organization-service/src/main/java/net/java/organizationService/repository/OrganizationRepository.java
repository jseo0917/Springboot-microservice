package net.java.organizationService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.java.organizationService.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByOrganizationCode(String code);
}
