# 💈 Sistema de Gestão para Barbearia - Java & MySQL

Este projeto é um sistema de gerenciamento desenvolvido em **Java** com banco de dados **MySQL**, voltado para o controle e organização de uma barbearia. A aplicação possui uma interface intuitiva e funcionalidades pensadas para facilitar o dia a dia dos barbeiros e administradores.

## ⚙️ Funcionalidades Principais

- **Agendamento de Serviços**
    
    Organize serviços como corte de cabelo, barba e outros.
    
- **Gestão de Barbeiros**
    
    Cadastre, edite e gerencie informações dos barbeiros.
    
- **Edição e Exclusão de Dados**
    
    Modifique ou remova registros com segurança e facilidade.
    
- **Controle de Acesso por Perfis**
    
    Sistema de login com distinção entre Administrador e Usuário, garantindo maior segurança.
    

## 🛠️ Tecnologias Utilizadas

- Java 8 (OpenJDK 1.8.0_422)
- Java Swing
- MySQL
- JDBC

## 🚀 Como Rodar o Projeto

1. Clone o repositório:
    
    ```bash
    git clone https://github.com/viniciusvilla/projeto-java-barbearia
    ```
    
2. Importe o banco de dados
    
    O script SQL está localizado na pasta `dump_bd/`. Importe-o no seu MySQL para criar as tabelas e registros necessários.
    
3. Adicione as bibliotecas ao projeto (se necessário)
    
    As bibliotecas utilizadas estão na pasta `/lib`:
    
    - `mysql-connector-j-9.0.0.jar` – Responsável pela conexão com o banco de dados MySQL.
    - `rs2xml.jar` – Facilita a exibição de dados em tabelas (`JTable`).
    
    > ⚠️ Em alguns casos, sua IDE pode não reconhecer automaticamente as bibliotecas.
    > 
    > Se isso acontecer, adicione-as manualmente:
    > 
    > - Clique com o botão direito em **"Bibliotecas"** no seu projeto.
    > - Selecione **"Adicionar JAR/Pasta"**.
    > - Navegue até a pasta `/lib` e selecione os arquivos `.jar`.

## 🔐 Login para Testes

- **Usuário:** guilherme  
- **Senha:** 123  
- **Perfil:** Administrador

## 👤 Clientes cadastrados para teste

- Vinicius  
- Pedro  
- Antonio