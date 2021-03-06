package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.SecurityDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.TokenStatus;
import org.trinity.yqyl.common.message.lookup.UserStatus;
import org.trinity.yqyl.common.message.lookup.VerifyCodeType;
import org.trinity.yqyl.process.controller.base.IAccountProcessController;
import org.trinity.yqyl.process.controller.base.ISecurityProcessController;
import org.trinity.yqyl.process.controller.base.ISmsProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IOperatorClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.ITokenRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserVerifycodeRepository;
import org.trinity.yqyl.repository.business.entity.OperatorClient;
import org.trinity.yqyl.repository.business.entity.Token;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.UserVerifycode;

@Service
public class SecurityProcessController implements ISecurityProcessController {
    @Autowired
    private ITokenRepository tokenRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserVerifycodeRepository userVerifycodeRepository;
    @Autowired
    private IExceptionFactory exceptionFactory;
    @Autowired
    private IObjectConverter<User, SecurityDto> securityConverter;
    @Autowired
    private IAccountProcessController accountProcessController;
    @Autowired
    private IOperatorClientRepository operatorClientRepository;

    @Autowired
    private ISmsProcessController smsProcessController;

    @Override
    @Transactional(rollbackOn = IException.class)
    public SecurityDto authenticate(final String tokenName, final String username, final String password)
            throws IException {
        final User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw exceptionFactory.createException(ErrorMessage.UNABLE_TO_FIND_USER);
        }

        if (!user.getPassword().equals(password)) {
            throw exceptionFactory.createException(ErrorMessage.WRONG_PASSWORD);
        }

        final SecurityDto userDto = securityConverter.convert(user);

        final Token token = tokenRepository.findOneByToken(tokenName);
        final Date now = new Date();
        token.setUser(user);
        token.setActiveTimestamp(now);
        token.setLastActiveTimestamp(now);
        token.setStatus(TokenStatus.AUTHENTICATED);
        tokenRepository.save(token);

        // user.getTokens().forEach(item -> {
        // if (!item.getToken().equals(tokenName) && item.getStatus() ==
        // TokenStatus.AUTHENTICATED) {
        // item.setStatus(TokenStatus.LOGGED_BY_OTHERS);
        // tokenRepository.save(item);
        // }
        // });

        userDto.setServicer(user.getAccessrights().contains(AccessRight.SERVICE_SUPPLIER)
                || user.getAccessrights().contains(AccessRight.SERVICE_SUPPLIER_REGISTER));

