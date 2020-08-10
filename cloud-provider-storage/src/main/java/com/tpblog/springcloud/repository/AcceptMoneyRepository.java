package com.tpblog.springcloud.repository;

import com.tpblog.common.oracleEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AcceptMoneyRepository extends JpaRepository<User, Long> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE User u SET u.account = u.account + :money WHERE u.id=:id")
    Integer updateAccount(@Param("money") Double money, @Param("id") Integer id);

    User findById(Integer id);
}
