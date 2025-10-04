package br.compneusgppremium.api.service;

import br.compneusgppremium.api.controller.dto.ReleCreateDTO;
import br.compneusgppremium.api.controller.dto.ReleResponseDTO;
import br.compneusgppremium.api.controller.model.RegistroMaquinaModel;
import br.compneusgppremium.api.controller.model.SonoffModel;
import br.compneusgppremium.api.repository.RegistroMaquinaRepository;
import br.compneusgppremium.api.repository.SonoffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReleService {

    private final SonoffRepository sonoffRepository;
    private final RegistroMaquinaRepository registroMaquinaRepository;
    private final TasmotaService tasmotaService;

    public ReleService(SonoffRepository sonoffRepository,
                       RegistroMaquinaRepository registroMaquinaRepository,
                       TasmotaService tasmotaService) {
        this.sonoffRepository = sonoffRepository;
        this.registroMaquinaRepository = registroMaquinaRepository;
        this.tasmotaService = tasmotaService;
    }

    @Transactional
    public ReleResponseDTO gravar(ReleCreateDTO dto) {
        // Validar máquina (FK)
        Optional<RegistroMaquinaModel> maquinaOpt = registroMaquinaRepository.findByIdAndDtDeleteIsNull(dto.getMaquinaId());
        if (!maquinaOpt.isPresent()) {
            throw new IllegalArgumentException("Máquina com ID " + dto.getMaquinaId() + " não encontrada");
        }

        RegistroMaquinaModel maquina = maquinaOpt.get();

        SonoffModel model = sonoffRepository.findByIpAndDtDeleteIsNull(dto.getIp())
                .map(existing -> {
                    existing.setCelularId(dto.getCelularId());
                    existing.setMaquina(maquina);
                    return existing;
                })
                .orElseGet(() -> {
                    SonoffModel novo = new SonoffModel();
                    novo.setIp(dto.getIp());
                    novo.setCelularId(dto.getCelularId());
                    novo.setMaquina(maquina);
                    return novo;
                });

        SonoffModel salvo = sonoffRepository.save(model);

        // Configurar dispositivo Tasmota após persistência (usa nome da máquina)
        try {
            tasmotaService.configurar(salvo.getIp(), maquina.getNome(), maquina.getNome());
        } catch (RuntimeException e) {
            // Não impedir a gravação por falha de configuração, mas propagar se necessário
        }

        return toResponseDTO(salvo);
    }

    public List<ReleResponseDTO> listarPorCelular(String celularId) {
        return sonoffRepository.findByCelularIdAndDtDeleteIsNullOrderByDtCreateDesc(celularId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReleResponseDTO> listarTodos() {
        return sonoffRepository.findByDtDeleteIsNullOrderByDtCreateDesc()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ReleResponseDTO toResponseDTO(SonoffModel model) {
        Long maquinaId = null;
        String maquinaNome = null;
        if (model.getMaquina() != null) {
            maquinaId = model.getMaquina().getId();
            maquinaNome = model.getMaquina().getNome();
        }

        return new ReleResponseDTO(
                model.getId(),
                model.getIp(),
                model.getCelularId(),
                maquinaId,
                maquinaNome,
                model.getDtCreate(),
                model.getDtUpdate()
        );
    }
}