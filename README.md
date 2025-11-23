# 🌟 LUME – Plataforma Inteligente de Desenvolvimento de Soft Skills

A **LUME** é uma plataforma corporativa criada para apoiar colaboradores e gestores no desenvolvimento de **soft skills**, oferecendo testes, feedbacks inteligentes e acompanhamento por meio da assistente virtual **LUM.IA**.  

O projeto integra **Front-end em React + Vite** e **Back-end em Java (Spring Boot)**, seguindo boas práticas de arquitetura, DDD, modelagem de dados e experiência do usuário.

---

## 👥 Desenvolvedores

| Nome | RM | Função |
|------|------|--------|
| **Gabriely Bonfim Silva** | RM566242 | Front-end, Back-end, UI/UX, integração com API |
| **Mirelly Sousa Alves** | RM566299 | Back-end, lógica de negócio, LUM.IA e modelagem de domínio |
| **Henrique Sousa Vespasiano** | RM562917 | Banco de dados, DDD, testes e regras de negócio, Aplicações em PY |

---

## 📌 Sobre o Projeto

A LUME foi desenvolvida com o objetivo de **automatizar e padronizar processos de desenvolvimento humano dentro das empresas**, oferecendo:

- Gestão completa de colaboradores  
- Atribuição e realização de testes  
- Geração de feedbacks inteligentes  
- Chat com IA integrada  
- Painéis administrativos e operacionais  
- Histórico de respostas e acompanhamento evolutivo  

A interface foi pensada para ser **leve, acessível, responsiva e intuitiva**, utilizando design moderno em tons suaves de verde e bege.

---

## 🛠️ Arquitetura da Solução

### 🎨 Front-end (React + Vite + TypeScript)
- React 19  
- Vite  
- TailwindCSS  
- React Router DOM  
- Componentização e reaproveitamento  
- Requisições assíncronas para API  
- Páginas:
  - Homepage  
  - Login  
  - Perfil do Colaborador  
  - Painel Administrativo  
  - Lista de Testes  
  - Detalhes do Teste  
  - Sobre Nós  

---

### ☕ Back-end (Java + Spring Boot)
- Java 17  
- Spring Boot  
- Controllers REST  
- Services com regras de negócio  
- Repository + JPA  
- DDD (Domain Driven Design)  
- Banco MySQL / H2  
- Entidades:
  - Colaborador  
  - Teste  
  - Feedback  

---

## 🗂️ Banco de Dados – Modelo Resumido

### **COLABORADOR**
- id  
- nome  
- email  
- telefone  
- dataNascimento  
- numero  
- testes (1:N)

### **TESTE**
- id  
- titulo  
- conteudo  
- criadoEm  
- status  
- colaborador_id  

### **FEEDBACK**
- id  
- conteudo  
- criadoEm  
- teste_id  

---

## 📡 Endpoints da API

| Método | Rota | Descrição |
|--------|-------|-----------|
| GET | `/colaboradores` | Lista todos os colaboradores |
| POST | `/colaboradores` | Cria um novo colaborador |
| GET | `/testes` | Lista todos os testes disponíveis |
| POST | `/colaboradores/{id}/teste/{idTeste}` | Atribui um teste a um colaborador |
| GET | `/testes/{id}` | Retorna detalhes do teste |
| POST | `/feedback/{idTeste}` | Adiciona feedback a um teste |

---

## 🌐 Links do Projeto

🔗 **Frontend (Vercel):** _adicionar aqui_  
🔗 **Backend (Render):** _adicionar aqui_  
🎥 **Vídeo de Demonstração (YouTube):** _adicionar aqui_  

_(Me envie os links e eu coloco aqui formatado!)_

---

## 🚀 Como rodar o projeto localmente

### 🔧 Back-end (Java)

```bash
cd lume-backend
mvn spring-boot:run

cd lume-frontend
npm install
npm run dev

```
Acessar API:
👉 http://localhost:8080

### Front end (REACT)

```bash
cd lume-frontend
npm install
npm run dev
```
Acessar aplicação:
👉 http://localhost:5173

## 🌱 Principais Funcionalidades
### 👤 Colaboradores

Visualizar suas informações

Ver testes pendentes

Realizar testes

Consultar histórico de feedbacks

Conversar com a LUM.IA

### 🛠️ Administradores

Criar, editar e remover colaboradores

Atribuir testes individualmente

Acompanhar evoluções

Acessar conversas e feedbacks

Monitorar o andamento dos testes

### 🧠 Assistente Inteligente – LUM.IA

A LUM.IA é capaz de:

Responder dúvidas

Gerar feedbacks personalizados

Registrar conversas

Acompanhar o progresso do colaborador

Facilitar a comunicação entre equipe e gestão

# 🧾 Conclusão

A LUME representa uma solução inovadora e acessível para empresas que desejam investir no desenvolvimento pessoal e profissional de seus colaboradores.
Combinando tecnologia, inteligência artificial e um design amigável, o projeto demonstra maturidade técnica, organização e visão de futuro.

A plataforma está pronta para evoluir, podendo receber novas funcionalidades como dashboards avançados, gamificação e relatórios automáticos.

# ✨ Obrigado por visitar o projeto LUME!


