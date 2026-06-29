package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.dto.ArchivoDTO;
import com.salud.demo.dto.HistoriaClinicaDTO;
import com.salud.demo.dto.HistoriaClinicaResponseDTO;
import com.salud.demo.models.ArchivoHC;
import com.salud.demo.models.HistoriaClinica;
import com.salud.demo.models.Paciente;
import com.salud.demo.repositories.ArchivoHCRepository;
import com.salud.demo.repositories.HistoriaClinicaRepository;
import com.salud.demo.repositories.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class HistoriaClinicaServiceTest {

    @Mock
    private HistoriaClinicaRepository hcRepo;

    @Mock
    private ArchivoHCRepository archivoRepo;

    @Mock
    private PacienteRepository pacienteRepo;

    @InjectMocks
    private HistoriaClinicaService service;

    @Captor
    private ArgumentCaptor<HistoriaClinica> hcCaptor;

    @Captor
    private ArgumentCaptor<ArchivoHC> archivoCaptor;

    private Paciente paciente;
    private HistoriaClinicaDTO dto;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");

        dto = new HistoriaClinicaDTO();
        dto.setPacienteId(1L);
        dto.setResponsable("Dr. Gomez");
        dto.setEspecialidad("Cardiologia");
        dto.setDescripcion("Revision general");
    }

    @Test
    void guardarWithoutFiles() {
        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(paciente));
        when(hcRepo.save(any(HistoriaClinica.class))).thenAnswer(invocation -> {
            HistoriaClinica hc = invocation.getArgument(0);
            hc.setId(1L);
            return hc;
        });

        HistoriaClinica result = service.guardar(dto);

        assertNotNull(result);
        assertEquals(paciente, result.getPaciente());
        assertEquals("Dr. Gomez", result.getResponsable());
        assertEquals("Cardiologia", result.getEspecialidad());
        assertEquals("Revision general", result.getDescripcion());

        verify(pacienteRepo).findById(1L);
        verify(hcRepo).save(any(HistoriaClinica.class));
        verify(archivoRepo, never()).save(any());
    }

    @Test
    void guardarWithFiles() {
        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setUrl("http://example.com/doc.pdf");
        archivoDTO.setTipoArchivo("PDF");
        archivoDTO.setNombreOriginal("informe.pdf");
        archivoDTO.setCategoria("Informes");
        dto.setArchivos(List.of(archivoDTO));

        when(pacienteRepo.findById(1L)).thenReturn(Optional.of(paciente));
        when(hcRepo.save(any(HistoriaClinica.class))).thenAnswer(invocation -> {
            HistoriaClinica hc = invocation.getArgument(0);
            hc.setId(1L);
            return hc;
        });
        when(archivoRepo.save(any(ArchivoHC.class))).thenAnswer(invocation -> invocation.getArgument(0));

        HistoriaClinica result = service.guardar(dto);

        assertNotNull(result);
        verify(pacienteRepo).findById(1L);
        verify(hcRepo).save(any(HistoriaClinica.class));
        verify(archivoRepo).save(archivoCaptor.capture());

        ArchivoHC savedArchivo = archivoCaptor.getValue();
        assertEquals("http://example.com/doc.pdf", savedArchivo.getUrl());
        assertEquals("PDF", savedArchivo.getTipoArchivo());
        assertEquals("Informes", savedArchivo.getNombreOriginal());
        assertNotNull(savedArchivo.getHistoriaClinica());
    }

    @Test
    void guardarPacienteNotFound() {
        when(pacienteRepo.findById(99L)).thenReturn(Optional.empty());
        dto.setPacienteId(99L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardar(dto);
        });

        assertEquals("Paciente no encontrado", exception.getMessage());
        verify(hcRepo, never()).save(any());
        verify(archivoRepo, never()).save(any());
    }

    @Test
    void obtenerPorPaciente() {
        HistoriaClinica hc = new HistoriaClinica();
        hc.setId(1L);
        hc.setPaciente(paciente);
        hc.setResponsable("Dr. Gomez");
        hc.setEspecialidad("Cardiologia");
        hc.setDescripcion("Revision general");

        when(hcRepo.findByPacienteId(1L)).thenReturn(List.of(hc));
        when(archivoRepo.findByHistoriaClinicaId(1L)).thenReturn(List.of());

        List<HistoriaClinicaResponseDTO> result = service.obtenerPorPaciente(1L);

        assertEquals(1, result.size());
        HistoriaClinicaResponseDTO dtoResult = result.get(0);
        assertEquals(1L, dtoResult.getId());
        assertEquals("Dr. Gomez", dtoResult.getResponsable());
        assertEquals("Cardiologia", dtoResult.getEspecialidad());
        assertTrue(dtoResult.getArchivos().isEmpty());

        verify(hcRepo).findByPacienteId(1L);
        verify(archivoRepo).findByHistoriaClinicaId(1L);
    }

    @Test
    void obtenerPorPacienteWithFiles() {
        HistoriaClinica hc = new HistoriaClinica();
        hc.setId(1L);
        hc.setPaciente(paciente);
        hc.setResponsable("Dr. Gomez");

        ArchivoHC archivo = new ArchivoHC();
        archivo.setUrl("http://example.com/img.jpg");
        archivo.setTipoArchivo("IMAGEN");
        archivo.setNombreOriginal("foto.jpg");
        archivo.setCategoria("Fotos");

        when(hcRepo.findByPacienteId(1L)).thenReturn(List.of(hc));
        when(archivoRepo.findByHistoriaClinicaId(1L)).thenReturn(List.of(archivo));

        List<HistoriaClinicaResponseDTO> result = service.obtenerPorPaciente(1L);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getArchivos().size());
        ArchivoDTO archivoResult = result.get(0).getArchivos().get(0);
        assertEquals("http://example.com/img.jpg", archivoResult.getUrl());
        assertEquals("IMAGEN", archivoResult.getTipoArchivo());
        assertEquals("foto.jpg", archivoResult.getNombreOriginal());
        assertEquals("Fotos", archivoResult.getCategoria());
    }
}
