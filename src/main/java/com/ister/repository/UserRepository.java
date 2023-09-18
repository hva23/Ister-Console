package com.ister.repository;

import com.ister.domain.Location;
import com.ister.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRespository<User, String> {


    Optional<User> findByUsername(String username);

}
