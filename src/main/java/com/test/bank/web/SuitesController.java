package com.test.bank.web;

import com.test.bank.model.Suite;
import com.test.bank.service.SuitesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

import static com.test.bank.ControllerKeyConstants.ID_KEY;
import static com.test.bank.ControllerKeyConstants.STATUS_KEY;

@RestController
@AllArgsConstructor
public class SuitesController {

    private final SuitesService suitesService;

    @RequestMapping(value = "/projects/{projectId}/suites", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createSuite(@PathVariable("projectId") Long projectId, @RequestBody Suite suite) {
        Optional<Suite> value = suitesService.findSuitByName(projectId, suite.getName(), false);
        if (value.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap(
                    STATUS_KEY, "Suite with name " + suite.getName() +  " exist."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Collections.singletonMap(ID_KEY, suitesService.add(projectId, suite)),HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/suites/{suiteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSuiteById(@PathVariable Long suiteId) {
        Optional<Suite> value = suitesService.findSuiteById(suiteId);
        if (!value.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such project with id " + suiteId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(value.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSuitesByProjectId(@PathVariable Long projectId, Boolean deleted) {
        return new ResponseEntity<>(suitesService.findActiveSuitesByProjectId(projectId, deleted), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/suites/{suiteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteSuite(@PathVariable Long suiteId) {
        Optional<Suite> value = suitesService.findSuiteById(suiteId);
        if (!value.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such project with id " + suiteId), HttpStatus.NOT_FOUND);
        }
        Suite suite = value.get();
        suite.setDeleted(true);

        suitesService.updateSuite(suite);

        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "Deleted"), HttpStatus.OK);
    }
}
