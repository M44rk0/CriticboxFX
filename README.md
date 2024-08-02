
![Critic](https://github.com/user-attachments/assets/9b901343-bf75-46ca-ad26-c5abfe394310)

Criticbox é um sistema de avaliação de filmes, séries e animes, inspirado na plataforma Letterboxd. Desenvolvido em Java 21, com uma interface gráfica interativa utilizando JavaFX, o Criticbox proporciona aos usuários, por meio da TMDB API, a capacidade de buscar, avaliar e gerenciar seus conteúdos favoritos, além acessar informações detalhadas sobre cada título. Este projeto foi realizado como o trabalho final para a disciplina de Programação Orientada a Objetos, demonstrando o uso de conceitos avançados de programação e integração com APIs externas.

## Descrição do Problema e Escopo do Trabalho

O Criticbox tem como objetivo fornecer uma plataforma intuitiva para a avaliação e gestão de filmes, séries e animes. Os principais objetivos incluem:

- **Avaliação de Filmes e Séries**: Permitir que os usuários forneçam suas avaliações detalhadas sobre filmes e séries, incluindo a possibilidade de comentar e classificar cada título.
- **Favoritos e Históricos**: Oferecer a funcionalidade de adicionar títulos aos favoritos e manter um histórico de conteúdos assistidos, facilitando o acesso rápido e a organização pessoal dos usuários.
- **Informações Detalhadas**: Exibir informações extensivas sobre filmes e séries, como elenco, diretores, escritores e outros membros da equipe de produção, aproveitando dados fornecidos pela Tmdb API.

## Funcionalidades

### 1. Cadastro de Usuários

O sistema permite que novos usuários se registrem na plataforma fornecendo informações básicas, como nome, login e senha. Além disso, ao realizar o login em uma nova conta, o sistema é capaz de identificar e exibir a última busca feita pelo usuário, facilitando a continuidade e o acesso às informações recentemente consultadas.

![login](https://github.com/user-attachments/assets/a363740a-aacf-4dfd-baec-6080f0e4147b)


### 2. Gerenciamento de Títulos (Filmes e Séries)

![ezgif-2-f57c8da9b8](https://github.com/user-attachments/assets/1b732b70-a6aa-48ea-bf33-ba6ea4134198)


Criticbox realiza a busca, armazena e organiza informações detalhadas sobre títulos, incluindo filmes e séries. Para filmes, o sistema captura dados essenciais como data de lançamento, duração, gênero, diretor, elenco, escritores, produção, etc. No caso das séries, o gerenciamento se estende às temporadas e episódios.

![buscagif](https://github.com/user-attachments/assets/8227a35d-406b-458c-89fe-b0314640512b)

### 3. Avaliação de Títulos

![reviewgif](https://github.com/user-attachments/assets/21e07b42-faa8-4c4b-97a4-5fcb5cd1b4c9)

Os usuários têm a capacidade de avaliar não apenas filmes e séries, mas também episódios individuais de séries. As avaliações incluem uma nota e um comentário textual.

![reviewEpisodiogif](https://github.com/user-attachments/assets/cc03b9d5-f071-43e9-8c80-da66bc6c3892)


### 4. Favoritar Títulos

Os usuários podem adicionar títulos à sua lista de favoritos. Isso facilita o acesso rápido a filmes e séries que o usuário deseja assistir mais tarde ou que considera interessantes, melhorando a organização pessoal e a navegação no sistema.

![favoritosgif](https://github.com/user-attachments/assets/eed0d439-e8eb-4d0e-8226-937e9545bc30)

### 5. Marcar Títulos Assistidos

Com a opção de marcar títulos como assistidos, os usuários podem gerenciar melhor seu histórico de visualização. Esta funcionalidade permite aos usuários acompanhar o que já foi visto, proporcionando uma maneira prática de gerenciar seu conteúdo consumido.

![assistidosgif](https://github.com/user-attachments/assets/7b50fde9-607e-44f8-8cdb-24e6e7d7c86c)

### 6. Persistência de Dados

O Criticbox utiliza um banco de dados MySQL para armazenar as informações dos usuários e dos títulos. A persistência é gerenciada por meio de JDBC, garantindo que todas as informações sejam armazenadas corretamente.

### 7. Testes Unitários

O projeto inclui alguns casos de testes unitários básicos realizados com JUnit. Os testes ajudam a identificar e corrigir possíveis erros e garantem a qualidade do código.

## Tecnologias Utilizadas

- **Java 21**: A versão mais recente da linguagem Java.
- **JavaFX + SceneBuilder**: Framework utilizado para criar a interface gráfica da aplicação.
- [**Tmdb API**](https://developer.themoviedb.org/docs/getting-started): API usada para obter dados sobre filmes, séries e animes. A Tmdb API oferece diversas informações sobre diversos títulos.
- [**Wrapper TheMovieDB API**](https://github.com/c-eg/themoviedbapi): Biblioteca usada para gerenciar as requisições e respostas da Tmdb API, facilitando o acesso a informações detalhadas sobre filmes e séries.
- **JDBC**: Tecnologia utilizada para conectar, consultar e gerenciar a persistência de dados com o banco de dados MySQL.
- **JUnit**: Ferramenta usada para criar e executar testes unitários.

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo LICENSE para detalhes.
