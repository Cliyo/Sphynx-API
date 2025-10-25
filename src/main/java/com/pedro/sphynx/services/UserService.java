package com.pedro.sphynx.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.auth.UserDataRegisterInput;
import com.pedro.sphynx.dtos.group.GroupDataInput;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.exceptions.Validation;
import com.pedro.sphynx.repositories.GroupRepository;
import com.pedro.sphynx.repositories.PermissionMenuRepository;
import com.pedro.sphynx.repositories.UnitRepository;
import com.pedro.sphynx.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private PermissionMenuRepository permissionMenuRepository;

    @Autowired
    private EmailService emailService;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public UserDataComplete create(UserDataRegisterInput data, User loggedUser) {
        if (userRepository.existsByUser(data.user())) {
            throw new EntityExistsException("User already exists");
        }

        if (userRepository.existsByRa(data.ra())) {
            throw new EntityExistsException("RA already exists");
        }

        String password = "sphynx@" + data.ra();
        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User(
            null, 
            data.name(), 
            data.ra(),
            null, 
            data.isAdmin(), 
            data.user(),
            encryptedPassword,
            null,
            null, 
            loggedUser, 
            null, 
            null, 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );

        if (data.unitId() != null) {
            if (!unitRepository.existsById(data.unitId())) {
                throw new Validation(messages.getString("error.unitNotExists"));
            }

            user.setUnit(unitRepository.getReferenceById(data.unitId()));
        }

        if (data.tag() != null) {
            if (userRepository.existsByTag(data.tag())) {
                throw new EntityExistsException(messages.getString("error.tagAlreadyExists"));
            }

            user.setTag(data.tag());
        }
        
        User userCreated = userRepository.save(user);

        if (data.permissionMenu() != null && !data.permissionMenu().isEmpty()) {
            List<String> permissionMenuNames = data.permissionMenu().stream()
                .map(Enum::name)
                .toList();

            var permissionMenus = permissionMenuRepository.findByNameIn(permissionMenuNames);

            if (permissionMenus.size() != data.permissionMenu().size()) {
                throw new Validation("One or more permission menus are invalid");
            }

            userCreated.setPermissionMenus(permissionMenus);
        }

        if (data.groupId() != null) {
            if (!groupRepository.existsById(data.groupId())) {
                throw new Validation(messages.getString("error.groupNotExists"));
            }

            userCreated.setGroup(groupRepository.getReferenceById(data.groupId()));
        } else if (loggedUser.isAdmin()) {
            userCreated.setGroup(groupRepository.save(new Group(new GroupDataInput(data.ra() + " - Grupo Padrão", null), userCreated)));
        }

        userCreated = userRepository.save(userCreated);
        
        emailService.sendSimpleMessage(data.user(), "Sphynx | Bem-vindo ao Sphynx", 
            "Olá " + data.name() + ",\n\n" +
            "Sua conta foi criada com sucesso.\n\n" +
            "Nome de usuário: " + data.user() + "\n" +
            "RA: " + data.ra() + "\n\n" +
            "Senha: " + password + "\n\n" +
            "Atenciosamente,\n" +
            "A equipe Sphynx");

        return new UserDataComplete(userCreated);
    }

    public UserDataComplete getById(Long id) {
        User userEntity = userRepository.findById(id)
            .orElseThrow(() -> new EntityExistsException("User not found"));
        return new UserDataComplete(userEntity);
    }

    public List<UserDataComplete> getAll(User loggedUser) {
        List<UserDataComplete> listUsers;

        if (loggedUser.isAdmin()) {
            listUsers = userRepository.findAll()
                .stream()
                .map(UserDataComplete::new)
                .sorted(Comparator.comparing(UserDataComplete::id).reversed())
                .toList();
            
        } else {
            listUsers = userRepository.findAllByUnitId(loggedUser.getUnit().getId())
                .stream()
                .map(UserDataComplete::new)
                .sorted(Comparator.comparing(UserDataComplete::id).reversed())
                .toList();
        }

        return listUsers;
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("User not found");
        }

        userRepository.deleteById(id);
    }

    public UserDataComplete update(UserDataRegisterInput data, Long id){
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException(messages.getString("error.idDontExists"));
        }

        if(userRepository.existsByRaAndIdNot(data.ra(), id)){
            throw new EntityExistsException(messages.getString("error.raAlreadyExists"));
        }

        if(userRepository.existsByTagAndIdNot(data.tag(), id)){
            throw new EntityExistsException(messages.getString("error.tagAlreadyExists"));
        }

        var user = userRepository.getReferenceById(id);
        user.actualizeData(data);

        if(data.groupId() != null){
            if(!groupRepository.existsById(data.groupId())){
                throw new Validation(messages.getString("error.groupNotExists"));
            }
            user.setGroup(groupRepository.getReferenceById(data.groupId()));
        }

        if (data.permissionMenu() != null && !data.permissionMenu().isEmpty()) {
            List<String> permissionMenuNames = data.permissionMenu().stream()
                .map(Enum::name)
                .toList();

            var permissionMenus = permissionMenuRepository.findByNameIn(permissionMenuNames);

            if (permissionMenus.size() != data.permissionMenu().size()) {
                throw new Validation("One or more permission menus are invalid");
            }

            user.setPermissionMenus(permissionMenus);
        }

        user.setDtupdate(LocalDateTime.now());

        User userUpdated = userRepository.save(user);

        return new UserDataComplete(userUpdated);
    }
    
}