        return userDto;
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public SecurityDto logout(final String tokenName) throws IException {
        final SecurityDto result = new SecurityDto();
        final Token tokenItem = tokenRepository.findOneByToken(tokenName);

        final User user = tokenItem.getUser();
        if (user != null) {
            result.setUsername(user.getUsername());
        }

        tokenItem.setStatus(TokenStatus.LOGGED_OUT);
        tokenItem.setLastActiveTimestamp(new Date());

        tokenRepository.save(tokenItem);

        return result;
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void register(final SecurityDto securityDto) throws IException {
        final String username = securityDto.getUsername();
        final String password = securityDto.getPassword();
        final String cellphone = securityDto.getCellphone();
        final boolean isServicer = securityDto.isServicer();

        User user = userRepository.findOneByUsername(username);
        if (user != null) {
            if (!cellphone.equals(user.getCellphone())) {
                throw exceptionFactory.createException(ErrorMessage.USERNAME_IS_REGISTERED);
            }
        } else {
            user = userRepository.findOneByCellphone(cellphone);

            if (user != null) {
                throw exceptionFactory.createException(ErrorMessage.CELLPHONE_IS_REGISTERED);
            } else {
                user = new User();
                user.setCellphone(securityDto.getCellphone());
                user.setStatus(UserStatus.UNREGISTERED);
                user.setUserVerifycodes(new ArrayList<>());
            }
        }

        user.setAccount(accountProcessController.createAccount());

        user.setUsername(username);
        user.setPassword(password);

        user.setStatus(UserStatus.ACTIVE);

        if (isServicer) {
            final List<AccessRight> accessRights = new ArrayList<>();
            accessRights.add(AccessRight.SERVICE_SUPPLIER_REGISTER);
            user.setAccessrights(accessRights);
        }

        userRepository.save(user);

        final OperatorClient operatorClient = new OperatorClient();
        operatorClient.setName("");
        operatorClient.setStaffNo("");
        operatorClient.setStatus(OperatorClientStatus.INACTIVE);
        operatorClient.setUser(user);
        operatorClient.setUserId(user.getId());

        operatorClientRepository.save(operatorClient);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void registerVerify(final SecurityDto securityDto) throws IException {
        User user = userRepository.findOneByCellphone(securityDto.getCellphone());
        if (user != null) {
            if (user.getStatus() != UserStatus.UNREGISTERED) {
                throw exceptionFactory.createException(ErrorMessage.CELLPHONE_IS_REGISTERED);
            }
        } else {
            user = new User();
            user.setCellphone(securityDto.getCellphone());
            user.setStatus(UserStatus.UNREGISTERED);
            user.setUserVerifycodes(new ArrayList<>());
        }

        final Optional<UserVerifycode> anyVerifycode = user.getUserVerifycodes().stream()
                .filter(item -> item.getType() == VerifyCodeType.REGISTER).findAny();

        final String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        smsProcessController.sendMessage(securityDto.getCellphone(), verifyCode);

        UserVerifycode verifyCodeEntity = null;
        if (anyVerifycode.isPresent()) {
            verifyCodeEntity = anyVerifycode.get();

            final Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, -1);
            if (now.getTime().compareTo(verifyCodeEntity.getTimestamp()) <= 0) {
                throw exceptionFactory.createException(ErrorMessage.SMS_HAS_BEEN_SEND_IN_ONE_MINUTE);
            }
        } else {
            verifyCodeEntity = new UserVerifycode();
            verifyCodeEntity.setCode(verifyCode);
            verifyCodeEntity.setType(VerifyCodeType.REGISTER);
            verifyCodeEntity.setStatus(RecordStatus.ACTIVE);
            verifyCodeEntity.setUser(user);

            user.getUserVerifycodes().add(verifyCodeEntity);
        }
        verifyCodeEntity.setCode(verifyCode);
        verifyCodeEntity.setTimestamp(new Date());

        userRepository.save(user);
        userVerifycodeRepository.save(verifyCodeEntity);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void resetPassword(final SecurityDto securityDto) throws IException {
        final String password = securityDto.getPassword();
        final String cellphone = securityDto.getCellphone();
        final String verifyCode = securityDto.getVerifyCode();

        final User user = userRepository.findOneByCellphone(cellphone);
        if (user == null) {
            throw exceptionFactory.createException(ErrorMessage.UNABLE_TO_FIND_USER);
        }

        final UserVerifycode verifyCodeEntity = user.getUserVerifycodes().stream()
                .filter(item -> item.getType() == VerifyCodeType.FORGET_PASSWORD).findFirst()
                .orElseThrow(() -> exceptionFactory.createException(ErrorMessage.VERIFY_CODE_IS_EXPIRED));

        if (!verifyCode.equals(verifyCodeEntity.getCode())) {
            throw exceptionFactory.createException(ErrorMessage.INCORRECT_VERIFY_CODE);
        }

        userVerifycodeRepository.delete(verifyCodeEntity);

        user.setPassword(password);

        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void resetPasswordVerify(final SecurityDto securityDto) throws IException {
        final User user = userRepository.findOneByCellphone(securityDto.getCellphone());
        if (user == null) {
            throw exceptionFactory.createException(ErrorMessage.UNABLE_TO_FIND_USER);
        }

        final Optional<UserVerifycode> anyVerifycode = user.getUserVerifycodes().stream()
                .filter(item -> item.getType() == VerifyCodeType.FORGET_PASSWORD).findAny();

        final String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        smsProcessController.sendMessage(securityDto.getCellphone(), verifyCode);

        UserVerifycode verifyCodeEntity = null;
        if (anyVerifycode.isPresent()) {
            verifyCodeEntity = anyVerifycode.get();

            final Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, -1);
            if (now.getTime().compareTo(verifyCodeEntity.getTimestamp()) <= 0) {
                throw exceptionFactory.createException(ErrorMessage.SMS_HAS_BEEN_SEND_IN_ONE_MINUTE);
            }
        } else {
            verifyCodeEntity = new UserVerifycode();
            verifyCodeEntity.setCode(verifyCode);
            verifyCodeEntity.setType(VerifyCodeType.FORGET_PASSWORD);
            verifyCodeEntity.setStatus(RecordStatus.ACTIVE);
            verifyCodeEntity.setUser(user);

            user.getUserVerifycodes().add(verifyCodeEntity);
        }
        verifyCodeEntity.setCode(verifyCode);
        verifyCodeEntity.setTimestamp(new Date());

        userRepository.save(user);
        userVerifycodeRepository.save(verifyCodeEntity);
    }
}
