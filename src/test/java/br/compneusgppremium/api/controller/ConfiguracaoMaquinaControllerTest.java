package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.dto.ConfiguracaoMaquinaCreateDTO;
import br.compneusgppremium.api.controller.model.ConfiguracaoMaquinaModel;
import br.compneusgppremium.api.controller.model.MatrizModel;
import br.compneusgppremium.api.controller.model.RegistroMaquinaModel;
import br.compneusgppremium.api.repository.ConfiguracaoMaquinaRepository;
import br.compneusgppremium.api.repository.MatrizRepository;
import br.compneusgppremium.api.repository.RegistroMaquinaRepository;
import br.compneusgppremium.api.util.UsuarioLogadoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfiguracaoMaquinaControllerTest {

    @Mock
    private ConfiguracaoMaquinaRepository configuracaoMaquinaRepository;

    @Mock
    private RegistroMaquinaRepository registroMaquinaRepository;

    @Mock
    private MatrizRepository matrizRepository;

    @Mock
    private UsuarioLogadoUtil usuarioLogadoUtil;

    @InjectMocks
    private ConfiguracaoMaquinaController configuracaoMaquinaController;

    @Test
    public void testCriarConfiguracao_ComMatrizValida_DeveDefinirMatrizCorretamente() {
        // Arrange
        ConfiguracaoMaquinaCreateDTO dto = new ConfiguracaoMaquinaCreateDTO();
        dto.setMaquinaId(1L);
        dto.setMatrizId(1);
        dto.setCelularId("CEL001");
        dto.setDescricao("Configuração de produção");
        dto.setAtributos("{\"velocidade\":100,\"temperatura\":80}");

        // Mock da máquina
        RegistroMaquinaModel maquina = new RegistroMaquinaModel();
        maquina.setId(1L);
        maquina.setNome("Máquina Teste");
        when(registroMaquinaRepository.findById(1L)).thenReturn(Optional.of(maquina));

        // Mock da matriz
        MatrizModel matriz = new MatrizModel();
        matriz.setId(1);
        matriz.setDescricao("Matriz Teste");
        when(matrizRepository.findById(1)).thenReturn(Optional.of(matriz));

        // Mock do usuário logado
        when(usuarioLogadoUtil.getUsuarioIdLogado()).thenReturn(1L);

        // Mock do save - captura o objeto que será salvo
        when(configuracaoMaquinaRepository.save(any(ConfiguracaoMaquinaModel.class)))
            .thenAnswer(invocation -> {
                ConfiguracaoMaquinaModel config = invocation.getArgument(0);
                config.setId(1L);
                return config;
            });

        // Act
        ResponseEntity<?> response = configuracaoMaquinaController.criarConfiguracao(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        // Verifica se a resposta contém os dados corretos
        ConfiguracaoMaquinaModel resultado = (ConfiguracaoMaquinaModel) response.getBody();
        assertNotNull(resultado);
        assertNotNull(resultado.getMatriz(), "A matriz deve estar definida na configuração");
        assertEquals(1, resultado.getMatriz().getId(), "O ID da matriz deve ser 1");
        assertEquals("Matriz Teste", resultado.getMatriz().getDescricao());
        assertEquals("CEL001", resultado.getCelularId());
        assertEquals("Configuração de produção", resultado.getDescricao());
    }

    @Test
    public void testCriarConfiguracao_ComMatrizInexistente_DeveRetornarNotFound() {
        // Arrange
        ConfiguracaoMaquinaCreateDTO dto = new ConfiguracaoMaquinaCreateDTO();
        dto.setMaquinaId(1L);
        dto.setMatrizId(999); // ID inexistente
        dto.setCelularId("CEL001");
        dto.setDescricao("Configuração de produção");
        dto.setAtributos("{\"velocidade\":100,\"temperatura\":80}");

        // Mock da máquina
        RegistroMaquinaModel maquina = new RegistroMaquinaModel();
        maquina.setId(1L);
        maquina.setNome("Máquina Teste");
        when(registroMaquinaRepository.findById(1L)).thenReturn(Optional.of(maquina));

        // Mock da matriz inexistente
        when(matrizRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = configuracaoMaquinaController.criarConfiguracao(dto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}