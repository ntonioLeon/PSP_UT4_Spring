package org.educa.airline.services;

import org.educa.airline.entity.User;
import org.educa.airline.exceptions.NoTenesPoderAquiException;
import org.educa.airline.repository.inmemory.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final InMemoryUserRepository inMemoryUserRepository;

    @Autowired
    public UserService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return inMemoryUserRepository.getUser(username);
    }

    public boolean create(User user) {
        if (!inMemoryUserRepository.existUser(user.getUsername())) {
            inMemoryUserRepository.createUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(String id, User user) throws NoTenesPoderAquiException {
        if (inMemoryUserRepository.existUser(id)) {
            inMemoryUserRepository.updateUser(user);
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String id) throws NoTenesPoderAquiException {
        if (inMemoryUserRepository.existUser(id)) {
            return inMemoryUserRepository.getUser(id);
        } else {
            return null;
        }
    }

    public boolean delete(String id) throws NoTenesPoderAquiException {
        if (inMemoryUserRepository.existUser(id)) {
            inMemoryUserRepository.deleteUser(id);
            return true;
        } else {
            return false;
        }
    }
}
