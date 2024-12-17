package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.api.model.RegistrationBody;
import com.esempio.Ecommerce.exception.UserAlreadyExistsException;
import com.esempio.Ecommerce.model.LocalUser;
import com.esempio.Ecommerce.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private LocalUserDAO localUserDAO;
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
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
        //TODO:Encrypt password!!
        user.setPassword(registrationBody.getPassword());
        user=localUserDAO.save(user);
    return user;


    }
}
