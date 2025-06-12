[![Iniciante em Programação G8 - ONE](https://img.shields.io/badge/Iniciante%20em%20Programação%20G8%20-%20ONE-blue)](https://cursos.alura.com.br/formacao-logica-de-programacao-grupo8-one)
[![ONE - Oracle Next Education](https://img.shields.io/badge/ONE%20-%20Oracle%20Next%20Education-blue)](https://www.oracle.com/br/education/oracle-next-education/)
[![Projeto Concluído](https://img.shields.io/badge/Projeto%20Concluído-success)](https://github.com/jguilherme936/conversor-moeda-java)

ORACLE NEXT EDUCATION | Alura                                                               2025-06-12

# 💱 CHALLENGE Conversor de Moedas

Este projeto é uma aplicação Java que utiliza a ExchangeRate-API para conversão de moedas em tempo real, desenvolvida como parte do programa Oracle Next Education (ONE). A aplicação serve para obter cotações atualizadas e oferece uma interface de linha de comando intuitiva para o usuário.

## 🚀 Funcionalidades

### 1. **Conversão de Moedas**
- Conversão em tempo real entre diferentes moedas
- Integração com a API ExchangeRate-API para cotações precisas
- Suporte a mais de 160 moedas mundiais
- Validação de códigos de moeda e valores de entrada

### 2. **Histórico de Conversões**
- Armazenamento de todas as conversões realizadas na sessão
- Visualização completa do histórico com detalhes das operações
- Funcionalidade para limpar o histórico quando necessário

### 3. **Listagem de Moedas**
- Exibição das principais moedas suportadas (USD, BRL, EUR, GBP, JPY, CAD)
- Lista completa de todas as moedas disponíveis organizadas em colunas
- Nomes das moedas em português para melhor usabilidade

### 4. **Interface Interativa**
- Menu principal com opções numeradas
- Validação de entrada do usuário
- Mensagens de erro e sucesso claras
- Design responsivo no terminal

## 🏗️ Arquitetura do Projeto

O projeto segue princípios de Clean Architecture e implementa outros padrões de projeto, como Singleton, Factory Builder, e outras boas práticas de programação:

### **Pacotes Organizados**
- `Main`: Contém a classe principal da aplicação
- `Conversor`: Classes de domínio e serviços
- `Exception`: Hierarquia de exceções customizadas

### **Padrões de Design Implementados**
- **Singleton**: `GerenciadorServicoMoeda` para gerenciar instâncias do serviço
- **Factory**: `FabricaMoeda` para criação consistente de objetos Moeda
- **Strategy**: Interface `ServicoMoeda` permitindo diferentes implementações de APIs

### **Classes Principais**

#### `AplicativoConversorMoeda`
- Classe principal que gerencia a interface com o usuário
- Implementa o menu interativo e controla o fluxo da aplicação

#### `ConversorMoeda`
- Classe de fachada que coordena as operações de conversão
- Gerencia o histórico de conversões

#### `ServicoExchangeRateApi`
- Implementação da integração com a API ExchangeRate-API
- Tratamento de requisições HTTP e processamento de JSON

#### `Moeda` (Record)
- Representa uma moeda com código e nome
- Implementa equals e hashCode baseados no código da moeda

#### `ResultadoConversao`
- Encapsula o resultado de uma conversão com todos os detalhes
- Formatação elegante para exibição

## 🛠️ Tecnologias Utilizadas

- **Temurin JDK 21**: Linguagem principal do projeto
- **Gson**: Biblioteca para processamento de JSON
- **ExchangeRate-API**: API externa para cotações de moedas
- **HttpURLConnection**: Para requisições HTTP nativas

## 📋 Pré-requisitos

- Java 17 ou superior
- Conexão com a internet
- Chave da API ExchangeRate-API (gratuita)

## ⚙️ Como Executar

1. **Clone o repositório**
   ```bash
   git clone https://github.com/jguilherme936/conversor-moeda-java.git
   cd conversor-moeda-java
   ```

2. **Obtenha sua chave da API**
   - Acesse [ExchangeRate-API](https://www.exchangerate-api.com/)
   - Cadastre-se gratuitamente
   - Obtenha sua chave da API

3. **Compile e execute**
   ```bash
   javac -cp ".:lib/gson.jar" Main/AplicativoConversorMoeda.java
   java -cp ".:lib/gson.jar" Main.AplicativoConversorMoeda
   ```

4. **Uso da aplicação**
   - Digite sua chave da API quando solicitado
   - Navegue pelo menu usando os números das opções
   - Para conversões, insira os códigos das moedas (ex: USD, BRL)
   - Digite o valor a ser convertido

## 💡 Exemplo de Uso

```
=== Sistema de Conversão de Moedas ===
Integração com ExchangeRate-API

Digite sua chave da API ExchangeRate: sua-chave-aqui

Carregando moedas suportadas...
✓ Carregadas 168 moedas suportadas

=== Menu ===
1. Converter moeda
2. Ver histórico
3. Limpar histórico
4. Listar todas as moedas
5. Sair
Escolha uma opção: 1

Moeda de origem (ex: USD): USD
Moeda de destino (ex: BRL): BRL
Valor a ser convertido: 100

✓ === Resultado da Conversão ===
100.00 USD = 518.50 BRL (Taxa: 5.1850) - 2025-06-12T10:30:15
```

## 🔧 Tratamento de Erros

A aplicação possui um sistema robusto de tratamento de exceções:

- **ExcecaoMoeda**: Erros relacionados a moedas inválidas
- **ExcecaoMoedaInvalida**: Códigos de moeda com formato incorreto
- **ExcecaoApi**: Problemas de conectividade ou resposta da API

## 🎯 Principais Aprendizados

Este projeto demonstra conceitos importantes de programação Java:

- Programação Orientada a Objetos
- Tratamento de exceções customizadas
- Consumo de APIs REST
- Padrões de Design
- Organização de código em pacotes
- Records (Java 14+)
- Programação defensiva

#### 👨‍💻 Quem sou

João Guilherme Azevedo Barros, aluno de Engenharia da Computação no 4º Período pela Universidade CEUMA, entusiasta de Full-stack, dev e Cibersegurança.

##### 🙏 Agradecimentos

Gostaria de expressar minha profunda gratidão à Alura e à Oracle pelo programa ONE (Oracle Next Education). Os cursos e materiais oferecidos foram fundamentais para o meu desenvolvimento como profissional de TI. Através deles, adquiri conhecimentos valiosos que me permitiram realizar este projeto com sucesso, aplicando conceitos de programação orientada a objetos, consumo de APIs, tratamento de exceções e boas práticas de desenvolvimento. 
