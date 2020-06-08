package com.ibyte.employer.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.ibyte.employer.entity.Setor;
import com.ibyte.employer.services.SetorService;
import com.ibyte.employer.viewmodel.SetorVM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/setor")
public class SetorController {
	@Autowired
	private SetorService _setorService;
	
	@GetMapping
	public ResponseEntity<List<SetorVM>> ListarSetores() {
		List<SetorVM> setores = _setorService.toViewModel(_setorService.read());
		return new ResponseEntity<>(setores, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> CadastrarSetor(@RequestBody SetorVM entidade) {
		Setor setor = _setorService.create(entidade);
		if (setor != null)
			return new ResponseEntity<>(setor.toString(), HttpStatus.OK);
		return new ResponseEntity<>("Não pode existir dois departamentos com o mesmo nome!", HttpStatus.NOT_ACCEPTABLE);
	}

	@PutMapping
	public ResponseEntity<String> AtualizarSetor(@RequestBody SetorVM entidade ) {
		try {
			Setor setor = _setorService.update(entidade);
			if (setor != null)
				return new ResponseEntity<>(setor.toString(), HttpStatus.OK);
			return new ResponseEntity<>("Departamento não encontrado!", HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException nsee){
			return new ResponseEntity<>("Departamento não encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping
	public ResponseEntity<String> DeletarSetor(@RequestParam(value = "id", required = true) Long id) {
		try {
			String retorno = _setorService.delete(id);
			if (retorno == "Departamento excluído!")
				return new ResponseEntity<>(retorno, HttpStatus.OK);
			return new ResponseEntity<>(retorno, HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException nsee){
			return new ResponseEntity<>("Departamento não encontrado!", HttpStatus.NOT_FOUND);
		}
	}
}