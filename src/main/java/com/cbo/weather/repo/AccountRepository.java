package com.cbo.weather.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cbo.weather.model.AccountEnquery;

public interface AccountRepository extends JpaRepository<AccountEnquery, Long> {

	List<AccountEnquery> findByNameContaining(String name);
}
