package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.*;
import br.compneusgppremium.api.repository.PerfilRepository;
import br.compneusgppremium.api.repository.ProducaoRepository;
import br.compneusgppremium.api.repository.UsuarioRepository;
import br.compneusgppremium.api.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProducaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioModel usuarioTeste;
    private PerfilModel perfilTeste;
    private ProducaoModel producaoTeste;
    private String tokenJWT;

    @BeforeEach
    void setUp() {
        // Criar perfil de teste
        perfilTeste = new PerfilModel();
        perfilTeste.setDescricao("ROLE_USER");
        perfilTeste = perfilRepository.save(perfilTeste);

        // Criar usuário de teste
        usuarioTeste = new UsuarioModel();
        usuarioTeste.setLogin("teste@teste.com");
        usuarioTeste.setSenha(passwordEncoder.encode("123456"));
        usuarioTeste.setNome("Usuario Teste");
        usuarioTeste.getPerfil().add(perfilTeste);
        usuarioTeste = usuarioRepository.save(usuarioTeste);

        // Gerar token JWT
        Authentication auth = new UsernamePasswordAuthenticationToken(usuarioTeste, null, usuarioTeste.getAuthorities());
        tokenJWT = tokenService.gerarToken(auth);

        // Criar produção de teste
        producaoTeste = new ProducaoModel();
        producaoTeste.setCriadoPor(usuarioTeste);
        producaoTeste = producaoRepository.save(producaoTeste);
    }

    @Test
    void deveListarProducoesComAutenticacao() throws Exception {
        mockMvc.perform(get("/api/producao")
                .header("Authorization", "Bearer " + tokenJWT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveBuscarProducaoPorIdComAutenticacao() throws Exception {
        mockMvc.perform(get("/api/producao/" + producaoTeste.getId())
                .header("Authorization", "Bearer " + tokenJWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(producaoTeste.getId()));
    }

    @Test
    void deveRetornarNotFoundParaProducaoInexistente() throws Exception {
        mockMvc.perform(get("/api/producao/999999")
                .header("Authorization", "Bearer " + tokenJWT))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarProducaoComAutenticacao() throws Exception {
        // Criar dados de teste válidos
        PaisModel pais = new PaisModel();
        pais.setId(1);
        pais.setDescricao("Brasil");
        
        MedidaModel medida = new MedidaModel();
        medida.setId(1);
        medida.setDescricao("295/80R22.5");
        
        MarcaModel marca = new MarcaModel();
        marca.setId(1);
        marca.setDescricao("Bridgestone");
        
        ModeloModel modelo = new ModeloModel();
        modelo.setId(1);
        modelo.setDescricao("R250");
        modelo.setMarca(marca);
        
        StatusCarcacaModel statusCarcaca = new StatusCarcacaModel();
        statusCarcaca.setId(1);
        statusCarcaca.setDescricao("Disponível");
        
        CarcacaModel carcaca = new CarcacaModel();
        carcaca.setId(1);
        carcaca.setNumero_etiqueta("TEST001");
        carcaca.setDot("1234567890");
        carcaca.setStatus("start");
        carcaca.setModelo(modelo);
        carcaca.setMedida(medida);
        carcaca.setPais(pais);
        carcaca.setStatus_carcaca(statusCarcaca);
        carcaca.setDt_create(new Date());
        carcaca.setUuid(UUID.randomUUID());
        
        RegraModel regra = new RegraModel();
        regra.setId(1);
        regra.setTamanho_min(10.0);
        regra.setTamanho_max(15.0);
        regra.setTempo("90-120-12");
        
        ProducaoModel producao = new ProducaoModel();
        producao.setCarcaca(carcaca);
        producao.setMedida_pneu_raspado(10.5);
        producao.setRegra(regra);

        mockMvc.perform(post("/api/producao")
                .header("Authorization", "Bearer " + tokenJWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producao)))
                .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarProducaoComAutenticacao() throws Exception {
        // Criar dados de teste válidos
        PaisModel pais = new PaisModel();
        pais.setId(1);
        pais.setDescricao("Brasil");
        
        MedidaModel medida = new MedidaModel();
        medida.setId(1);
        medida.setDescricao("295/80R22.5");
        
        MarcaModel marca = new MarcaModel();
        marca.setId(1);
        marca.setDescricao("Bridgestone");
        
        ModeloModel modelo = new ModeloModel();
        modelo.setId(1);
        modelo.setDescricao("R250");
        modelo.setMarca(marca);
        
        StatusCarcacaModel statusCarcaca = new StatusCarcacaModel();
        statusCarcaca.setId(1);
        statusCarcaca.setDescricao("Disponível");
        
        CarcacaModel carcaca = new CarcacaModel();
        carcaca.setId(1);
        carcaca.setNumero_etiqueta("TEST001");
        carcaca.setDot("1234567890");
        carcaca.setStatus("start");
        carcaca.setModelo(modelo);
        carcaca.setMedida(medida);
        carcaca.setPais(pais);
        carcaca.setStatus_carcaca(statusCarcaca);
        carcaca.setDt_create(new Date());
        carcaca.setUuid(UUID.randomUUID());
        
        RegraModel regra = new RegraModel();
        regra.setId(1);
        regra.setTamanho_min(10.0);
        regra.setTamanho_max(15.0);
        regra.setTempo("90-120-12");
        
        ProducaoModel producao = new ProducaoModel();
        producao.setCarcaca(carcaca);
        producao.setMedida_pneu_raspado(15.0);
        producao.setRegra(regra);

        mockMvc.perform(put("/api/producao/" + producaoTeste.getId())
                .header("Authorization", "Bearer " + tokenJWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medida_pneu_raspado").value(15.0));
    }

    @Test
    void deveDeletarProducaoComAutenticacao() throws Exception {
        mockMvc.perform(delete("/api/producao/1")
                .header("Authorization", "Bearer " + tokenJWT))
                .andExpect(status().isNotFound()); // Assumindo que não existe produção com ID 1
    }

    @Test
    void deveFazerPesquisaComParametros() throws Exception {
        mockMvc.perform(get("/api/producao/pesquisa")
                .header("Authorization", "Bearer " + tokenJWT)
                .param("descricao", "Teste"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}