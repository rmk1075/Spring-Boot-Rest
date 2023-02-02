
package com.spring.practice.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.service.dataset.DatasetService;

@RestController
@RequestMapping("/datasets")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @GetMapping("/")
    public List<DatasetInfo> getDatasets() {
        List<DatasetInfo> datasets = datasetService.getDatasets();
        return datasets;
    }
    
    @GetMapping("/{id}")
    public DatasetInfo getDataset(@PathVariable Long id) {
        DatasetInfo dataset = datasetService.getDataset(id);
        return dataset;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public DatasetInfo createDataset(@RequestBody DatasetUserCreate userCreate) {
        DatasetInfo dataset = datasetService.createDataset(userCreate);
        return dataset;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteDataset(@PathVariable Long id) {
        datasetService.deleteDataset(id);
    }
}
