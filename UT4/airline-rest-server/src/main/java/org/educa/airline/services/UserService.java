package org.educa.airline.services;

import org.educa.airline.entity.Role;
import org.educa.airline.entity.User;
import org.educa.airline.exceptions.NoTenesPoderAquiException;
import org.educa.airline.repository.inmemory.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext() .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userDetail.getUsername().equals(id) || userDetail.getAuthorities().contains(new Role("ROLE_admin", "Administrador", "El que administra"))) {
            if (inMemoryUserRepository.existUser(id)) {
                inMemoryUserRepository.updateUser(user);
                return true;
            } else {
                return false;
            }
        } else {
        throw new NoTenesPoderAquiException();
        }
    }

    public User getUser(String id) throws NoTenesPoderAquiException {
        Authentication auth = SecurityContextHolder.getContext() .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userDetail.getUsername().equals(id) || userDetail.getAuthorities().contains(new Role("ROLE_admin", "Administrador", "El que administra"))) {
            if (inMemoryUserRepository.existUser(id)) {
                return inMemoryUserRepository.getUser(id);
            } else {
                return null;
            }
        } else {
            throw new NoTenesPoderAquiException();
        }

    }

    public boolean delete(String id) throws NoTenesPoderAquiException {
        Authentication auth = SecurityContextHolder.getContext() .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        if (userDetail.getUsername().equals(id) || userDetail.getAuthorities().contains(new Role("ROLE_admin", "Administrador", "El que administra"))) {
            if (inMemoryUserRepository.existUser(id)) {
                inMemoryUserRepository.deleteUser(id);
                return true;
            } else {
                return false;
            }
        } else {
            throw new NoTenesPoderAquiException();
        }
    }
}
