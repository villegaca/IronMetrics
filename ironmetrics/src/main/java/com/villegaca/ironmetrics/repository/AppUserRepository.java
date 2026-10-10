package com.villegaca.ironmetrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.villegaca.ironmetrics.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    
}
