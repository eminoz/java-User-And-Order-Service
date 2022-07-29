package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.AuthService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.ErrorDataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.LoginUser;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.LoginUserDto;
import com.trendyol.backend.entities.dtos.UserDto;
import com.trendyol.backend.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthManager implements AuthService {
    private UserDao userDao;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public AuthManager(UserDao userDao, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<UserDto> createUser(User user) {

        String email = user.getEmail();
        User userByEmail = this.userDao.findOneUserByEmail(email);
        if (userByEmail != null) {
            final ErrorDataResult<UserDto> user_already_exist = new ErrorDataResult<>(null, "user already exist");
            return user_already_exist;
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User insert = this.userDao.insert(user);
        UserDto userDto = this.modelMapper.map(insert, UserDto.class);//Burada kayıt frontende döneceğimiz verilere çevirmiş oluyoruz
        return new
                SuccessDataResult<>(userDto, "user created");
    }

    @Override
    public DataResult<LoginUserDto> login(LoginUser user) {
        User oneUserByEmail = this.userDao.findOneUserByEmail(user.getEmail());
        if (oneUserByEmail == null) {
            DataResult<LoginUserDto> objectDataResult = new DataResult<>(null, false, "user not found with this email");
            return objectDataResult;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authenticate);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = this.jwtTokenProvider.generateJwtToken(authenticate);
        LoginUserDto user_logged_in = new LoginUserDto("user logged in", oneUserByEmail.getId(), token);
        DataResult<LoginUserDto> loginUserDtoDataResult = new DataResult<>(user_logged_in, true, "");
        return loginUserDtoDataResult;
    }
}
