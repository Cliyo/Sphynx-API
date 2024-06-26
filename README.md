# <p align="center"> SPHYNX API </p>

## Resumo
Continuação do projeto Sphynx, com foco em melhorar e acrescentar funcionalidades ao projeto original, como controle efetivo de acesso, níveis de permissão, biometria e reconhecimento facial. Sendo desenvolvido utilizando o framework Spring.

## Tecnologias
- Spring Boot
- Spring Data JPA
- Spring Validation
- Spring Security
- Token JWT
- MySQL

## Modelos
- Consumer
  - id
  - name
  - ra
  - tag
- Local
  - id
  - name
  - mac
- AccessRegister
  - id
  - consumer_id
  - local_id
- Group
  - id
  - name

## Requisitos
- Possuir MySQL instalado na máquina
- Caso queira utilizar em conjunto com o frontend e outra API do projeto, baixar no link abaixo:
  - https://github.com/Cliyo/Sphynx-Web
  - https://github.com/Cliyo/Sphynx-Finder
