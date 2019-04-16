package com.test.bank.mapper;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.TestCase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    TestCase toTestCase(TestCaseDTO testCaseDTO);
}
