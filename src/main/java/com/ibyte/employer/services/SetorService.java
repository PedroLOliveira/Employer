package com.ibyte.employer.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibyte.employer.entity.Setor;
import com.ibyte.employer.repository.SetorRepository;
import com.ibyte.employer.viewmodel.SetorVM;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SetorService {
    @Autowired
    private SetorRepository _setorRepository;
    @Autowired
    private PessoaService _pessoaService;

    public SetorService() {};

    public List<SetorVM> toViewModel(List<Setor> setores) {
        List<SetorVM> setoresVM = new ArrayList<SetorVM>();
		setores.forEach(setor -> {
			setoresVM.add(new SetorVM(setor.getId(), setor.getName()));
        });
        return setoresVM;
    }

    public SetorVM toViewModel(Optional<Setor> setor) {
        if (setor.isPresent()) {
            SetorVM setorVM = new SetorVM(setor.get().getId(), setor.get().getName());
            return setorVM;
        } else {
            return null;
        }
    }

    public List<Setor> read() {
        List<Setor> departments = _setorRepository.findAll();
        return departments;
    }

    public Optional<Setor> read(Long id) {
        Optional<Setor> department = _setorRepository.findById(id);
        return department;
    }

    public Optional<Setor> read(String name) {
        Optional<Setor> department = _setorRepository.findByName(name);
        return department;
    }

    public Setor create(SetorVM entidade) {
        if (!_setorRepository.findByName(entidade.getName()).isPresent()){
            Setor retorno = _setorRepository.save(new Setor(entidade.getName()));
            return retorno;
        }
        return null;
    }

    public Setor update(SetorVM entidade) {
        Optional<Setor> setor = _setorRepository.findById(entidade.getId());
        if (setor.isPresent()) {
            setor.get().setName(entidade.getName());
            Setor retorno = _setorRepository.save(setor.get());
            return retorno;
        }
        return null;
    }

    public void delete() {
        _pessoaService.delete();
        _setorRepository.deleteAll();
    }

    public String delete(Long id) {
        Optional<Setor> setor = this.read(id);
        if (setor.isPresent()) {
            setor.get().getEmployees().forEach(employee -> {
                _pessoaService.delete(employee.getId());
            });
            _setorRepository.deleteById(id);
            return "Departamento excluído!";
        }
        else {
            return "Departamento não encontrado!";
        }
    }
}