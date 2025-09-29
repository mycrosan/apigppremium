# gp_app
App para gestão de carcaças

# Descrição
Sistema Exclusivo GP Premium

### Commit type	Emoji

- Initial commit	🎉 :tada:
- Version tag	🔖 :bookmark:
- New feature	✨ :sparkles:
- Bugfix	🐛 :bug:
- Metadata	📇 :card_index:
- Documentation	📚 :books:
- Documenting source code	💡 :bulb:
- Performance	🐎 :racehorse:
- Cosmetic	💄 :lipstick:
- Tests	🚨 :rotating_light:
- Adding a test	✅ :white_check_mark:
- Make a test pass	✔️ :heavy_check_mark:
- General update	⚡ :zap:
- Improve format/structure	🎨 :art:
- Refactor code	🔨 :hammer:
- Removing code/files	🔥 :fire:
- Continuous Integration	💚 :green_heart:
- Security	🔒 :lock:
- Upgrading dependencies	⬆️ :arrow_up:
- Downgrading dependencies	⬇️ :arrow_down:
- Lint	👕 :shirt:
- Translation	👽 :alien:
- Text	📝 :pencil:
- Critical hotfix	🚑 :ambulance:
- Deploying stuff	🚀 :rocket:
- Fixing on MacOS	🍎 :apple:
- Fixing on Linux	🐧 :penguin:
- Fixing on Windows	🏁 :checkered_flag:
- Work in progress	🚧 :construction:
- Adding CI build system	👷 :construction_worker:
- Analytics or tracking code	📈 :chart_with_upwards_trend:
- Removing a dependency	➖ :heavy_minus_sign:
- Adding a dependency	➕ :heavy_plus_sign:
- Docker	🐳 :whale:
- Configuration files	🔧 :wrench:
- Package.json in JS	📦 :package:
- Merging branches	🔀 :twisted_rightwards_arrows:
- Bad code / need improv.	💩 :hankey:
- Reverting changes	⏪ :rewind:
- Breaking changes	💥 :boom:
- Code review changes	👌 :ok_hand:
- Accessibility	♿ :wheelchair:
- Move/rename repository	🚚 :truck:

## Matar sessao 
- lsof -i :8080
COMMAND   PID   USER   FD   TYPE DEVICE SIZE/OFF NODE NAME  
java     **12345**  you   ...  TCP  ...    LISTEN    ...
- kill -9 12345
## sdk default java 11.0.17-tem

mvn install

-- Criar usuário
CREATE USER 'monty'@'%' IDENTIFIED BY 'some_pass';

-- Criar o banco (se ainda não existir)
CREATE DATABASE IF NOT EXISTS sislife CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Conceder privilégios totais no banco para o usuário
GRANT ALL PRIVILEGES ON sislife.* TO 'monty'@'%';

-- Atualizar privilégios
FLUSH PRIVILEGES;

sdk use java 11.0.24-tem

http://localhost:8080/swagger-ui/index.html



mvn spring-boot:run 


