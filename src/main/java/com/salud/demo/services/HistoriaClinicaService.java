package com.salud.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salud.demo.dto.ArchivoDTO;
import com.salud.demo.dto.HistoriaClinicaDTO;
import com.salud.demo.dto.HistoriaClinicaResponseDTO;
import com.salud.demo.models.ArchivoHC;
import com.salud.demo.models.HistoriaClinica;
import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.ArchivoHCRepository;
import com.salud.demo.repositories.HistoriaClinicaRepository;
import com.salud.demo.repositories.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository hcRepo;
    private final ArchivoHCRepository archivoRepo;
    private final PacienteRepository pacienteRepo;
    
    public HistoriaClinicaService(HistoriaClinicaRepository hcRepo, 
                                ArchivoHCRepository archivoRepo,
                                PacienteRepository pacienteRepository) {
        this.hcRepo = hcRepo;
        this.archivoRepo = archivoRepo;
        this.pacienteRepo = pacienteRepository;
    }

    @Transactional
    public HistoriaClinica guardar(HistoriaClinicaDTO dto) {
        Paciente paciente = pacienteRepo.findById(dto.getPacienteId())
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        // Guardar un hc
        HistoriaClinica hc = new HistoriaClinica();
        hc.setPaciente(paciente);
        hc.setResponsable(dto.getResponsable());
        hc.setEspecialidad(dto.getEspecialidad());
        hc.setDescripcion(dto.getDescripcion());
        hcRepo.save(hc);

        // Guardar archivos
        if (dto.getArchivos() != null) {
            for (ArchivoDTO archivoDTO : dto.getArchivos()) {
                ArchivoHC archivo = new ArchivoHC();
                archivo.setHistoriaClinica(hc);
                archivo.setUrl(archivoDTO.getUrl());
                archivo.setTipoArchivo(archivoDTO.getTipoArchivo());
                archivo.setNombreOriginal(archivoDTO.getCategoria());
                archivoRepo.save(archivo);
            }
        }
        return hc;
    }

    public List<HistoriaClinicaResponseDTO> obtenerPorPaciente(Long pacienteId) {
        return hcRepo.findByPacienteId(pacienteId).stream().map(hc -> {
            HistoriaClinicaResponseDTO dto = new HistoriaClinicaResponseDTO();
            dto.setId(hc.getId());
            dto.setResponsable(hc.getResponsable());
            dto.setEspecialidad(hc.getEspecialidad());
            dto.setDescripcion(hc.getDescripcion());
            dto.setFechaRegistro(hc.getFechaRegistro());
            
            List<ArchivoDTO> archivos = archivoRepo.findByHistoriaClinicaId(hc.getId())
                .stream().map(a -> {
                    ArchivoDTO adto = new ArchivoDTO();
                    adto.setUrl(a.getUrl());
                    adto.setTipoArchivo(a.getTipoArchivo());
                    adto.setNombreOriginal(a.getNombreOriginal());
                    adto.setCategoria(a.getCategoria());
                    return adto;
                }). collect(Collectors.toList());
            
            dto.setArchivos(archivos);
            return dto;

        }).collect(Collectors.toList());
    }

    
}
