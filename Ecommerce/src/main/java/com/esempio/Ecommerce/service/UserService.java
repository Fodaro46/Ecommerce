package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.api.dto.LoginBody;
import com.esempio.Ecommerce.api.dto.RegistrationBody;
import com.esempio.Ecommerce.exception.UserAlreadyExistsException;
import com.esempio.Ecommerce.model.Entity.LocalUser;
import com.esempio.Ecommerce.model.repository.LocalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final LocalUserRepository localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public UserService(LocalUserRepository localUserDAO, EncryptionService encryptionService, JWTService jwtService) {

        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }
    public LocalUser registerUser( RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent() ){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        //TODO:Encrypt password!! Fatto!!!
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user=localUserDAO.save(user);
    return user;
    }
    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()){
            LocalUser user = opUser.get();
            if(encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
