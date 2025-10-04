package br.compneusgppremium.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TasmotaService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Configura o dispositivo Tasmota via HTTP, aplicando nome amigável, hostname e tópico.
     * Usa comando Backlog para aplicar múltiplos comandos em uma única chamada.
     */
    public void configurar(String ip, String nome, String maquina) {
        try {
            String backlog = String.format("Backlog FriendlyName %s; Hostname %s; Topic %s", nome, maquina, maquina);
            String encodedCmd = URLEncoder.encode(backlog, StandardCharsets.UTF_8);
            String url = String.format("http://%s/cm?cmnd=%s", ip, encodedCmd);
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao configurar Tasmota no IP " + ip + ": " + e.getMessage(), e);
        }
    }
}