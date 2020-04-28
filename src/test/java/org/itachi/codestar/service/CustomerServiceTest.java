package org.itachi.codestar.service;

import org.itachi.codestar.domain.Customer;
import org.itachi.codestar.repositories.jpa.JpaCustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaCustomerRepository repository;

    @Before
    public void setUp() {
        Customer customer = new Customer();
        customer.setName("admin");
        entityManager.persist(customer);
    }

    @Test
    public void findCustomerOne() {
        Customer customer = repository.getOne(1L);
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo("admin");
    }
}