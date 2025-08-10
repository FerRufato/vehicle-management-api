# Vehicle CRUD System

Sistema simples para cadastro, listagem, edição e exclusão de veículos.  
Back-end em **Kotlin + Spring Boot** e front em **HTML/CSS/JS puro**.

```
vehicle-crud-system/
├─ vehicles-api/        # Backend (Spring Boot + Kotlin)
└─ frontend/            # Frontend (HTML/CSS/JS)
   ├─ index.html
   ├─ style.css
   └─ script.js
```

---

##  Como rodar

### 1) Backend (API)
```bash
cd vehicles-api
./gradlew bootRun
```
- Swagger: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:veiculodb` (ou a que estiver no seu `application.yml`)
   - User: `sa` / senha: (vazio)

### 2) Frontend (SPA simples)
**Opção A — VS Code (Live Server)**
1. Abra `frontend/` no VS Code
2. Clique com o botão direito em `index.html` → **Open with Live Server**
3. Ele abre em `http://localhost:5500`

**Opção B — Python 3**
```bash
cd frontend
python -m http.server 5500
```
Acesse: `http://localhost:5500`

> No `script.js`, a URL da API está em:
> ```js
> const API = 'http://localhost:8080';
> ```
> Se mudar a porta/host, ajuste aqui.  
> No back, o CORS está liberado para `http://localhost:5500`.

---

##  Funcionalidades

### API (Spring Boot)
- CRUD de veículos: `POST /veiculos`, `GET /veiculos`, `GET /veiculos/{id}`, `PUT /veiculos/{id}`, `PATCH /veiculos/{id}`, `DELETE /veiculos/{id}`.
- Filtros combináveis em `GET /veiculos?marca=&ano=&cor=`.
- Relatórios:
   - `GET /veiculos/nao-vendidos` (quantidade)
   - `GET /veiculos/por-marca` (mapa marca→quantidade)
   - `GET /veiculos/por-decada` (mapa década→quantidade)
   - `GET /veiculos/ultimos-7-dias` (lista)

### Front (HTML/CSS/JS)
- **Lista** com filtros `marca/ano/cor`.
- **Formulário** de criar/editar (PUT) e **exclusão** (DELETE).
- **Relatórios** consumindo os endpoints do back.

---

##  Tecnologias

- **Backend**: Kotlin, Spring Boot (Web, Validation, JPA), H2, Swagger (springdoc).
- **Frontend**: HTML5, CSS3, JavaScript (Fetch API).
- **Build**: Gradle.

---

##  Decisões rápidas

- **DTOs** para isolar a entidade do tráfego HTTP.
- **Bean Validation** nos DTOs (ex.: `@NotBlank`, `@Min`).
- **Regra de negócio**: normalização e validação de marcas (whitelist).
- **Filtros** simples em memória (legível e suficiente para o escopo).
- **CORS** liberando `http://localhost:5500`.
- **H2 em memória** para execução rápida; dados zeram a cada restart.
   - Se quiser manter registros, usar `data.sql` ou `CommandLineRunner`.

---

##  Como testar rápido (Swagger)

1) Crie 3–5 veículos via `POST /veiculos`.
2) Liste tudo: `GET /veiculos` → deve retornar a lista.
3) Filtros:
   - `?marca=Honda`
   - `?marca=Ford&ano=2008&cor=azul`
4) Detalhe/erro:
   - `GET /veiculos/{id}` existente → 200
   - `GET /veiculos/99999` → 404
5) Atualizações:
   - `PUT /veiculos/{id}` (muda cor/ano)
   - `PATCH /veiculos/{id}` body: `{ "vendido": true }`
6) Delete:
   - `DELETE /veiculos/{id}` → 200/204
   - `GET /veiculos/{id}` → 404
7) Relatórios:
   - `/nao-vendidos`, `/por-marca`, `/por-decada`, `/ultimos-7-dias`.

---

## 🛡 Erros & CORS

- Erro 404 em `PUT/DELETE`: geralmente **ID inexistente** (verifique com `GET /veiculos`).
- Erro de **CORS** no front: garanta a config liberando `http://localhost:5500` e reinicie a API.
- H2 vazio após restart: é normal (banco em memória). Recrie via Swagger ou use seed.

---

##  Prints

> Salve as imagens em `vehicle-crud-system/docs/` com os nomes abaixo.

### Swagger — Listagem
![Swagger Listagem](./docs/swagger-listagem.png)

### Front — Lista de veículos
![Front Lista](./docs/front-lista.png)

### Front — Formulário
![Front Formulário](./docs/front-formulario.png)

### Front — Relatórios
![Front Relatórios](./docs/front-relatorios.png)

---

## 🗂 Estrutura de pastas (resumo)
```
vehicle-crud-system/
├─ vehicles-api/
│  ├─ src/main/kotlin/com/.../domain
│  ├─ src/main/kotlin/com/.../dto
│  ├─ src/main/kotlin/com/.../repo
│  ├─ src/main/kotlin/com/.../service
│  ├─ src/main/kotlin/com/.../web
│  └─ src/main/resources/application.yml
└─ frontend/
   ├─ index.html
   ├─ style.css
   └─ script.js
```

---