package pets.HomeForPets.servise;

import pets.HomeForPets.dto.UserDTO;
import pets.HomeForPets.model.User;
import pets.HomeForPets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            return userRepository.save(existingUser);
        } else {
            return null; // Можна реалізувати відповідний викид помилки, якщо користувача з даним id не знайдено
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerNewUser(UserDTO userDTO) {
        if (!isEmailAlreadyExists(userDTO.getEmail())) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            return userRepository.save(user);
        } else {
            // Обробка ситуації, коли електронна адреса вже існує
            return null;
        }
    }

    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
