package com.ibyte.employer.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.ibyte.employer.entity.Pessoa;
import com.ibyte.employer.entity.Setor;
import com.ibyte.employer.services.PessoaService;
import com.ibyte.employer.services.SetorService;
import com.ibyte.employer.viewmodel.PessoaVM;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {
	@Autowired
	private PessoaService _pessoaService;
	@Autowired
	private SetorService _setorService;

	@GetMapping
	public ResponseEntity<Iterable<PessoaVM>> ListarPessoas() {
		List<PessoaVM> pessoasVM = _pessoaService.toViewModel(_pessoaService.read());
		return new ResponseEntity<>(pessoasVM, HttpStatus.OK);
	}

	@GetMapping(path="/setor")
	public ResponseEntity<Iterable<PessoaVM>> ListarPessoasPorDepartamento(@RequestParam(value = "department", required = true) String department) {
		Optional<Setor> setor = _setorService.read(department);
		if (!setor.isPresent())
			return new ResponseEntity<Iterable<PessoaVM>>(HttpStatus.NOT_FOUND);
		try {
			return new ResponseEntity<Iterable<PessoaVM>>(_pessoaService.readByDepartment(setor.get().getName()), HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Iterable<PessoaVM>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> CadastrarPessoa(@RequestBody PessoaVM entidade) {
		Pessoa pessoa = _pessoaService.create(entidade);
		if (pessoa != null)
			return new ResponseEntity<>(pessoa.toString(), HttpStatus.OK);
		else
			return new ResponseEntity<>("Departamento não encontrado!", HttpStatus.NOT_FOUND);
	}

	@PutMapping
	public ResponseEntity<String> AtualizarPessoa(@RequestBody PessoaVM entidade) {
		try {
			Pessoa pessoa = _pessoaService.update(entidade);
			if (pessoa != null)
				return new ResponseEntity<>(pessoa.toString(), HttpStatus.OK);
			return new ResponseEntity<>("Departamento ou funcionário não encontrado!", HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<>("Departamento ou funcionário não encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping
	public ResponseEntity<String> DeletarPessoa(@RequestParam(value = "id", required = true) Long id) {
		try {
			String retorno = _pessoaService.delete(id);
			if (retorno == "Funcionário excluído!")
				return new ResponseEntity<>(retorno, HttpStatus.OK);
			return new ResponseEntity<>(retorno, HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<>("Funcionário não encontrado!", HttpStatus.NOT_FOUND);
		}
	}
}