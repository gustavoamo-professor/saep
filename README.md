# Sistema de Login - Aplicação Java

## Parte 4 – Questões

1. O sistema pega a senha atual digitada, gera o hash SHA-256 dela e compara com o hash que está gravado no banco de dados (método verificarSenha). Se for igual, a senha está correta.

2. O método alterarSenha() atualiza a senha do usuário no banco de dados.
A senha nunca deve ser salva em texto simples porque, se alguém acessar o banco, consegue ver a senha real. Por isso ela é gravada apenas como hash (SHA-256).

3. Validações feitas antes de salvar a nova senha:

- Todos os campos devem estar preenchidos
- A nova senha deve ter no mínimo 6 caracteres
- A nova senha e a confirmação devem ser iguais
- A nova senha deve ser diferente da senha atual
- A senha atual informada deve estar correta.