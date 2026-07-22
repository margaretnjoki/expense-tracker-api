package com.margaretnjoki.expense_tracker_api.service;

import com.margaretnjoki.expense_tracker_api.repository.CategoryRepository;
import com.margaretnjoki.expense_tracker_api.repository.UserRepository;
import com.margaretnjoki.expense_tracker_api.security.CurrentUserProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private  CategoryRepository categoryRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  CurrentUserProvider currentUserProvider;

    @InjectMocks
    private CategoryService categoryService;
}
