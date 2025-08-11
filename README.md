# Vehicle CRUD System

Sistema para **cadastro, listagem, atualização e remoção de veículos**.  
Back-end em **Kotlin + Spring Boot** com **H2** em memória e **Swagger**; front-end simples em **HTML + CSS + JavaScript (Fetch API)**.  
Inclui também os **exercícios de lógica (1–4)** em Kotlin (programas de console).

---

##  Estrutura do projeto


```
vehicle-crud-system/
├─ exercicios-kotlin/ # Exercícios de lógica (console)
│ ├─ BubbleSort.kt # (2) Bubble Sort
│ ├─ Eleicao.kt # (4) Percentuais de votos
│ ├─ Fatorial.kt # (3) Fatorial (iterativo)
│ └─ multiplos.kt # (1) Soma de múltiplos de 3 e 5 (<1000)
│
├─ frontend/ # SPA simples (estático)
│ ├─ index.html
│ ├─ script.js
│ └─ style.css
│
└─ vehicles-api/ # Back-end (Kotlin + Spring Boot)
├─ src/main/kotlin/com/ferufato/vehicles_api/
│ ├─ config/ # (se houver) config específica
│ ├─ domain/ # Entidades JPA
│ ├─ dto/ # DTOs de request/response
│ ├─ repo/ # Repositórios (Spring Data JPA)
│ ├─ service/ # Regras de negócio
│ └─ web/ # Controllers (endpoints REST)
├─ src/test/kotlin/com/ferufato/vehicles_api/
│ ├─ web/VeiculoControllerTest.kt # Testes unitários da API (GETs com WebMvcTest)
│ └─ VehiclesApiApplicationTests.kt
├─ src/main/resources/ # application.yml/properties, etc.
└─ build.gradle.kts # Gradle Kotlin DSL

```



##  Requisitos

- **JDK 21**
- **Gradle Wrapper** (já incluso)
- Navegador (para Swagger/SPA)

---

##  Como rodar

## 1) Back-end (API)
```bash
cd vehicles-api
./gradlew bootRun


```
Swagger: http://localhost:8080/swagger-ui/index.html

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:veiculosdb

User: sa | Password: (vazio)

Dica: se o front não estiver recebendo os dados, confira/ajuste a URL base da API dentro de frontend/script.js.

 ##   2) Front-end (SPA)
   Opção A — VS Code (Live Server)

Abra a pasta frontend/ no VS Code.

Clique com o botão direito em index.html → Open with Live Server.

Acesse: http://localhost:5500 (ou a porta indicada).

cd frontend
python -m http.server 5500
 acesse http://localhost:5500

```


Endpoints principais
CRUD:

POST /veiculos — cria veículo

GET /veiculos — lista veículos (filtros ?marca=&ano=&cor=)

GET /veiculos/{id} — detalhes por id

PUT /veiculos/{id} — atualização completa

PATCH /veiculos/{id} — atualização parcial

DELETE /veiculos/{id} — remove veículo

Relatórios/consultas:

GET /veiculos/nao-vendidos

GET /veiculos/por-marca

GET /veiculos/por-decada

GET /veiculos/ultimos-7-dias

Fluxo rápido para testar via Swagger

Use POST /veiculos para cadastrar 3–5 veículos.

Liste com GET /veiculos e aplique filtros (ex.: marca=Honda, ano=2019).

Valide GET /veiculos/{id} (id existente e inexistente).

Exercite PUT/PATCH/DELETE e os relatórios.

````

## Testes automatizados

Testes automatizados (unitários da API)
Implementados com JUnit 5 + @WebMvcTest (MockMvc).

Cobertura nesta versão:

GET /veiculos (lista)

GET /veiculos?marca=… (filtro)

GET /veiculos/{id}: 200 (existente) e 404 (inexistente)

Como executar:


dentro de vehicles-api
./gradlew test


Observação: são testes unitários da camada web (controller).
Não usam banco real e não afetam o Swagger/Frontend.

## Exercícios de Lógica


Os exercícios estão em exercicios-kotlin/ e rodam via main() no IntelliJ.

```kotlin
(1) Soma de múltiplos de 3 e 5 (multiplos.kt)
Soma todos os naturais abaixo de 1000 que sejam múltiplos de 3 ou 5.
Saída esperada: 233168.


(2) Bubble Sort (BubbleSort.kt)
Ordena o vetor {5, 3, 2, 4, 7, 1, 0, 6} usando Bubble Sort.
Saída esperada: [0, 1, 2, 3, 4, 5, 6, 7].

(3) Fatorial (Fatorial.kt)
Cálculo iterativo de n!.
Exemplo: 5! = 120.

(4) Percentuais de votos (Eleicao.kt)
Dado total, válidos, brancos e nulos, exibe os percentuais de cada um.
Exemplo: total=1000, válidos=800, brancos=150, nulos=50 →
Válidos 80.00% | Brancos 15.00% | Nulos 5.00%.

Como executar cada exercício

Abra o arquivo *.kt no IntelliJ e clique no ▶ da função main() (ou Run).

```



Decisões técnicas

Camadas: Controller → Service → Repository (JPA).

DTOs para requests/responses (evita expor entidades).

Validações com Bean Validation (@Valid, @NotBlank, etc.).

Normalização/filtros case-insensitive (marca/cor).

H2 em memória para setup simples.

Swagger (springdoc) para documentação e exploração.

Frontend desacoplado usando Fetch API.

```
.gitignore (sugestão)

# IDE/Build
.idea/
*.iml
.gradle/
build/
out/

# Front
node_modules/

# Logs/temp
*.log
*.tmp
```


Licença
MIT — livre para usar, modificar e distribuir.