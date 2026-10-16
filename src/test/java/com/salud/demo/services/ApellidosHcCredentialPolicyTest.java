package com.salud.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.salud.demo.models.Paciente;
import com.salud.demo.services.auth.ApellidosHcCredentialPolicy;

class ApellidosHcCredentialPolicyTest {

    private final ApellidosHcCredentialPolicy policy = new ApellidosHcCredentialPolicy();

    @Test
    void ignoraEspaciosYToleraApellidosNulos() {
        Paciente p = new Paciente();
        p.setApaterno("  Garcia ");
        p.setAmaterno(null);
        p.setHc("HC000002");

        assertTrue(policy.coincide(p, "GarciaHC000002"));
        assertFalse(policy.coincide(p, "garciahc000002"));
    }
}
