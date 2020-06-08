package com.ibyte.employer.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibyte.employer.entity.Pessoa;
import com.ibyte.employer.entity.Setor;
import com.ibyte.employer.repository.PessoaRepository;
import com.ibyte.employer.repository.SetorRepository;
import com.ibyte.employer.viewmodel.PessoaVM;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository _pessoaRepository;
    @Autowired
    private SetorRepository _setorRepository;

    public PessoaService() {};

    public List<PessoaVM> toViewModel(List<Pessoa> pessoas) {
        List<PessoaVM> pessoasVM = new ArrayList<PessoaVM>();
		pessoas.forEach(pessoa -> {
			Setor setor = pessoa.getDepartmentNavigation();
			pessoasVM.add(new PessoaVM(pessoa.getId(), pessoa.getFirst_name(), pessoa.getLast_name(), pessoa.getCareer(), setor.getName()));
        });
        return pessoasVM;
    }

    public List<Pessoa> read() {
        List<Pessoa> employees = _pessoaRepository.findAll();
        return employees;
    }

    public Optional<Pessoa> read(Long id) {
        Optional<Pessoa> employee = _pessoaRepository.findById(id);
        return employee;
    }

    public List<PessoaVM> readByDepartment(String department) {
        List<PessoaVM> employeesVM = this.toViewModel(_pessoaRepository.findByDepartmentNavigation_Name(department));
        return employeesVM;
    }

    public Pessoa create(PessoaVM pessoa) {
        Optional<Setor> setor = _setorRepository.findByName(pessoa.getDepartment());
        if (setor.isPresent()) {
            Pessoa retorno = _pessoaRepository.save(new Pessoa(pessoa.getFirst_name(), pessoa.getLast_name(), pessoa.getCareer(), setor.get()));
            return retorno;
        }
        return null;
    }

    public Pessoa update(PessoaVM entidade) {
        Optional<Setor> setor = _setorRepository.findByName(entidade.getDepartment());
        if (setor.isPresent()) {
            Optional<Pessoa> pessoa = _pessoaRepository.findById(entidade.getId());
            if (pessoa.isPresent()){
                pessoa.get().setFirst_name(entidade.getFirst_name());
                pessoa.get().setLast_name(entidade.getLast_name());
                pessoa.get().setCareer(entidade.getCareer());
                pessoa.get().setDepartmentNavigation(setor.get());
                Pessoa retorno = _pessoaRepository.save(pessoa.get());
                return retorno;
            }
        }
        return null;
    }

    public void delete() {
        _pessoaRepository.deleteAll();
    }

    public String delete(Long id) {
        if (_pessoaRepository.findById(id).isPresent()) {
            _pessoaRepository.deleteById(id);
            return "Funcionário excluído!";
        } else {
            return "Funcionário não encontrado!";
        }
    }
}