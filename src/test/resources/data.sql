-- Script de inicialização para testes
-- Inserir dados básicos necessários para os testes

-- Inserir perfil padrão
INSERT INTO perfil (id, descricao) VALUES (1, 'ROLE_USER') ON DUPLICATE KEY UPDATE descricao = 'ROLE_USER';

-- Inserir usuário de teste
INSERT INTO usuario (id, nome, login, senha) VALUES 
(1, 'Usuario Teste', 'teste@teste.com', '$2a$10$HvzA2YBbZQyQbfeXfJTlUezu5H7kqxjEuqXEyRXxgYxmOqrEeVBpm') 
ON DUPLICATE KEY UPDATE nome = 'Usuario Teste';

-- Inserir relação usuário-perfil
INSERT INTO usuario_perfil (id, perfil_id, usuario_id) VALUES (1, 1, 1) 
ON DUPLICATE KEY UPDATE perfil_id = 1, usuario_id = 1;

-- Inserir matriz de teste
INSERT INTO matriz (id, descricao) VALUES (1, 'Matriz Teste') 
ON DUPLICATE KEY UPDATE descricao = 'Matriz Teste';

-- Inserir máquina de teste
INSERT INTO maquina_registro (id, nome, descricao, usuario_id, status, numero_serie, dt_create) VALUES 
(1, 'Máquina Teste', 'Máquina para testes', 1, 'ATIVA', 'SN-TEST-001', NOW()) 
ON DUPLICATE KEY UPDATE nome = 'Máquina Teste';