package org.trinity.yqyl.repository.business.dataaccess;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.trinity.yqyl.repository.business.entity.Token;

public interface ITokenRepository extends CrudRepository<Token, Long> {
    Token findOneByDeviceIdentity(String deviceIdentity);

    Token findOneByToken(String token);

    @Modifying
    @Query("update Token set lastActiveTimestamp=:lastActiveTimestamp where token=:token")
    void updateLastActiveTimestampByToken(@Param("token") String token, @Param("lastActiveTimestamp") Date lastActiveTimestamp);
}
