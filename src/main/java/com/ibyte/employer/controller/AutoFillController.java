package com.ibyte.employer.controller;

import com.ibyte.employer.services.PessoaService;
import com.ibyte.employer.services.SetorService;
import com.ibyte.employer.viewmodel.PessoaVM;
import com.ibyte.employer.viewmodel.SetorVM;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/autofill")
public class AutoFillController {
    @Autowired
    private PessoaService _pessoaService;
    @Autowired
    private SetorService _setorService;

    @GetMapping
	public ResponseEntity<String> FillDepartamentos() {
        _pessoaService.delete();
        _setorService.delete();
        RestTemplate restTemplate = new RestTemplate();
        String urlDepartments = "https://5e61af346f5c7900149bc5b3.mockapi.io/desafio03/department";
        String urlEmployees = "https://5e61af346f5c7900149bc5b3.mockapi.io/desafio03/employer";
        String jsonString;
        JSONArray jsArray;

        // get data for department
        jsonString = restTemplate.getForObject(urlDepartments, String.class);
        jsArray = new JSONArray(jsonString);

        // set data for department
        for (int i = 0; i < jsArray.length(); i++) {
            String name = jsArray.getJSONObject(i).getString("name").toString();
            if (!_setorService.read(name).isPresent()) {
                _setorService.create(new SetorVM(name));
            }
        }

        // get data for employees
        jsonString = restTemplate.getForObject(urlEmployees, String.class);
        jsArray = new JSONArray(jsonString);

        // set data for employees
        for (int i = 0; i < jsArray.length(); i++) {
            _pessoaService.create(new PessoaVM(
                    jsArray.getJSONObject(i).getString("first_name").toString(),
                    jsArray.getJSONObject(i).getString("last_name").toString(),
                    jsArray.getJSONObject(i).getString("career").toString(),
                    jsArray.getJSONObject(i).getString("departament").toString()
                ));
        }
        
        return new ResponseEntity<>(_setorService.read().toString().concat("\n\n" + _pessoaService.read().toString()), HttpStatus.OK);
    }
}