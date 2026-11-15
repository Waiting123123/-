package org.csu.mypetstore.service;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.csu.mypetstore.dto.UserReadResult;
import org.csu.mypetstore.entity.User;
import org.csu.mypetstore.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeAll
    static void initializeEntityMetadata() {
        // Lambda 字段解析需要表元数据；这里只初始化内存映射，不创建数据源。
        TableInfoHelper.initTableInfo(
                new MapperBuilderAssistant(new MybatisConfiguration(), "UserServiceTest"),
                User.class);
    }

    @BeforeEach
    void setUp() {
        userService = new UserService(userMapper);
    }

    @Test
    void returnsReadResultWhenUserExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alice");
        user.setDisplayName("林同学");
        user.setRole("CUSTOMER");
        user.setStatus("ENABLED");
        when(userMapper.selectOne(any())).thenReturn(user);

        Optional<UserReadResult> result = userService.findByUsername("alice");

        assertEquals(Optional.of(new UserReadResult(
                1L, "alice", "林同学", "CUSTOMER", "ENABLED")), result);
    }

    @Test
    void returnsEmptyWhenUserDoesNotExist() {
        when(userMapper.selectOne(any())).thenReturn(null);

        Optional<UserReadResult> result = userService.findByUsername("not-exists");

        assertTrue(result.isEmpty());
    }

    @Test
    void propagatesTheSameDataAccessException() {
        DataAccessResourceFailureException expected =
                new DataAccessResourceFailureException("Simulated database failure");
        when(userMapper.selectOne(any())).thenThrow(expected);

        DataAccessResourceFailureException actual = assertThrows(
                DataAccessResourceFailureException.class,
                () -> userService.findByUsername("alice"));

        assertSame(expected, actual);
    }
}
