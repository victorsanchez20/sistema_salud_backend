package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.salud.demo.models.Doctor;
import com.salud.demo.models.HorarioDoctor;
import com.salud.demo.repositories.DoctorRepository;
import com.salud.demo.repositories.HorarioDoctorRepository;

@ExtendWith(MockitoExtension.class)
class HorarioDoctorServiceTest {

    @Mock
    private HorarioDoctorRepository horarioRepo;

    @Mock
    private DoctorRepository doctorRepo;

    @InjectMocks
    private HorarioDoctorService horarioDoctorService;

    @Captor
    private ArgumentCaptor<HorarioDoctor> horarioCaptor;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor(1L, "Dr. Perez", "Activo");
    }

    @Test
    void guardarHorarioAllNew() {
        when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctor));
        when(horarioRepo.findByDoctorId(1L)).thenReturn(List.of());

        horarioDoctorService.guardarHorario(1L, List.of("09:00", "10:00", "11:00"));

        verify(doctorRepo).findById(1L);
        verify(horarioRepo).findByDoctorId(1L);
        verify(horarioRepo, times(3)).save(horarioCaptor.capture());
        verify(horarioRepo, never()).deleteByDoctorIdAndHoraInicio(anyLong(), any(LocalTime.class));

        List<HorarioDoctor> saved = horarioCaptor.getAllValues();
        assertEquals(3, saved.size());
        Set<LocalTime> horas = Set.of(
            saved.get(0).getHoraInicio(),
            saved.get(1).getHoraInicio(),
            saved.get(2).getHoraInicio()
        );
        assertTrue(horas.contains(LocalTime.of(9, 0)));
        assertTrue(horas.contains(LocalTime.of(10, 0)));
        assertTrue(horas.contains(LocalTime.of(11, 0)));
    }

    @Test
    void guardarHorarioRemoveAndAdd() {
        HorarioDoctor existing = new HorarioDoctor();
        existing.setDoctor(doctor);
        existing.setHoraInicio(LocalTime.of(9, 0));

        when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctor));
        when(horarioRepo.findByDoctorId(1L)).thenReturn(List.of(existing));

        horarioDoctorService.guardarHorario(1L, List.of("10:00", "11:00"));

        verify(horarioRepo).deleteByDoctorIdAndHoraInicio(1L, LocalTime.of(9, 0));
        verify(horarioRepo, times(2)).save(any(HorarioDoctor.class));
    }

    @Test
    void guardarHorarioNoChanges() {
        HorarioDoctor existing = new HorarioDoctor();
        existing.setDoctor(doctor);
        existing.setHoraInicio(LocalTime.of(9, 0));

        when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctor));
        when(horarioRepo.findByDoctorId(1L)).thenReturn(List.of(existing));

        horarioDoctorService.guardarHorario(1L, List.of("09:00"));

        verify(horarioRepo, never()).deleteByDoctorIdAndHoraInicio(anyLong(), any(LocalTime.class));
        verify(horarioRepo, never()).save(any(HorarioDoctor.class));
    }

    @Test
    void guardarHorarioDoctorNotFound() {
        when(doctorRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            horarioDoctorService.guardarHorario(99L, List.of("09:00"));
        });

        verify(horarioRepo, never()).findByDoctorId(anyLong());
        verify(horarioRepo, never()).save(any());
        verify(horarioRepo, never()).deleteByDoctorIdAndHoraInicio(anyLong(), any());
    }
}
