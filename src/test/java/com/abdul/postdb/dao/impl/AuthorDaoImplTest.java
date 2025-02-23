package com.abdul.postdb.dao.impl;

import com.abdul.postdb.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?,?,?)"),
                eq(1L), eq("Abdullah"), eq(21)
        );
    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );

    }

    @Test
    public void testThatUpdateGeneratesTheCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.update(1L, author);
        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?"),
                eq(author.getId()),
                eq(author.getName()),
                eq(author.getAge()),
                eq(1L)
        );
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql() {
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id = ?"),
                eq(1L)
        );

    }
}
