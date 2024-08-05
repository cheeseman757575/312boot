package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.exception.NoSuchUserException;
import ru.kata.spring.boot_security.demo.service.exception.RoleCreationException;
import ru.kata.spring.boot_security.demo.service.exception.UserSaveException;
import ru.kata.spring.boot_security.demo.service.exception.UserUpdateException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordService passwordService;


    private static final String USER_WITH_THIS_ID_NOT_FOUND = "Пользователь с ID %d не найден";
    private static final String USER_LIST_IS_EMPTY = "Cписок пользователей пуст";
    private static final String ERROR_SAVING_USER = "Ошибка при сохранении пользователя";
    private static final String ERROR_UPDATING_USER = "Ошибка при обновлении пользователя";


    @Override
    public List<User> getAllUser() {
        List<User> usersList = userRepository.findAll();
        if (usersList.isEmpty()) {
            logger.warn("Cписок пользователей пуст\"");
            throw new NoSuchUserException(USER_LIST_IS_EMPTY);

        } return usersList;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> userId = userRepository.findById(id);
        if(userId.isEmpty()) {
            logger.warn("Пользователь не найден");
            throw new NoSuchUserException(USER_WITH_THIS_ID_NOT_FOUND);
        } return userId;
    }

    @Override
    public User saveUser(User user) {
        logger.debug("Запрос на сохранение пользователя");

        try {
            user = roleService.validateRoles(user);
            user.setPassword(passwordService.encodePassword(user.getPassword()));

            User savedUser = userRepository.save(user);

            logger.info("Пользователь успешно сохранен: {}", savedUser);
            return  savedUser;

        } catch (RoleCreationException e) {
            throw e; // Пробросить исключение, чтобы оно могло быть обработано в контроллере
        } catch (Exception e) {
            throw new UserSaveException(ERROR_SAVING_USER);
        }
    }


    /**
     * Обновляет существующего пользователя в базе данных.
     * <p>
     * Поиск пользователя по его ID с помощью метода findById() из UserRepository.
     * Валидация ролей и установка их пользователю.
     * Обрабатывает пароль с помощью метода passwordProcessing.
     * Сохраняет обновленного пользователя.
     * Выполняет конвертацию в UserDTO.
     * Пробрасывает возможные исключения, пойманные при поиске пользователя по ID, валидации ролей, кодирования пароля, обновлении и конвертации пользователя.
     *
     * @param user объект User, содержащий обновленные данные пользователя
     * @return обновленный пользователь в виде UserDTO
     * @throws NoSuchUserException с сообщением "Пользователь с ID %d не найден"
     * @throws RoleCreationException "Ошибка при создании роли: "
     * @throws UserUpdateException с сообщением "Ошибка при обновлении пользователя"
     */
    @Override
    public User updateUser(User user) {
        logger.debug("ЗАПРОС НА ОБНОВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ");
        Long userId = Long.valueOf(user.getId());
        User existingUser = userRepository.findById((long) user.getId())
                .orElseThrow(() ->
                        new NoSuchUserException(String.format(USER_WITH_THIS_ID_NOT_FOUND, userId)));
        try {
            user = roleService.validateRoles(user);
            user.setPassword(passwordService.passwordProcessing(existingUser, user));

            User updatedUser = userRepository.save(user);


            logger.info("Пользователь успешно обновлен: {}", user);
            return updatedUser;

        } catch (RoleCreationException e) {
            throw e; // Пробросить исключение, чтобы оно могло быть обработано в контроллере
        } catch (Exception e) {
            throw new UserUpdateException(ERROR_UPDATING_USER, e);
        }
    }

    @Override
    public Optional<String> deleteUser(Long id) {
        logger.debug("Запрос на удаление подьзователя по id", id);

        return Optional.ofNullable(userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    String succesMessage = String.format("Пользователь удален", id);
                    logger.info(succesMessage);
                    return succesMessage;
                })
                .orElseThrow(() -> new NoSuchUserException(String.format(USER_WITH_THIS_ID_NOT_FOUND, id))));
    }

    @Override
    public User getUserById(Long id) {
        return null;












    }
}