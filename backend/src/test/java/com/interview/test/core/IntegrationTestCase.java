package com.interview.test.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
        classes = {TestApplicationConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@RunWith(SpringRunner.class)
@ActiveProfiles("integration")
@Transactional
public abstract class IntegrationTestCase {
}
