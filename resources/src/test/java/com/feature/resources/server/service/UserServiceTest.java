package com.feature.resources.server.service;

import com.feature.resources.server.config.beanvalidate.AutoValidating;
import com.feature.resources.server.config.beanvalidate.ValidatorGuiceModule;
import com.feature.resources.server.dao.UserDao;
import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.service.impl.UserServiceImpl;
import com.feature.resources.server.util.DomainObjectFactory;
import com.feature.resources.server.util.beanvalidator.BeanValidators;
import com.google.code.morphia.Key;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午9:53
 * FileName:UserServiceTest
 */
@RunWith(JukitoRunner.class)
@AutoValidating
public class UserServiceTest {



    public static class Module extends JukitoModule{

        @Override
        protected void configureTest() {
            bindMock(UserDao.class).in(TestSingleton.class);
//            bind(Validator.class).to(ValidatorImpl.class);
            bind(UserService.class).to(UserServiceImpl.class).in(TestSingleton.class);
            bind(DomainObjectFactory.class).in(TestSingleton.class);
            install(new ValidatorGuiceModule());
        }
    }


    private UserDTO userDTO;

    @Inject
    private UserService userService;

    @Inject
    private DomainObjectFactory domainObjectFactory;
    @Inject
    private ValidatorFactory validatorFactory;

    @Before
    @AutoValidating
    public void setUp(){
        userDTO = new UserDTO();
        userDTO.setEmail("abc@test.com");
        userDTO.setLoginName("joesmart");
        userDTO.setName("abc@test.com");
        userDTO.setPassword("123121");
        testValitor(userDTO);
       // validator = Validation.buildDefaultValidatorFactory().getValidator();
        Validator validator = validatorFactory.getValidator();
        BeanValidators.validateWithException(validator, userDTO);
    }


    public void testValitor(UserDTO userDTO){

    }

    @Test
    public void should_regitster_user_successful(UserDao userDao) throws Exception {
        final Key<User> key = new Key<User>(User.class,new ObjectId());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                User user = (User) invocation.getArguments()[0];
                user.setId((ObjectId)key.getId());
                return key;
            }
        }).when(userDao).save(any(User.class));

        boolean result= userService.registerUser(userDTO);
        verify(userDao).save(any(User.class));
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_register_user_failed(UserDao userDao){
        User user = new User();
        user.setId(new ObjectId());
        user.setName("xxx");
        user.setLoginName("xxx");
        DomainObjectFactory spy =  spy(domainObjectFactory);
        doReturn(user).when(spy).translateToUser(userDTO);
        userService.updateDomainObjectFactory(spy);
        boolean result= userService.registerUser(userDTO);
        verify(userDao).save(any(User.class));
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_false_when_user_login_name_exists(UserDao userDao){
        when(userDao.exists("loginName","xxx")).thenReturn(true);
        boolean result = userService.isUserAlreadyExists("xxx");
        assertThat(result).isTrue();
    }

    @Test
    public void should_get_user_by_loginName_when_user_exists(UserDao userDao){
        String loginName = "xxxx";
        User user1 = new User();
        when(userDao.findUserByLoginName(loginName)).thenReturn(user1);
        User user = userService.findUserByLoginName(loginName);
        assertThat(user).isNotNull();
    }
    @Test
    public void should_Get_user_by_loginName_when_user_not_exists(UserDao userDao){
        String loginName = "xxxx";
        User user1 = new User();
        when(userDao.findUserByLoginName(loginName)).thenReturn(null);
        User user = userService.findUserByLoginName(loginName);
        assertThat(user).isNull();
    }
}
