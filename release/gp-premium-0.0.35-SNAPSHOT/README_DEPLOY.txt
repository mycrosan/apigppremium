GP Premium API - Pacote de Implantação
======================================

Conteúdo:
- app/gp-premium-0.0.35-SNAPSHOT.jar
- config/application.properties (ajuste credenciais e segredos)
- systemd/gp-spring-boot.service

Pré-requisitos:
- Java 11+ instalado (/usr/bin/java)
- MySQL 8.x com banco criado (ex.: gp_premium) e usuário com permissões

Passos de implantação (Linux):
1) Criar diretórios padrão:
   sudo mkdir -p /opt/gp/app /etc/gp
   sudo chown -R gp:gp /opt/gp /etc/gp

2) Copiar arquivos:
   sudo cp app/gp-premium-0.0.35-SNAPSHOT.jar /opt/gp/app/
   sudo cp config/application.properties /etc/gp/

3) Instalar serviço systemd:
   sudo cp systemd/gp-spring-boot.service /etc/systemd/system/
   sudo systemctl daemon-reload
   sudo systemctl enable gp-spring-boot
   sudo systemctl start gp-spring-boot

4) Logs e verificação:
   sudo journalctl -u gp-spring-boot -f
   A API deve subir em http://SEU_HOST:8080/

Observações:
- Flyway executará migrações automaticamente ao iniciar se spring.flyway.enabled=true.
- Ajuste server.port e variáveis de JWT conforme necessário.
- Para atualizar, substitua o JAR em /opt/gp/app e reinicie o serviço.
