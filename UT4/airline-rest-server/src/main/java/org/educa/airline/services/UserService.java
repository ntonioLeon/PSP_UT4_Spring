package org.educa.airline.services;

import org.educa.airline.entity.Role;
import org.educa.airline.entity.User;
import org.educa.airline.exceptions.NoTenesPoderAquiException;
import org.educa.airline.exceptions.user.UserDuplicatedException;
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

    public boolean update(String id, User user) throws NoTenesPoderAquiException, UserDuplicatedException {
        UserDetails userDetail = getAutenticated();
        if (userDetail.getUsername().equals(id) || esAdmin(userDetail)) {
            if (user.getUsername().equals(id)) {
                if (inMemoryUserRepository.existUser(id)) {
                    if ("z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg_SpIdNs6c5H0NE8XYXysP-DGNKHfuwvY7kxvUdBeoGlODJ6-SfaPg==".equals(user.getPassword())) {
                        user.setPassword(inMemoryUserRepository.getUser(id).getPassword());
                    }
                    inMemoryUserRepository.updateUser(user);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (!inMemoryUserRepository.existUser(user.getUsername())) {
                    if ("z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg_SpIdNs6c5H0NE8XYXysP-DGNKHfuwvY7kxvUdBeoGlODJ6-SfaPg==".equals(user.getPassword())) {
                        user.setPassword(inMemoryUserRepository.getUser(id).getPassword());
                    }
                    inMemoryUserRepository.deleteUser(id);
                    inMemoryUserRepository.createUser(user);
                    return true;
                } else {
                    throw new UserDuplicatedException();
                }
            }
        } else {
            throw new NoTenesPoderAquiException();
        }
    }

    public User getUser(String id) throws NoTenesPoderAquiException {
        UserDetails userDetail = getAutenticated();
        if (userDetail.getUsername().equals(id) || esAdmin(userDetail) || esPersonal(userDetail)) {
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
        UserDetails userDetail = getAutenticated();
        if (userDetail.getUsername().equals(id) || esAdmin(userDetail)) {
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

    private UserDetails getAutenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getPrincipal();
    }

    private boolean esAdmin(UserDetails userDetail) {
        return userDetail.getAuthorities().contains(new Role("ROLE_admin", "Administrador", "El que administra"));
    }

    private boolean esPersonal(UserDetails userDetail) {
        return userDetail.getAuthorities().contains(new Role("ROLE_personal", "Personal", "El que personalea"));
    }
}
